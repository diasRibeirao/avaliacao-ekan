package br.com.ekan.avaliacao.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private SecurityFilter securityFilter;

	private SecurityUnauthorizedHandler unauthorizedHandler;

	public SecurityConfig(SecurityFilter securityFilter, SecurityUnauthorizedHandler unauthorizedHandler) {
		this.securityFilter = securityFilter;
		this.unauthorizedHandler =unauthorizedHandler;
	}

	private static final String[] PUBLIC_MATCHERS = {
			"/v1/auth/registrar",
			"/v1/auth/login",
			"/h2-console/**",
			"/v3/api-docs/**",
			"/swagger-ui/**",
			"/swagger-ui.html"
		};

	@Bean
    SecurityFilterChain setSecurityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_MATCHERS).permitAll()
						.requestMatchers(HttpMethod.POST, "/v1/beneficiarios").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/v1/beneficiarios").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/v1/beneficiarios").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(this.securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers("/h2-console/**");
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}