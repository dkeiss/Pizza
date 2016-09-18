package pizza.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pizza.domain.PasswordType;
import pizza.domain.User;
import pizza.repositories.UserRepository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Daniel Keiss on 12.09.2016.
 */
public class UserServiceTest {

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @Before
    public void before() {
        initMocks(this);

        when(userRepository.findByUsername("alias")).thenReturn(user);
    }

    @Test
    public void aliasAndPlainPasswordAreValid() throws Exception {
        when(user.getPasswordType()).thenReturn(PasswordType.PLAIN);
        when(user.getPassword()).thenReturn("password");

        boolean aliasAndPasswordValid = userService.isUsernameAndPasswordValid("alias", "password");

        assertThat(aliasAndPasswordValid, is(true));
    }

    @Test
    public void aliasValidAndPlainPasswordInvalid() throws Exception {
        when(user.getPasswordType()).thenReturn(PasswordType.PLAIN);
        when(user.getPassword()).thenReturn("password");

        boolean aliasAndPasswordValid = userService.isUsernameAndPasswordValid("alias", "123456");

        assertThat(aliasAndPasswordValid, is(false));
    }

    @Test
    public void aliasInvalid() throws Exception {
        boolean aliasAndPasswordValid = userService.isUsernameAndPasswordValid("dog", "iluvcatz");

        assertThat(aliasAndPasswordValid, is(false));
    }

    @Test
    public void isNoAdmin() throws Exception {
        assertFalse(userService.isAdmin("alias"));
    }

    @Test
    public void isAdmin() throws Exception {
        when(user.isAdmin()).thenReturn(true);

        assertTrue(userService.isAdmin("alias"));
    }

}