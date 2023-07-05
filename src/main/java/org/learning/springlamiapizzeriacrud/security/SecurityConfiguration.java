package org.learning.springlamiapizzeriacrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    /* per definire un AuthenticationProvider ho bisogno di:
      - uno UserDetailsService
      - un PasswordEncoder
     */
    // questo è lo UserDetailsService
    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    // questo è il PasswordEncoder (che deduce l'algoritmo di encoding da una stringa nella password
    // stessa)
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        // creo l'authenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // gli setto il PasswordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        // gli setto lo UserDetailsService
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // definisco la catena di filtri
        http.authorizeHttpRequests()
                .requestMatchers("/ingredients").hasAuthority("ADMIN")
                .requestMatchers("/pizza/edit/**").hasAuthority("ADMIN")
                .requestMatchers("/pizza/create").hasAuthority("ADMIN")
                .requestMatchers("/pizza/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/offers/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/pizza/**").hasAuthority("ADMIN")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .defaultSuccessUrl("/pizza")
                .and().logout();
        http.csrf().disable();

        return http.build();

    }
}