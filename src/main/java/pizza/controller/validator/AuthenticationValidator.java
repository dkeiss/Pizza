package pizza.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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

    public void setInitialAdminPassword(Principal principal, Model model, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        userService.setInitialAdminPassword(authenticationToken.getName(), password);
        model.addAttribute("username", authenticationToken.getName());
    }

    public String checkAdminAuthenticationGetPage(Principal principal, Model model, String template) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
        if (isAuthenticated(principal, model) && isAdmin(usernamePasswordAuthenticationToken)) {
            model.addAttribute("pageType", "admin");
            return template;
        }
        model.addAttribute("pageType", "order");
        return "login";
    }

    public String checkIsAdminGetPage(Principal principal, Model model) {
        if (isAdmin(principal)) {
            model.addAttribute("pageType", "admin");
            return "admin/admin";
        }
        model.addAttribute("pageType", "order");
        return "order/order";
    }

}
