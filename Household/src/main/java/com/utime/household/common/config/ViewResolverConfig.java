package com.utime.household.common.config;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import jakarta.annotation.Resource;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

/**
 * 환경 정보 설정
 */
@Configuration
@PropertySource("classpath:/application.properties")
public class ViewResolverConfig implements WebMvcConfigurer { 
    
	/**
	 * Static resources handler<P>
	 * 실제 위치와 서버 호출 주소를 매핑 시켜 준다.
	 */
	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/vendor/**").addResourceLocations("classpath:/static/vendor/");
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/");
    }
	
	@Resource(name="UserArgument")
	private HandlerMethodArgumentResolver userArgument;

    /**
     * Controller에서 별도 Object 형태의 파라미터를 전달 받을 때 사용함.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(this.userArgument);
    }
    
	@Bean
    public SpringResourceTemplateResolver templateResolver() {
        final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver ();
        
        templateResolver.setPrefix("classpath:templates/");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        templateResolver.setOrder(0);
        
        return templateResolver;
    }
    
    @Bean
    public SpringTemplateEngine templateEngine(MessageSource messageSource) {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        
        templateEngine.setEnableSpringELCompiler( true );
        templateEngine.setTemplateResolver( this.templateResolver());
        templateEngine.setTemplateEngineMessageSource(messageSource);
        templateEngine.addDialect(new LayoutDialect());
        
        return templateEngine;
    }
    
}
