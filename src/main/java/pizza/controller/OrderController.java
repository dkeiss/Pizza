package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pizza.domain.Order;
import pizza.domain.Product;
import pizza.service.ProductService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Created by Daniel Keiss on 18.09.2016.
 */
@Controller
public class OrderController {

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Order order(Principal principal, @Valid @ModelAttribute("order") Order order, BindingResult bindingResult) {
        System.out.println(order);
        return order;
    }

}
