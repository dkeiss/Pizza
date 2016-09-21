package pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pizza.domain.Order;

import java.security.Principal;

/**
 * Created by Daniel Keiss on 21.09.2016.
 */
@Controller
public class AdminController {

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public String adminLogin(Principal principal, Model model) {
        if (principal == null) {
            return "login";
        }
        model.addAttribute("order", new Order());
        return "order";
    }

}
