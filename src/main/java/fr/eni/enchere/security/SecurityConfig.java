package fr.eni.enchere.security;

import fr.eni.enchere.user.bo.User;
import fr.eni.enchere.user.dal.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    ✔ 1 seule requête au login
//    ✔ User complet disponible partout
//    ✔ Pas d’appel BDD dans les controllers
//    ✔ Plus rapide et plus propre
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            // On va chercher l'utilisateur en base UNE SEULE FOIS (au login)
            User user = userRepository.findByPseudo(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable !"));

            UserPrincipal principal = new UserPrincipal();
            principal.setUser(user);
            return principal;
        };
    }

//     Ne contient que login + password + roles
//     Pas ton objet User
//     Tu dois refaire une requête BDD après
//     Moins flexible
// UserDetailsService basé uniquement sur JDBC (Spring Security "par défaut")

//UserDetailsService userDetailsService(DataSource dataSource) {
//
//    JdbcUserDetailsManager jdbc = new JdbcUserDetailsManager(dataSource);
//
//    jdbc.setUsersByUsernameQuery(
//            "SELECT pseudo, mot_de_passe, actif FROM utilisateurs WHERE pseudo=? AND actif = true"
//    );
//    jdbc.setAuthoritiesByUsernameQuery(
//            "SELECT pseudo, role FROM utilisateurs WHERE pseudo=?"
//    );
//    return jdbc;
//}


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("SUPER_ADMIN")
                        // Formulaire et création réservés aux connectés
                        .requestMatchers(HttpMethod.GET,"/encheres/create").authenticated()
                        .requestMatchers("/profile/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/auth").permitAll()
                        .loginProcessingUrl("/auth")
                        .usernameParameter("pseudo")
                        .passwordParameter("motDePasse")
                        .defaultSuccessUrl("/")
                        .failureUrl("/auth?error=true")
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutUrl("/deconnexion")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403")
                )
                .build();
    }
}
