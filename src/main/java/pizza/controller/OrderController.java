package pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by Daniel Keiss on 13.11.2016.
 */
@Controller
public class OrderController {

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String order(Principal principal, Model model) {
        model.addAttribute("pageType", "order");
        return "order/order";
    }


}
