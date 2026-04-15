package dev._xdbe.booking.creelhouse.infrastructure.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(auth
                               -> auth
                                      // Step 4a: add access control
                                      .requestMatchers("/dashboard")
                                      .hasRole("ADMIN")
                                      // Step 4a: end
                                      .anyRequest()
                                      .permitAll())
        // Step 4b: Add login form
        .formLogin(withDefaults())
        // Step 4b: End of login form configuration

        .csrf((csrf) -> csrf.ignoringRequestMatchers("/h2-console/**"))
        .headers(
            headers
            -> headers.frameOptions(frameOptions -> frameOptions.disable()))
        .build();
  }

  // Step 3: add InMemoryUserDetailsManager
  @Bean

  public UserDetailsService userDetailsService() {

    UserDetails admin =

        User.builder()

            .username("admin")

            .password("{bcrypt}$2a$10$PVtPds1kSNQlYFHAZs4xe.YK.oRrEJgLw4KsN/"
                      +

                      "jSRNzJ4kn/Ac6ZS") // hash bcrypt

            .roles("ADMIN")

            .build();

    UserDetails guest =

        User.builder()

            .username("guest")

            .password("{bcrypt}$2a$10$k.ySNsMOKbsXAUp2Ka.UK.HST2kcdPI3f/"
                      +

                      "GaXzA899XQDxVUKg956") // hash bcrypt

            .roles("GUEST")

            .build();

    return new InMemoryUserDetailsManager(admin, guest);
  }
  // Step 3: end
}
