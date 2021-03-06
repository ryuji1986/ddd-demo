package org.ryuji.ddd.demo.project.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.ryuji.ddd.demo.project.common.Constants;
import org.ryuji.ddd.demo.infrastructure.event.interceptor.AccessLimtInterceptor;
import org.ryuji.ddd.demo.infrastructure.event.interceptor.JwtHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;
import java.util.TimeZone;


@EnableScheduling
@Configuration
@Slf4j
public class MyConvertConfig extends WebMvcConfigurationSupport {

//    @Autowired
//    private JwtHandlerInterceptor jwtHandlerInterceptor;

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders(
                        Constants.RequestHeaderKey.TOKEN,
                        "Content-Type");
    }

    /* js long type bug */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        /***
         * ???????????????????????????????????????????????????
         * ?????????string???????????????????????????json?????????
         */
        converters.add(stringHttpMessageConverter());

        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        /**
         * ????????????json???,????????????long??????string ??????js???????????????????????????????????????java long???
         */
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);

        converters.add(jackson2HttpMessageConverter);
    }

    /* swagger */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(newJwtHandlerInterceptor())
                // ???????????????????????????
                .addPathPatterns("/login/**")
                .addPathPatterns("/trade/**")
                .addPathPatterns("/home/**")
                .addPathPatterns("/record/**")
                .addPathPatterns("/social/**")
                .addPathPatterns("/im/**")
                .addPathPatterns("/phrases/**")
                .addPathPatterns("/wallet/**")
                .addPathPatterns("/call/**")
                .addPathPatterns("/cert/**")
                .addPathPatterns("/user/**");
        // ????????????????????????????????????????????????controller??????????????? @PassToken ??????

        registry.addInterceptor(getAccessLimtInterceptor()).addPathPatterns("/debug/**");

        super.addInterceptors(registry);
    }

    @Bean
    public AccessLimtInterceptor getAccessLimtInterceptor() {
        return new AccessLimtInterceptor();
    }

    @Bean
    public JwtHandlerInterceptor newJwtHandlerInterceptor() {
        return new JwtHandlerInterceptor();
    }
}
