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
        registry.addResourceHandler("/pic/**")
                // 클라이언트가 브라우저 또는 api로 요청을 보낼 때 경로 하위에 있는 모든 요청을 처리하도록 지정
                // ex) image1.jpg
                .addResourceLocations("file:" + uploadPath + "/");
                // 요청된 경로를 처리할 때 참조할 실제 파일 경로 지정
                // uploadpath 는 value 로 주입받은 경로 , 정적 리소스가 실제로 저장된 디렉토리를 가르킴
                // value - home/upload
                // home/upload/image1.jpg
                // home/upload 부분을 /pic 가
    }

    @Override
    // 빈 애노테이션 사용 -> 메소드 void 사용x , @Component 만 써도 괜찮음
    public void configurePathMatch(PathMatchConfigurer configurer){

        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
        // RestController 애노테이션이 있는 클래스에 api 를 prefix 해준다
    }
}
/*
여기서 swagger 랑 연결하는 부분을 작성하였고 webmvc 인터페이스를 상속받아서 메소드를 오버라이드 하였다.
그리고 1단계로 경로를 만들어주기 위해서 @value 를 작성하였고 uploadpath 파라미터 받아온걸로 주소를 넘겼다.
 그다음 registry 를 통해 받아온 데이터에서 handler 로 사진은 /pic/ ** 를 작성하였는데
 사진은 pic 다음 ** 아무꺼나 라는 조건부를 작성 + location 은 file: uploadpath / 이렇게 인데
 이거는 현재 작업한 내용 기준 localhost 가 file 에 들어가고 uploadpath 가 8080 에 해당하는 부분이다
  그다음 restcontroller 애노테이션이 있는 클래스에는 api 를 앞에 prefix 해주는건데 / 는 앞에 자동으로
   추가해준다
 */