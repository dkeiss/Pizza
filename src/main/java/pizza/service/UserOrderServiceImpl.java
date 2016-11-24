package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.order.BulkOrder;
import pizza.domain.order.UserOrder;
import pizza.domain.order.UserOrderAdditional;
import pizza.domain.product.Product;
import pizza.domain.product.ProductCatalog;
import pizza.domain.product.ProductVariation;
import pizza.domain.product.additional.Additional;
import pizza.domain.product.additional.AdditionalPrice;
import pizza.domain.user.User;
import pizza.repositories.UserOrderRepository;
import pizza.repositories.UserRepository;
import pizza.service.exception.NotFoundException;
import pizza.service.exception.userorder.*;
import pizza.vo.order.UserOrderAdditionalVO;
import pizza.vo.order.UserOrderDetailsVO;
import pizza.vo.order.UserOrderPaidVO;
import pizza.vo.order.UserOrderVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static pizza.service.common.AdditionalBusinessToValueConverter.getProductIdsFromProductsString;
import static pizza.service.common.UserOrderBusinessToValueObjectConverter.getUserOrderFromBO;
import static pizza.service.common.UserOrderBusinessToValueObjectConverter.getUserOrdersFromBOs;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserOrderRepository userOrderRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private BulkOrderService bulkOrderService;

	@Autowired
	private ProductCatalogService productCatalogService;

	@Autowired
	private AdditionalService additionalService;

	@Override
	public UserOrderDetailsVO addUserOrder(String username, UserOrderVO userOrderVO) {
		checkUserOrderValid(userOrderVO);
		User user = userService.findUserByUsername(username);
		if (user == null) {
			throw new UserOrderUserNotFoundException();
		}

		BulkOrder activeBulkOrder = bulkOrderService.findActiveBulkOrder();
		if (activeBulkOrder == null) {
			throw new UserOrderNoBulkOrderActiveException();
		}

		Product product = productCatalogService.findProduct(userOrderVO.getProductId());
		ProductVariation productVariation = productCatalogService.findProductVariation(userOrderVO.getProductVariationId());
		if (!product.getProductId().equals(productVariation.getProduct().getProductId())) {
			throw new UserOrderProductAndProductVariationNotMatchException();
		}

		ProductCatalog productCatalog = product.getProductGroup().getProductCategory().getProductCatalog();
		if (!productCatalog.getProductCatalogId().equals(activeBulkOrder.getCatalogId())) {
			throw new UserOrderProductCatalogNotMatchException();
		}

		UserOrder userOrder = new UserOrder();
		userOrder.setUser(user);
		userOrder.setBulkOrder(activeBulkOrder);
		userOrder.setProduct(product);
		userOrder.setProductVariation(productVariation);
		List<UserOrderAdditional> userOrderAdditionals = createUserOrderAdditionals(product, userOrderVO, userOrder);
		userOrder.setUserOrderAdditionals(userOrderAdditionals);
		calculateAmount(userOrder, user, userOrderVO.getNumber(), productVariation, userOrderAdditionals);
		userOrder.setPaid(false);
		userOrder.setNumber(userOrderVO.getNumber());
		userOrder.setCreationDate(new Date());

		userOrderRepository.save(userOrder);

		return getUserOrderFromBO(userOrder);
	}

	@Override
	public List<UserOrderDetailsVO> getAllUserOrders() {
		List<UserOrder> userOrders = new ArrayList<>();
		userOrderRepository.findAll().forEach(userOrders::add);
		return getUserOrdersFromBOs(userOrders);
	}

	private void calculateAmount(UserOrder userOrder, User user, Integer number, ProductVariation productVariation,
			List<UserOrderAdditional> userOrderAdditionalses) {

		BigDecimal amount = productVariation.getPrice();

		// add additional prices
		if (userOrderAdditionalses != null) {
			for (UserOrderAdditional userOrderAdditional : userOrderAdditionalses) {
				AdditionalPrice additionalPrice = userOrderAdditional.getAdditionalPrice();
				if (additionalPrice == null) {
					continue;
				}
				amount = amount.add(additionalPrice.getPrice());
			}
		}

		// number of orders
		amount = amount.multiply(new BigDecimal(number));

		// calculate discount
		if (user.getDiscount() != null && user.getDiscount().compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal amountAfterDiscount = amount.subtract(user.getDiscount());
			// amount is negative
			if (amountAfterDiscount.compareTo(BigDecimal.ZERO) < 0) {
				user.setDiscount(amountAfterDiscount.multiply(BigDecimal.valueOf(-1)));
				userOrder.setDiscount(amount);
				amount = BigDecimal.ZERO;
			}
			// no discount left
			else {
				userOrder.setDiscount(user.getDiscount());
				user.setDiscount(BigDecimal.ZERO);
				amount = amountAfterDiscount;
			}

			userRepository.save(user);
		}

		userOrder.setAmount(amount);
	}

	private List<UserOrderAdditional> createUserOrderAdditionals(Product product, UserOrderVO userOrderVO, UserOrder userOrder) {
		if (userOrderVO.getUserOrderAdditionals() == null || userOrderVO.getUserOrderAdditionals().isEmpty()) {
			return null;
		}
		List<UserOrderAdditional> userOrderAdditions = new ArrayList<>();
		for (UserOrderAdditionalVO userOrderAdditionalVO : userOrderVO.getUserOrderAdditionals()) {
			Additional additional = additionalService.findAdditional(userOrderAdditionalVO.getAdditionalId());
			if (additional == null) {
				throw new UserOrderAdditionalNotFoundException();
			}
			AdditionalPrice additionalPrice = additionalService.findAdditionalPrice(userOrderAdditionalVO.getAdditionalPriceId());
			if (additionalPrice == null || !additionalPrice.getAdditional().getAdditionalId().equals(additional.getAdditionalId())) {
				throw new UserOrderAdditionalPriceNotFoundException();
			}
			if (!getProductIdsFromProductsString(additional.getAdditionalCategory().getProductIds()).contains(product.getProductId())) {
				throw new UserOrderAdditionalAndProductNotMatchException();
			}
			UserOrderAdditional userOrderAdditional = new UserOrderAdditional();
			userOrderAdditional.setUserOrder(userOrder);
			userOrderAdditional.setAdditional(additional);
			userOrderAdditional.setAdditionalPrice(additionalPrice);
			userOrderAdditional.setCreationDate(new Date());
			userOrderAdditions.add(userOrderAdditional);
		}
		return userOrderAdditions;
	}

	private void checkUserOrderValid(UserOrderVO userOrderVO) {
		checkIfProductExist(userOrderVO.getProductId());
		checkIfProductVariationExist(userOrderVO.getProductVariationId());
	}

	private void checkIfProductExist(Integer productId) {
		if (!productCatalogService.productExists(productId)) {
			throw new UserOrderProductNotFoundException();
		}
	}

	private void checkIfProductVariationExist(Integer productVariationId) {
		if (!productCatalogService.productVariationExists(productVariationId)) {
			throw new UserOrderProductVariationNotFoundException();
		}
	}

	@Override
	public List<UserOrderDetailsVO> getUserOrders(Integer userId) {
		User user = userService.findUser(userId);
		if (user == null) {
			throw new NotFoundException();
		}
		List<UserOrder> userOrders = userOrderRepository.findByUser(user);
		return getUserOrdersFromBOs(userOrders);
	}

	@Override
	public UserOrderDetailsVO getUserOrder(Integer userOrderId) {
		UserOrder userOrder = getUserOrderBO(userOrderId);
		return getUserOrderFromBO(userOrder);
	}

	private UserOrder getUserOrderBO(Integer userOrderId) {
		UserOrder userOrder = userOrderRepository.findOne(userOrderId);
		if (userOrder == null) {
			throw new NotFoundException();
		}
		return userOrder;
	}

	@Override
	public void setUserOrderPaid(Integer userOrderId, UserOrderPaidVO userOrderPaidVO) {
		UserOrder userOrder = getUserOrderBO(userOrderId);
		userOrder.setPaid(userOrderPaidVO.getPaid());
		userOrderRepository.save(userOrder);
	}

	@Override
	public void deleteUserOrder(Integer userOrderId) {
		UserOrder userOrder = getUserOrderBO(userOrderId);
		if (userOrder.getDiscount() != null && userOrder.getDiscount().compareTo(BigDecimal.ZERO) > 0) {
			User user = userOrder.getUser();
			if (user.getDiscount() == null) {
				user.setDiscount(BigDecimal.ZERO);
			}
			user.setDiscount(user.getDiscount().add(userOrder.getDiscount()));
			userRepository.save(user);
		}

		userOrderRepository.delete(userOrderId);
	}

	@Override
	public List<UserOrderDetailsVO> getCurrentUserOrdersIncludingDiscount() {
		BulkOrder bulkOrder = bulkOrderService.findOpenBulkOrder();
		List<UserOrder> userOrders = userOrderRepository.findByBulkOrder(bulkOrder);
		List<UserOrderDetailsVO> userOrderVOs = getUserOrdersFromBOs(userOrders);
		return userOrderVOs;
	}

}
