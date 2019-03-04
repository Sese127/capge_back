package capgemini.stage.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JWTFilter jwtFilter;	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable().formLogin().disable()
		.authorizeRequests()
		.antMatchers("/ano/**","/admin/**").permitAll()
		.antMatchers("/", "/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
				"/swagger-resources/configuration/security", "/swagger-ui.html", "/employee","/webjars/**")	.permitAll()
		//.antMatchers("/admin").access("hasRole('ROLE_admin')")
		.anyRequest().authenticated().and()
		.httpBasic().and()
		.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
					
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
