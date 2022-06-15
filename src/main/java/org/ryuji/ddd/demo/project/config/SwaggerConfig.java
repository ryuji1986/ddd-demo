package org.ryuji.ddd.demo.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Profile({"dev", "test", "pre", "local"})
@Configuration
@EnableOpenApi
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket productApi() {
        //添加head参数start
        RequestParameterBuilder tokenPar = new RequestParameterBuilder();
        List<RequestParameter> pars = new ArrayList<RequestParameter>();
        //这个位置设置请求头：比如令牌
        tokenPar.name("token").description("令牌")
                .in("header")
                .required(false)
                .build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2).select()
                // 扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("org.ryuji.ddd.demo.api.controller"))
                // 定义要生成文档的Api的url路径规则
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(pars)
                // 设置swagger-ui.html页面上的一些元素信息。
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                // 标题
                .title("Swagger3 Document")
                // 描述
                .description("项目接口文档")
                // 文档版本
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
