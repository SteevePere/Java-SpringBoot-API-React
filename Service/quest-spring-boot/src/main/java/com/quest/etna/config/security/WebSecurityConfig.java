package com.quest.etna.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private UserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
		throws Exception
	{
		auth.userDetailsService(jwtUserDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean()
		throws Exception
	{
		return super.authenticationManagerBean();
	}
	
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter()
	{
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(0);
		
		return bean;
	}
	
	
	@Override
	protected void configure(HttpSecurity httpSecurity)
		throws Exception
	{
		httpSecurity.csrf().disable()
			.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().and()
			.authorizeRequests().antMatchers("/register").permitAll().and()
			.authorizeRequests().antMatchers(
				"/v3/api-docs/**",
				"/swagger-ui.html",
				"/swagger-ui/**",
				"/swagger-resources",
				"/swagger-resources/**",
				"/swagger/**"
			).permitAll().and()
			.authorizeRequests().antMatchers("/authenticate").permitAll()
			.anyRequest().authenticated().and()
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSecurity.addFilterBefore(
			jwtRequestFilter,
			UsernamePasswordAuthenticationFilter.class
		);
	}
}