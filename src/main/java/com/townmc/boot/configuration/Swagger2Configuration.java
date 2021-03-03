package com.townmc.boot.configuration;

import com.townmc.boot.constants.SystemConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
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
public class Swagger2Configuration {
    private static final String BASE_PACKAGE = "com.townmc.boot";
    private static final String DOC_TITLE = "api文档";
    private static final String DOC_VERSION = "1.0";

    @Bean
    public Docket initSwagger() {

        List<Parameter> pars = new ArrayList<Parameter>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(SystemConstants.AUTHORIZATION_KEY)
                .defaultValue(SystemConstants.DEBUG_TOKEN)
                .description("用户访问令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(DOC_TITLE)
                .description(DOC_TITLE)
                .version(DOC_VERSION)
                .build();
    }
}
