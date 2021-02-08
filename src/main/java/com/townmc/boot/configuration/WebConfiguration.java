package com.townmc.boot.configuration;

import com.townmc.boot.web.AccessTokenArgumentResolver;
import com.townmc.boot.web.DefaultInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.townmc.boot.constants.SystemConstants.AUTHORIZATION_KEY;

/**
 * 加载配置web相关
 * 拦截器、跨域访问等等
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.defaultInterceptorInstance()).addPathPatterns("/**");
    }

    /**
     * 允许跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("Content-Type", "Accept", "X-Requested-With", "Access-Token", "Session-Id", AUTHORIZATION_KEY)
                .maxAge(3600L)
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }


    /**
     * Controller 方法参数注入
     *
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(new AccessTokenArgumentResolver());
    }

    @Bean
    public DefaultInterceptor defaultInterceptorInstance() {
        return new DefaultInterceptor();
    }

}
