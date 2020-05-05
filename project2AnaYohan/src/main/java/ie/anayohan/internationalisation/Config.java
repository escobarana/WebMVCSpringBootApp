package ie.anayohan.internationalisation;

import java.util.Locale;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Configuration
public class Config implements WebMvcConfigurer {
	
	//Using /index?lang=esp and /index?lang=fr system along with cookies to remember the user's choice
	
	// To enable the project to determine the current locale and to use session variables
	/*@Bean
	public SessionLocaleResolver localeResolver() {
		
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.UK);
		return localeResolver;
	}*/
	
	// To enable the project to persist in the preferred language an to use cookies
	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.UK);
		return localeResolver;
	}
	
	// Registry the interceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor()); // Injection into the registry
	}
	
	// To enable the project to switch language
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}
	
	//	<!--  adapted from https://stackoverflow.com/questions/27623405/thymeleaf-add-parameter-to-current-url -->
	
	@Bean
	public Function<String, String> currentUrlWithoutParam() {
		return param -> ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(param).toUriString();
	}
}
