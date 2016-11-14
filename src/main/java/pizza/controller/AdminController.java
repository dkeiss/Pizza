package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pizza.controller.validator.AuthenticationValidator;
import pizza.vo.admin.InitialAdminVO;

import javax.servlet.http.HttpServletRequest;
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
        return authenticationValidator.checkAdminAuthenticationGetPage(principal, model, "admin/admin");
    }

    @RequestMapping(value = "/admin/usermanagement", method = RequestMethod.GET)
    public String usermanagement(Principal principal, Model model) {
        return authenticationValidator.checkAdminAuthenticationGetPage(principal, model, "admin/usermanagement");
    }

    @RequestMapping(value = "/admin/shoppingcard", method = RequestMethod.GET)
    public String shoppingcard(Principal principal, Model model) {
        return authenticationValidator.checkAdminAuthenticationGetPage(principal, model, "admin/shoppingcard");
    }

    @RequestMapping(value = "/admin/initialpassword", method = RequestMethod.GET)
    public String getInitialPasswordPage(Principal principal, Model model) {
        return authenticationValidator.checkIsInitialAdminPasswordGetPage(principal, model);
    }

    @RequestMapping(value = "/admin/initialpassword", method = RequestMethod.POST)
    public String setInitialPassword(InitialAdminVO initialAdmin, Principal principal, Model model) {
        if (StringUtils.isEmpty(initialAdmin.getPassword()) || !authenticationValidator.isInitialAdminPassword(principal, model)) {
            return "admin/initialpassword";
        }
        authenticationValidator.setInitialAdminPassword(principal, model, initialAdmin.getPassword());
        return "login";
    }

}
