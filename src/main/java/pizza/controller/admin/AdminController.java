package pizza.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pizza.controller.validator.AuthenticationValidator;
import pizza.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by Daniel Keiss on 21.09.2016.
 */
@Controller
public class AdminController {

    @Autowired
    private AuthenticationValidator authenticationValidator;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Principal principal, Model model) {
        return authenticationValidator.checkAdminAuthentication(principal, model, "admin/admin");
    }

    @RequestMapping(value = "/admin/usermanagement", method = RequestMethod.GET)
    public String usermanagement(Principal principal, Model model) {
        return authenticationValidator.checkAdminAuthentication(principal, model, "admin/usermanagement");
    }

    @RequestMapping(value = "/admin/shoppingcard", method = RequestMethod.GET)
    public String shoppingcard(Principal principal, Model model) {
        return authenticationValidator.checkAdminAuthentication(principal, model, "admin/shoppingcard");
    }

    @RequestMapping(value = "/admin/initialpassword", method = RequestMethod.GET)
    public String getInitialPasswordPage(Principal principal, Model model) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
        if (usernamePasswordAuthenticationToken != null && userService.isInitialAdminPassword(usernamePasswordAuthenticationToken.getName())) {
            model.addAttribute("username", usernamePasswordAuthenticationToken.getName());
            return "admin/initialpassword";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "/admin/initialpassword", method = RequestMethod.POST)
    public String setInitialPassword(HttpServletRequest request, Principal principal, Model model) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
        String password = request.getParameter("password");
        if (usernamePasswordAuthenticationToken == null || StringUtils.isEmpty(usernamePasswordAuthenticationToken.getName()) || StringUtils.isEmpty(password) || !userService.isInitialAdminPassword(usernamePasswordAuthenticationToken.getName())) {
            return "admin/initialpassword";
        }
        userService.setInitialAdminPassword(usernamePasswordAuthenticationToken.getName(), password);
        model.addAttribute("username", usernamePasswordAuthenticationToken.getName());
        return "login";
    }

}
