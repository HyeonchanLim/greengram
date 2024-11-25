package com.green.greengramver1.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
@Component
@Configuration // 싱글톤 (객체 주소값 돌려 씀) , 싱글톤이면 shallow copy
// 메서드에 @bean 을 붙여주지 않으면 bean 등록을 하지 않는다 , 한번만 인스턴스화
// 다만 싱글톤으로 관리되며 내부 메서드 호출 시 proxy 를 통해 객체(싱글톤)를 공유하게 됩니다.
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final String uploadPath;

    //@Value 는 lombok 말고 spring 으로 해야 함 !!
    public WebMvcConfiguration(@Value("${file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/pic/**").addResourceLocations("file:" + uploadPath + "/");
    }

    @Override
    // 빈 애노테이션 사용 -> 메소드 void 사용x , @Component 만 써도 괜찮음
    public void configurePathMatch(PathMatchConfigurer configurer){

        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
        // RestController 애노테이션이 있는 클래스에 api 를 prefix 해준다
    }
}
