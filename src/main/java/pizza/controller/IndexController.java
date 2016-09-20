package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import pizza.domain.Order;
import pizza.domain.Product;
import pizza.service.ProductService;

/**
 * Created by Daniel Keiss on 02.09.2016.
 */
@Controller
public class IndexController {

    private Logger log = Logger.getLogger(IndexController.class);

    @Autowired
    private ProductService productService;

    @ModelAttribute("allProducts")
    public List<Product> allProducts() {
        return this.productService.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(Principal principal, Model model) {
        return login(principal, model);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Principal principal, Model model) {
        return login(principal, model);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Principal principal, Model model) {
        if (principal == null) {
            return "login";
        }
        model.addAttribute("order", new Order());
        return "order";
    }

    @RequestMapping("properties")
    @ResponseBody
    Properties properties() {
        return System.getProperties();
    }

}
