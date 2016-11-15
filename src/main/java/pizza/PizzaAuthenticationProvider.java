package pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pizza.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Keiss on 11.09.2016.
 */
@Component
public class PizzaAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    public final static SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
    public final static SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        if (!userService.usernameExist(name)) {
            return null;
        }
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(ROLE_USER);
        if (userService.isAdmin(name)) {
            grantedAuths.add(ROLE_ADMIN);
            String password = getPassword(authentication);
            UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
            if (!isPasswordValid(name, password)) {
                passwordAuthenticationToken.setAuthenticated(false);
            }
            return passwordAuthenticationToken;
        }
        return new UsernamePasswordAuthenticationToken(name, null, grantedAuths);
    }

    private boolean isPasswordValid(String name, String password) {
        return StringUtils.hasText(password) && userService.isUsernameAndPasswordValid(name, password);
    }

    private String getPassword(Authentication authentication) {
        return authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
