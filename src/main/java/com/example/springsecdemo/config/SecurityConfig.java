package com.example.springsecdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;


   @Autowired
   private UserDetailsService userDetailsService;



    //Whenever we pass username and password it becomes part of Authentication object
    //DaoAuthhenticationProvider indirectly implements AuthProvider
    //ok we got daoauthprovider but we need to pass information to it; which dbms we are usign, username password
    //provider is dependent on UserDetailsService, we just need to pass it correctly
    @Bean
    public AuthenticationProvider authProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;

    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

/* part 1        //disabling csrf
        httpSecurity.csrf(customizer -> customizer.disable());
        //we are making sure that we are enabling security for every request
        httpSecurity.authorizeHttpRequests(request -> request.anyRequest().authenticated());
        //we need to provide form
        //httpSecurity.formLogin(Customizer.withDefaults());
        //httpSecurity.httpBasic(Customizer.withDefaults());//security defaults

        //with above two lines we can enter normally with username and password; but after making stateless we'll remove those two lines
        //but we want to make it stateless so everytime we refresh the page it needs to give me new session id
        //
        httpSecurity.sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));*/

//following is a chained lambda way seems nicer part 3
        httpSecurity.csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request.
                        requestMatchers("register","login").permitAll().anyRequest().authenticated()) // requestMatchers("register").permitAll() by adding this we specified that every user can acces this path without logging in

                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
/*
imperative way(part 2) < lambda way (abovee, part 1) < chained lambda way (best, above)(part 3)
  Customizer<CsrfConfigurer<HttpSecurity>> custCsrf = new Customizer<CsrfConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CsrfConfigurer<HttpSecurity> configurer) {
                configurer.disable();

            }
        };

        httpSecurity.csrf(custCsrf);

        Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> custHttp =new Customizer<AuthorizeHttpRequestsConfigurer<org.springframework.security.config.annotation.web.builders.HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
            @Override
            public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
                registry.anyRequest().authenticated();
            }
        };
 httpSecurity.authorizeHttpRequests(custHttp);*/

        return httpSecurity.build();
    }

    //if we dont mention enything spring uses UserDetailService by default settings

    // there are multiple ways of authentication
    // Authentication Object gets checked by the Authenticaiton Provider which can be  configured in different ways

    //username and password is our auth object, it'll get checked by auth provider
    //since we want to configure auth provider, it will need to connect with database
    //and it will need some kind of connection with DAO layer
    //remember we need to use @Entity for data we save in database when we use JPA
    //Let's create that entity class


/*    //this method works with static values but we want to work with database
    @Bean
    public UserDetailsService userDetailsService() {
//think of it this way : we are creating Users and building it so that it becomes UserDetails object; we return InMemoryUserDetailsManager
        //now we can enter with this credentials

        *//*
        *
        *         UserDetails user = User
                .withDefaultPasswordEncoder()
                .username("ceyda")
                .password("n@123")
                .roles("USER")
                .build();
                *
                * return new InMemoryUserDetailsManager(user;
                * after doing this we will try it with database cause this is just hardcoded
        * *//*
        UserDetails user = User
                .withDefaultPasswordEncoder()
                .username("ceyda")
                .password("n@123")
                .roles("USER")
                .build();


        UserDetails admin = User
                .withDefaultPasswordEncoder()
                .username("admin")
                .password("admin@123")
                .roles("ADMIN")
                .build();


        return new InMemoryUserDetailsManager(user,admin);//this class indirectly implementing userdetailservice, by doing this app.properties does not get read; it also takes multiple users as UserDetails object


    }*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
