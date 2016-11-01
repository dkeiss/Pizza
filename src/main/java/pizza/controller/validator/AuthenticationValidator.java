package pizza.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import pizza.PizzaAuthenticationProvider;
import pizza.service.UserService;

import java.security.Principal;
import java.util.Map;

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
@Component
public class AuthenticationValidator {

    public String checkAuthentication(Principal principal, Model model, String template) {
        if (isAuthenticated(principal, model)) {
            return template;
        }
        return "login";
    }

    public String checkAdminAuthentication(Principal principal, Model model, String template) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
        if (isAuthenticated(principal, model) && isAdmin(usernamePasswordAuthenticationToken)) {
            return template;
        }
        return "login";
    }

    public boolean isAuthenticated(Principal principal, Model model) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        if (authenticationToken == null || StringUtils.isEmpty(principal.getName())) {
            return false;
        }
        model.addAttribute("username", authenticationToken.getName());

        if (isAdmin(authenticationToken)) {
            model.addAttribute("isAdmin", true);
        }

        return authenticationToken.isAuthenticated();
    }

    private boolean isAdmin(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return usernamePasswordAuthenticationToken.getAuthorities().contains(PizzaAuthenticationProvider.ROLE_ADMIN);
    }

}
