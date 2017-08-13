package com.mk.ssm.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by yankee on 2017/4/16.
 */
@EnableWebMvc
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.mk.ssm.api"})
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mk.ssm.web"))
                //过滤生成链接
                .paths(PathSelectors.any())
                .build();
    }
    //api接口作者相关信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("yankee", "http://ccyan.me", "ccyan@gmail.com");
        ApiInfo apiInfo = new ApiInfoBuilder().title("辅助系统").description("接口文档").contact(contact).version("1.0").build();
        return apiInfo;
    }
}
