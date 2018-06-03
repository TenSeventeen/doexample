package com.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
public class Config {
	
	@Autowired
	ThymeleafViewResolver thymeleafViewResolver;

	@Autowired
	Environment environment;
	
	@Value(value = "${spring.messages.basename}")
	private String basename;
    
	@Bean(name="messageSource")
	public ResourceBundleMessageSource getMessageSource(){
		ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
		messageSource.setBasename(basename);
		return messageSource;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.CHINA);
		return slr;
	}

	@Bean
	public LiteDeviceDelegatingViewResolver liteViewResolver() {
		LiteDeviceDelegatingViewResolver resolver = new LiteDeviceDelegatingViewResolver(thymeleafViewResolver);

		RelaxedPropertyResolver env = new RelaxedPropertyResolver(environment,
				"spring.mobile.devicedelegatingviewresolver.");

		resolver.setNormalPrefix(env.getProperty("normal-prefix", ""));
		resolver.setNormalSuffix(env.getProperty("normal-suffix", ""));
		resolver.setMobilePrefix(env.getProperty("mobile-prefix", "mobile/"));
		resolver.setMobileSuffix(env.getProperty("mobile-suffix", ""));
		resolver.setTabletPrefix(env.getProperty("tablet-prefix", "tablet/"));
		resolver.setTabletSuffix(env.getProperty("tablet-suffix", ""));
		resolver.setOrder(1);
		/* thymeleafViewResolver.getOrder() */
		resolver.setEnableFallback(true);
		return resolver;
	}

	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager, HttpServletRequest request) {

		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		resolvers.add(thymeleafViewResolver);
		resolvers.add(liteViewResolver());
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(resolvers);
		resolver.setContentNegotiationManager(manager);
		return resolver;

	}
}
