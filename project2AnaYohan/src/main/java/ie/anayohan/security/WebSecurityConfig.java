package ie.anayohan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().
			antMatchers("/flags/**","/css/**","/","/jobs/**","/bids/**","/activejobs/**","/inactivejobs/**","/job/**","/newuser/**").permitAll().
			antMatchers("/api/**").hasRole("API").
			antMatchers("/actuator/**").hasRole("ADMIN").
			antMatchers("/user/newbid").hasAnyRole("ADMIN", "USER").
			antMatchers("/user/newjob").hasAnyRole("ADMIN", "USER").
			anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().
			defaultSuccessUrl("/", true).usernameParameter("email").and().httpBasic().and().
			exceptionHandling().accessDeniedPage("/403"); // 403 error - no access to that page
	
		// To use h2 console
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	javax.sql.DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("SELECT user.userEmail, user.userPassword, user.userEnabled FROM User WHERE user.userEmail=?")
		.authoritiesByUsernameQuery("SELECT role.userEmail, role.roleDescription FROM Role WHERE role.userEmail=?");
	}
}