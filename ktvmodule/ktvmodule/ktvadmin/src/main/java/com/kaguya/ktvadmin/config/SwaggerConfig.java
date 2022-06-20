package com.kaguya.ktvadmin.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: Linwei
 * @date 2021/6/15
 * @Description:
 * 配置类和以前使用swagger几乎是一样;
 * 注解比原来的swagger多加一个 @EnableSwaggerBootstrapUi
 */

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("1.0版本")
                .select()
                //这里指定Controller扫描包路径(项目路径也行)
                .apis(RequestHandlerSelectors.basePackage("com.kaguya.ktvadmin"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("XXX项目API接口文档")
                .description("API服务相关接口")
                .termsOfServiceUrl("http://localhost:8080/")
                .contact(new Contact("Linwei",null,"Linwei.Lu@123456.com"))
                .version("1.0")
                .build();
    }
}