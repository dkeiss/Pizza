package pizza.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import pizza.PizzaAuthenticationProvider;
import pizza.service.UserService;

import java.security.Principal;

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
@Component
public class AuthenticationValidator {

    @Autowired
    private UserService userService;

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

    public boolean isInitialAdminPassword(Principal principal, Model model) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        if (authenticationToken != null) {
            model.addAttribute("username", authenticationToken.getName());
        }
        return authenticationToken != null && !StringUtils.isEmpty(authenticationToken.getName()) && userService.isInitialAdminPassword(authenticationToken.getName());
    }

    public String checkIsInitialAdminPasswordGetPage(Principal principal, Model model) {
        if (isInitialAdminPassword(principal, model)) {
            return "admin/initialpassword";
        } else {
            return "login";
        }
    }

    public boolean isAdmin(Principal principal) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        return authenticationToken.getAuthorities().contains(PizzaAuthenticationProvider.ROLE_ADMIN);
    }

    public String checkAdminAuthenticationGetPage(Principal principal, Model model, String template) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
        if (isAuthenticated(principal, model) && isAdmin(usernamePasswordAuthenticationToken)) {
            return template;
        }
        return "login";
    }

    public String checkIsAdminGetPage(Principal principal, Model model) {
        if (isAdmin(principal)) {
            return "redirect:admin";
        }
        return "redirect:order";
    }

}
