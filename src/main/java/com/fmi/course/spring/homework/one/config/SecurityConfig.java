package com.fmi.course.spring.homework.one.config;


import com.fmi.course.spring.homework.one.security.RestAuthenticationEntryPoint;
import com.fmi.course.spring.homework.one.security.RestSavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    RestSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/users").hasAnyRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/posts/**").hasAnyRole("ADMIN", "BLOGGER")
                //next ones with authority?
                .antMatchers(HttpMethod.POST, "/api/users/{id}").hasAnyRole("BLOGGER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users/{id}").hasAnyRole("BLOGGER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/users/{id}").hasAnyRole("BLOGGER", "ADMIN")

                //next ones with authority?
                .antMatchers(HttpMethod.POST, "/api/posts/{id}").hasAnyRole("BLOGGER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/posts/{id}").hasAnyRole("BLOGGER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/posts/{id}").hasAnyRole("BLOGGER", "ADMIN")

                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler());
//                .and()
//                .httpBasic();

    }


    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("user").password("user")
                .roles("ADMIN").build());
        return manager;
    }


}
