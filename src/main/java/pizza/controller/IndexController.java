package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by Daniel Keiss on 02.09.2016.
 */
@Controller
public class IndexController {

    @Autowired
    private AuthenticationValidator authenticationValidator;

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
        if (!authenticationValidator.isAuthenticated(usernamePasswordAuthenticationToken, model)) {
            return "login";
        }
        if (usernamePasswordAuthenticationToken.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "admin/admin";
        }
        return "order/order";
    }

}
