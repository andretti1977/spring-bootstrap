package com.ap.sp;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class Initializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(WebAppConfig.class);
		ctx.register(SecurityConfig.class);//diventa fondamentale registrare qui la classe sennò aggiungendo l'enableglobalsecurity si generano eccezioni

		ctx.setServletContext(servletContext);	

		configureSpringSecurity(servletContext, ctx);
		
		Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
		
	}
	
	/*
	 * La classe Initializer analoga per la sicurezza si trova sui link ma non ce ne è bisogno, configuro il bean di filterchain qui
	 * e poi nella SecurityConfig
	 */
	
	private void configureSpringSecurity(ServletContext servletContext, WebApplicationContext rootContext) {
	    FilterRegistration.Dynamic springSecurity = servletContext.addFilter("springSecurityFilterChain",
	        new DelegatingFilterProxy("springSecurityFilterChain", rootContext));
	    springSecurity.addMappingForUrlPatterns(null, true, "/*");
	  }
	
}