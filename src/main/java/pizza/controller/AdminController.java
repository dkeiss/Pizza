package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pizza.controller.validator.AuthenticationValidator;
import pizza.service.UserService;
import pizza.vo.admin.InitialAdminVO;

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
		if (!userService.isInitialAdminValid(initialAdmin)) {
			addDataToModel(initialAdmin, model);
			model.addAttribute("errorMsg", "Bitte überprüfen Sie Ihre Eingaben!");
			return "admin/initialpassword";
		}
		if (!authenticationValidator.isInitialAdminPassword(principal, model)) {
			addDataToModel(initialAdmin, model);
			model.addAttribute("errorMsg", "Der angebene Benutzer wurde bereits vollständig angelegt!");
			return "admin/initialpassword";
		}

		UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
		userService.setInitialAdminPassword(authenticationToken.getName(), initialAdmin.getPassword());
		model.addAttribute("username", authenticationToken.getName());

		return "login";
	}

	private void addDataToModel(InitialAdminVO initialAdmin, Model model) {
		model.addAttribute("username", initialAdmin.getUsername());
		model.addAttribute("firstName", initialAdmin.getFirstName());
		model.addAttribute("lastName", initialAdmin.getLastName());
	}

}
