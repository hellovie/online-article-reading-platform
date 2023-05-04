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
 * Swagger3 配置类. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/26 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    /** 用于读取配置文件 application.properties 中 swagger 属性是否开启. */
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

    /**
     * 配置默认 Docket.
     *
     * @return Swagger3 Docket.
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo())
                // 接口调试地址
                .host(host)
                // 是否开启swagger
                .enable(swaggerEnabled).select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build().securitySchemes(securitySchemes()).securityContexts(securityContexts());
    }

    /**
     * 配置用户模块 Docket.
     *
     * @return Swagger3 Docket.
     */
    @Bean
    public Docket userModuleDocket() {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.ant("/users/**")).build().groupName("用户模块").pathMapping("/");
    }

    /**
     * 配置网站的基本信息.
     *
     * @return Swagger3 ApiInfo.
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact(name, url, email);
        return new ApiInfo(title, description, version, termsOfServiceUrl, contact, license, licenseUrl, new ArrayList());
    }

    /**
     * 认证的安全上下文.
     *
     * @return 安全方案 SecurityScheme.
     */
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey("Authorization", "Authorization", "header"));
        return securitySchemes;
    }

    /**
     * 授权信息全局应用.
     *
     * @return SecurityContext.
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build());
        return securityContexts;
    }

    /**
     * 默认身份验证 Authorization token.
     *
     * @return SecurityReference.
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
}
