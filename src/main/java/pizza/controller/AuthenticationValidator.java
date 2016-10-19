package pizza.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.security.Principal;

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
        if (isAuthenticated(principal, model) && usernamePasswordAuthenticationToken.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return template;
        }
        return "login";
    }

    public boolean isAuthenticated(Principal principal, Model model) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
        if (usernamePasswordAuthenticationToken == null) {
            return false;
        }
        model.addAttribute("username", usernamePasswordAuthenticationToken.getName());
        model.addAttribute("admin", usernamePasswordAuthenticationToken.getAuthorities().contains(pizza.PizzaAuthenticationProvider.ROLE_ADMIN));
        return usernamePasswordAuthenticationToken.isAuthenticated();
    }

}
