package com.fmi.course.spring.homework.one.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//
//    @Autowired
//    private RestSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/actuator/info").permitAll()
//                .antMatchers("/actuator/health").permitAll()
//                .antMatchers("/v2/api-docs").permitAll()
//                .antMatchers("/swagger*/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/**").authenticated()
//                .antMatchers(HttpMethod.POST, "/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.PUT).hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.DELETE).hasAnyRole("USER", "ADMIN")
//                .and()
//                .formLogin()
//                .permitAll()
//                .successHandler(authenticationSuccessHandler)
//                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
//                .and()
//                .logout();
////                .and()
////                    .rememberMe();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withDefaultPasswordEncoder()
//                .username("user").password("user")
//                .roles("USER").build());
//        return manager;
//    }


    //Cookie????


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/users").hasAnyRole("ADMIN")

                //next ones with authority?
                .antMatchers(HttpMethod.POST, "/api/users/{id}").hasAnyRole("BLOGGER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users/{id}").hasAnyRole("BLOGGER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/users/{id}").hasAnyRole("BLOGGER", "ADMIN")

                //next ones with authority?
                .antMatchers(HttpMethod.POST, "/api/posts/{id}").hasAnyRole("BLOGGER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/posts/{id}").hasAnyRole("BLOGGER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/posts/{id}").hasAnyRole("BLOGGER", "ADMIN")

                .and()
                .formLogin()
                .and()
                .httpBasic();

        //is logout working(in browser seems it is) but with a post request?
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("{noop}pass") // Spring Security 5 requires specifying the password storage format
//                .roles("BLOGGER");
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("user").password("user")
                .roles("BLOGGER").build());
        return manager;
    }


}
