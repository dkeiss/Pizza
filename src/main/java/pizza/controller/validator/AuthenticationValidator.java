package pizza.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import pizza.PizzaAuthenticationProvider;
import pizza.service.UserService;
import pizza.vo.user.UserVO;

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
		if (authenticationToken == null || !StringUtils.hasText(authenticationToken.getName())) {
			return false;
		}
		model.addAttribute("username", authenticationToken.getName());

		if (isAdmin(authenticationToken)) {
			model.addAttribute("isAdmin", true);
		}

		boolean authenticated = authenticationToken.isAuthenticated();
		model.addAttribute("isLoggedIn", authenticated);

		return authenticated;
	}

	public boolean isInitialAdminPassword(Principal principal, Model model) {
		UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
		if (authenticationToken == null || !StringUtils.hasText(authenticationToken.getName())) {
			return false;
		}
		model.addAttribute("username", authenticationToken.getName());

		boolean initialAdminPassword = userService.isInitialAdminPassword(authenticationToken.getName());
		if(initialAdminPassword){
			UserVO user = userService.getUserByName(authenticationToken.getName());
			model.addAttribute("user", user);
		}

		return initialAdminPassword;
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

	public String checkAdminAuthenticationGetPage(Principal principal, Model model, String adminTemplate) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
		if (isAuthenticated(principal, model) && isAdmin(usernamePasswordAuthenticationToken)) {
			return adminTemplate;
		}
		return "login";
	}

	public String redirectBasedOnRole(Principal principal, Model model) {
		if (isAdmin(principal)) {
			return "redirect:admin";
		}
		return "redirect:order";
	}

}
