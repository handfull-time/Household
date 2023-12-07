package com.utime.household.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

/**
 * 환경 정보 설정
 */
@Configuration
@PropertySource("classpath:/application.properties")
public class ViewResolverConfig implements WebMvcConfigurer { 
    
	@Autowired
	private Filter transactionFilter;
	
	@Resource(name="LogInterceptor")
	private AsyncHandlerInterceptor interceptorLog;
	
	/**
	 * 인터셉터 추가.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		final List<String> patterns = new ArrayList<>();
		patterns.add("/css/**");
		patterns.add("/js/**");
		patterns.add("/images/**");
		patterns.add("/vendor/**");
		
		final List<String> urls = new ArrayList<>();
//		urls.add("/Admin/Trade/**");
//		urls.add("/Admin/Mall/**");
//		urls.add("/Admin/Statistics/**");
//		urls.add("/Admin/Optional/**");
//		urls.add("/Admin/Manager/**");

		// 로그
		registry.addInterceptor( this.interceptorLog ).excludePathPatterns(patterns);
	}

	/**
	 * 로그 포함될 주소 입력<P>
	 *  /Member/Join.json 과 같이 Full 주소를 입력 하거나
	 *  /Member/*  처럼 [*] 처리를 한다. 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<Filter> logFilter() {
		
		final List<String> patterns = new ArrayList<>();
		
	    final FilterRegistrationBean<Filter> result = new FilterRegistrationBean<>();
	    result.setOrder(Ordered.HIGHEST_PRECEDENCE);
	    
	    result.setFilter( this.transactionFilter );
	    result.addUrlPatterns( patterns.toArray(new String[patterns.size()]) );
	    
	    return result;
	}

		 
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
    }
	
//	@Resource(name="MemberInfoArgumentResolver")
//	private HandlerMethodArgumentResolver memberInfoArgument;

    /**
     * Controller에서 별도 Object 형태의 파라미터를 전달 받을 때 사용함.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(this.memberInfoArgument);
    }

    
	@Bean
    public SpringResourceTemplateResolver templateResolver() {
        final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver ();
        
        templateResolver.setPrefix("classpath:templates/Household/");
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
