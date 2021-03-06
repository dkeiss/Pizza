package pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Daniel Keiss on 01.09.2016.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PizzaAuthenticationProvider pizzaAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/rest/**").permitAll() // TODO disable after development
                .antMatchers("/console/**").permitAll() // h2 console disable after development
                .anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout().permitAll().and().
                authorizeRequests();
        http.csrf().disable(); // todo find a way for crsf token with javascript!

        // to use H2 web console
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(pizzaAuthenticationProvider);
    }

}