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

    @Autowired
    private UserService userService;

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
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        if (authenticationToken == null || StringUtils.isEmpty(principal.getName())) {
            return false;
        }
        model.addAttribute("username", authenticationToken.getName());
        boolean isAdmin = authenticationToken.getAuthorities().contains(PizzaAuthenticationProvider.ROLE_ADMIN);
        boolean initialAdminPassword = isInitialAdminPassword(authenticationToken.getDetails());
        model.addAttribute("isAdmin", isAdmin && !initialAdminPassword);

        boolean authenticated = authenticationToken.isAuthenticated();
        if (!authenticated && initialAdminPassword) {
            String password = authenticationToken.getCredentials() != null ? authenticationToken.getCredentials().toString() : null;
            if (StringUtils.isEmpty(password)) {
                model.addAttribute("adminInitialPassword", true);
            } else {
                // set initial password
                userService.setInitialAdminPassword(principal.getName(), password);
                return true;
            }
        }
        return authenticated;
    }

    private boolean isInitialAdminPassword(Object details) {
        if (details instanceof Map && ((Map) details).containsKey("initialAdminPassword")) {
            return (boolean) ((Map) details).get("initialAdminPassword");
        }
        return false;
    }

}
