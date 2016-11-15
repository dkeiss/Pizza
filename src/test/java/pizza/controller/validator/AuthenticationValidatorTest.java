package pizza.controller.validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.ui.Model;
import pizza.PizzaAuthenticationProvider;
import pizza.service.UserService;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Daniel Keiss on 15.11.2016.
 */
public class AuthenticationValidatorTest {

	@InjectMocks
	private AuthenticationValidator authenticationValidator = new AuthenticationValidator();

	@Mock
	private UserService userService;

	@Mock
	private UsernamePasswordAuthenticationToken principal;

	@Mock
	private Model model;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void isUserAuthenticated() throws Exception {
		when(principal.isAuthenticated()).thenReturn(true);
		when(principal.getName()).thenReturn("thecat");
		when(principal.getAuthorities()).thenReturn(Collections.singletonList(PizzaAuthenticationProvider.ROLE_USER));
		boolean authenticated = authenticationValidator.isAuthenticated(principal, model);

		assertTrue(authenticated);
		verify(model).addAttribute("username", "thecat");
		verify(model, never()).addAttribute("isAdmin", true);
	}

	@Test
	public void isAdminAuthenticated() throws Exception {
		when(principal.isAuthenticated()).thenReturn(true);
		when(principal.getName()).thenReturn("thedog");
		when(principal.getAuthorities()).thenReturn(Collections.singletonList(PizzaAuthenticationProvider.ROLE_ADMIN));
		boolean authenticated = authenticationValidator.isAuthenticated(principal, model);

		assertTrue(authenticated);
		verify(model).addAttribute("username", "thedog");
		verify(model).addAttribute("isAdmin", true);
	}

	@Test
	public void isInitialAdminPassword() throws Exception {
		when(principal.getName()).thenReturn("theinitial");
		when(userService.isInitialAdminPassword("theinitial")).thenReturn(true);
		boolean initialAdminPassword = authenticationValidator.isInitialAdminPassword(principal, model);

		assertTrue(initialAdminPassword);
		verify(model).addAttribute("username", "theinitial");
	}

	@Test
	public void isNoInitialAdminPassword() throws Exception {
		when(principal.getName()).thenReturn("therainer");
		when(userService.isInitialAdminPassword("therainer")).thenReturn(false);
		boolean initialAdminPassword = authenticationValidator.isInitialAdminPassword(principal, model);

		assertFalse(initialAdminPassword);
		verify(model).addAttribute("username", "therainer");
	}

	@Test
	public void checkIsInitialAdminPasswordGetPageWithInitialAdminUser() throws Exception {
		when(principal.getName()).thenReturn("theinitial");
		when(userService.isInitialAdminPassword("theinitial")).thenReturn(true);

		String page = authenticationValidator.checkIsInitialAdminPasswordGetPage(principal, model);

		assertThat(page, is("admin/initialpassword"));
	}

	@Test
	public void checkIsInitialAdminPasswordGetPageWithoutInitialAdminUser() throws Exception {
		when(principal.getName()).thenReturn("therainer");
		when(userService.isInitialAdminPassword("therainer")).thenReturn(false);

		String page = authenticationValidator.checkIsInitialAdminPasswordGetPage(principal, model);

		assertThat(page, is("login"));
	}

	@Test
	public void isAdmin() throws Exception {
		when(principal.getAuthorities()).thenReturn(Collections.singletonList(PizzaAuthenticationProvider.ROLE_ADMIN));

		boolean admin = authenticationValidator.isAdmin(principal);

		assertTrue(admin);
	}

	@Test
	public void checkAdminAuthenticationGetPageWithValidAdmin() throws Exception {
		when(principal.isAuthenticated()).thenReturn(true);
		when(principal.getName()).thenReturn("thedog");
		when(principal.getAuthorities()).thenReturn(Collections.singletonList(PizzaAuthenticationProvider.ROLE_ADMIN));

		String adminTemplate = authenticationValidator.checkAdminAuthenticationGetPage(principal, model, "adminTemplate");

		assertThat(adminTemplate, is("adminTemplate"));
	}

	@Test
	public void redirectBasedOnRoleWithAdmin() throws Exception {
		when(principal.isAuthenticated()).thenReturn(true);
		when(principal.getName()).thenReturn("thedog");
		when(principal.getAuthorities()).thenReturn(Collections.singletonList(PizzaAuthenticationProvider.ROLE_ADMIN));

		String redirectBasedOnRole = authenticationValidator.redirectBasedOnRole(principal, model);

		assertThat(redirectBasedOnRole, is("redirect:admin"));
	}

}