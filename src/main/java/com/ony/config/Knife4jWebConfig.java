package com.ony.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Tony
 * @date 2021/8/19
 */
@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class Knife4jWebConfig {
    /**
     * 需配置 knife4j.enable: true
     */
    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("ONY接口")
                        .description("ONY接口")
                        .termsOfServiceUrl("https://127.0.0.1:9090/doc.html")
                        .version("0.0.1")
                        .build())
                .groupName("0.0.1版本")
                .select()
                // .apis(RequestHandlerSelectors.basePackage("com.ony.controller"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .extensions(openApiExtensionResolver.buildExtensions("0.0.1版本"));
    }
}