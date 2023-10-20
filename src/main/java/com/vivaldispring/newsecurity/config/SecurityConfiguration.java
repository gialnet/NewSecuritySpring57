package com.vivaldispring.newsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final ShouldAuthenticateAgainstThirdPartySystem shouldAuth;

    private final LoggingAccessDeniedHandler accessDeniedHandler;
    public SecurityConfiguration(ShouldAuthenticateAgainstThirdPartySystem shouldAuth, LoggingAccessDeniedHandler accessDeniedHandler) {
        this.shouldAuth = shouldAuth;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authz) -> {
                            try {
                                authz
                                        .anyRequest().authenticated()
                                        .and().formLogin().
                                        loginPage("/login").
                                        defaultSuccessUrl("/",true)
                                        .permitAll()
                                        .and()
                                        .logout()
                                        .invalidateHttpSession(true)
                                        .clearAuthentication(true)
                                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                        .logoutSuccessUrl("/login?logout")
                                        .permitAll()
                                        .and()
                                        .exceptionHandling()
                                        .accessDeniedHandler(accessDeniedHandler)
                                        ;
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )

                .authenticationManager(new CustomAuthenticationManager(shouldAuth));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/register");
    }

}