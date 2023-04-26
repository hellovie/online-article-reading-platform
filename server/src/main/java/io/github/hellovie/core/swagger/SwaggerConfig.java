package io.github.hellovie.core.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置类
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/26 13:49
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    /** 用于读取配置文件application.properties中swagger属性是否开启 */
    @Value("${swagger.enabled}")
    private Boolean swaggerEnabled;

    /** 接口调试地址 */
    @Value("${swagger.try-host}")
    private String host;

    /** 作者名 */
    @Value("${swagger.api-info.name}")
    private String name;

    /** 作者网站 */
    @Value("${swagger.api-info.url}")
    private String url;

    /** 作者邮箱 */
    @Value("${swagger.api-info.email}")
    private String email;

    /** 项目名 */
    @Value("${swagger.api-info.title}")
    private String title;

    /** 项目描述 */
    @Value("${swagger.api-info.description}")
    private String description;

    /** 项目版本 */
    @Value("${swagger.api-info.version}")
    private String version;

    /** 服务条款网址 */
    @Value("${swagger.api-info.terms-of-service-url}")
    private String termsOfServiceUrl;

    /** 许可 */
    @Value("${swagger.api-info.license}")
    private String license;

    /** 许可链接 */
    @Value("${swagger.api-info.license-url}")
    private String licenseUrl;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo())
                // 接口调试地址
                .host(host)
                // 是否开启swagger
                .enable(swaggerEnabled).select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket userModuleDocket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/users/**"))
                .build()
                .groupName("用户模块")
                .pathMapping("/");
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(name, url, email);
        return new ApiInfo(title, description, version, termsOfServiceUrl, contact, license, licenseUrl, new ArrayList());
    }

    /** 认证的安全上下文 */
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey("Authorization", "Authorization", "header"));
        return securitySchemes;
    }

    /** 授权信息全局应用 */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
}
