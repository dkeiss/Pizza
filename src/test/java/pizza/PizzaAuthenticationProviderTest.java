package pizza;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import pizza.service.UserService;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Daniel Keiss on 12.09.2016.
 */
public class PizzaAuthenticationProviderTest {

    @InjectMocks
    private PizzaAuthenticationProvider pizzaAuthenticationProvider = new PizzaAuthenticationProvider();

    @Mock
    private UserService userService;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void authenticateValidUserPassword() throws Exception {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn("test1234");
        when(userService.isUsernameAndPasswordValid(anyString(), anyString())).thenReturn(true);

        authentication = pizzaAuthenticationProvider.authenticate(authentication);

        assertThat(authentication.isAuthenticated(), is(true));
        assertThat(authentication.getAuthorities().size(), is(1));
        assertThat(authentication.getAuthorities().iterator().next().getAuthority(), is("ROLE_USER"));
    }

    @Test
    public void authenticateValidUserPasswordWithAdminRole() throws Exception {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn("test1234");
        when(userService.isUsernameAndPasswordValid(anyString(), anyString())).thenReturn(true);
        when(userService.isAdmin(anyString())).thenReturn(true);

        authentication = pizzaAuthenticationProvider.authenticate(authentication);

        assertThat(authentication.isAuthenticated(), is(true));
        assertThat(authentication.getAuthorities().size(), is(2));
        Iterator<? extends GrantedAuthority> iterator = authentication.getAuthorities().iterator();
        assertThat(iterator.next().getAuthority(), is("ROLE_USER"));
        assertThat(iterator.next().getAuthority(), is("ROLE_ADMIN"));
    }

    @Test
    public void authenticateInalidUserPassword() throws Exception {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getCredentials()).thenReturn("test1234");
        when(userService.isUsernameAndPasswordValid(anyString(), anyString())).thenReturn(false);

        authentication = pizzaAuthenticationProvider.authenticate(authentication);

        assertNull(authentication);
    }

    @Test
    public void supports() throws Exception {
        assertTrue(pizzaAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void supportsNot() throws Exception {
        assertFalse(pizzaAuthenticationProvider.supports(Object.class));
    }

}