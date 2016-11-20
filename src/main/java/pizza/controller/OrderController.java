package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pizza.controller.validator.AuthenticationValidator;

import java.security.Principal;

/**
 * Created by Daniel Keiss on 13.11.2016.
 */
@Controller
public class OrderController {

    @Autowired
    private AuthenticationValidator authenticationValidator;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String order(Principal principal, Model model) {
        return authenticationValidator.checkAuthenticationGetPage(principal, model,  "order/order");
    }


}
