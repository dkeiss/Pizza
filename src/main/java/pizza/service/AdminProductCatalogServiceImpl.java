package pizza.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pizza.service.common.ObjectMapperUtil;
import pizza.service.exception.productCatalog.ProductCatalogInvalidException;
import pizza.vo.product.additional.AdditionalCategoryVO;
import pizza.vo.product.menu.ProductCatalogFullVO;
import pizza.vo.product.menu.ProductCatalogInfoVO;
import pizza.vo.product.menu.ProductCatalogVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Daniel Keiss on 06.11.2016.
 */
@Service
public class AdminProductCatalogServiceImpl implements AdminProductCatalogService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AdditionalService additionalService;

    @Autowired
    private ProductCatalogService productCatalogService;

    @Override
    public ProductCatalogInfoVO createProductCatalogFull(ProductCatalogFullVO productCatalogFull) {
        ProductCatalogVO productCatalog = productCatalogService.createProductCatalog(productCatalogFull.getProductCatalog());
        List<AdditionalCategoryVO> additionals = productCatalogFull.getAdditionals();
        if (additionals != null) {
            addAdditionals(productCatalog.getProductCatalogId(), additionals);
        }
        return ObjectMapperUtil.copyFromBusinessObject(productCatalog, new ProductCatalogInfoVO());
    }

    @Override
    public ProductCatalogFullVO getProductCatalogFullVO(MultipartFile file) {
        try {
            String fileString = read(file.getInputStream());
            return objectMapper.readValue(fileString, ProductCatalogFullVO.class);
        } catch (UnrecognizedPropertyException e) {
            throw new ProductCatalogInvalidException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAdditionals(Integer productCatalogId, List<AdditionalCategoryVO> additionals) {
        for (AdditionalCategoryVO additionalCategory : additionals) {
            additionalService.createAdditionalCategory(productCatalogId, additionalCategory);
        }
    }

    private String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input, "UTF-8"))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

}
