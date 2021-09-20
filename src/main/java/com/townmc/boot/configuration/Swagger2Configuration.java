package com.townmc.boot.configuration;

import com.townmc.boot.constants.SystemConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meng
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration implements WebMvcConfigurer {
    private static final String BASE_PACKAGE = "com.townmc.boot";
    private static final String DOC_TITLE = "api文档";
    private static final String DOC_VERSION = "1.0.0";
    @Value("${swagger.enable}")
    private Boolean enable;
    @Value("${swagger.test-auth}")
    private String testAuth;

    @Bean
    public Docket initSwagger() {
        RequestParameterBuilder aParameterBuilder = new RequestParameterBuilder();
        aParameterBuilder.name(SystemConstants.AUTHORIZATION_KEY)
                .query(q -> q.defaultValue(testAuth)
                        .model(modelSpecificationBuilder -> modelSpecificationBuilder.scalarModel(ScalarType.STRING)))
                .in(ParameterType.HEADER).required(true).build();

        List<RequestParameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.regex("(?!/error.*).*")).build().apiInfo(apiInfo())
                .globalRequestParameters(aParameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(DOC_TITLE)
                .description(DOC_TITLE)
                .version(DOC_VERSION)
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( "/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController( "/swagger-ui/")
                .setViewName("forward:" +  "/swagger-ui/index.html");
    }

}
