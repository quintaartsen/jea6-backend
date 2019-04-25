package twitter.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecurityUserDetailsService userDetailsService;

    public SecurityConfig(BCryptPasswordEncoder passwordEncoder, SecurityUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors()
            .and()
            .csrf().disable()
            .formLogin()
            .loginPage("/login.xhtml").permitAll()
            .defaultSuccessUrl("/dashboard.xhtml", true)
            .failureUrl("/login.xhtml?inCorrectLogin=true")
            .and()
            .logout()
            .logoutSuccessUrl("/login.xhtml")
            .deleteCookies("JSESSIONID")
            .and()
            .addFilter(new TokenAuthenticationFilter(authenticationManagerBean()))
            .addFilterAfter(new TokenAuthorizationFilter(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/dashboard*").hasRole("ADMIN")
            .anyRequest().authenticated()
            .antMatchers(HttpMethod.POST, TokenConstrains.getTOKEN_URL()).permitAll()
            .antMatchers(HttpMethod.POST, TokenConstrains.getSIGNUP_URL()).permitAll()
            .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}