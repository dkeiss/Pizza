package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pizza.controller.validator.AuthenticationValidator;

import java.security.Principal;

/**
 * Created by Daniel Keiss on 21.09.2016.
 */
@Controller
public class AdminController {

    @Autowired
    private AuthenticationValidator authenticationValidator;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Principal principal, Model model) {
        return authenticationValidator.checkAdminAuthentication(principal, model, "admin/admin");
    }

    @RequestMapping(value = "/admin/usermanagement", method = RequestMethod.GET)
    public String usermanagement(Principal principal, Model model) {
        return authenticationValidator.checkAdminAuthentication(principal, model, "admin/usermanagement");
    }

}
