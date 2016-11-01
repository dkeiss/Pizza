package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pizza.controller.validator.AuthenticationValidator;
import pizza.service.UserService;

import java.security.Principal;

/**
 * Created by Daniel Keiss on 02.09.2016.
 */
@Controller
public class IndexController {

    @Autowired
    private AuthenticationValidator authenticationValidator;

    @Autowired
    private UserService userService;

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
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
        boolean authenticated = authenticationValidator.isAuthenticated(usernamePasswordAuthenticationToken, model);
        if (!authenticated) {
            if (usernamePasswordAuthenticationToken != null && userService.isInitialAdminPassword(usernamePasswordAuthenticationToken.getName())) {
                return "admin/initialpassword";
            } else {
                return "login";
            }
        }
        if (isAdmin(usernamePasswordAuthenticationToken)) {
            return "admin/admin";
        }
        return "order/order";
    }

    boolean isAdmin(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return usernamePasswordAuthenticationToken.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
