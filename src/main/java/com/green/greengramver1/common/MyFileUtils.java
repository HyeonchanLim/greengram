package com.green.greengramver1.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component //빈등록
public class MyFileUtils {
    private final String uploadPath;

    /*
    @Value("${file.directory}")은
    yaml 파일에 있는 file.directory 속성에 저장된 값을 생성자 호출할 때 값을 넣어준다.
     */
    public MyFileUtils(@Value("${file.directory}") String uploadPath) {
        log.info("MyFileUtils - 생성자: {}", uploadPath);
        this.uploadPath = uploadPath;
    }
    // path = "ddd/aaa"
    // D:/2024-02/download/greengram_ver1/ddd/aaa : 뒤에 . ??? 확장자면이 없으면 폴더명
    //디렉토리 생성
    public String makeFolders(String path) {
        File file = new File(uploadPath, path); // 생성자 호출
        // file ( 부모 위치 경로 , 자식 위치 경로) , / 는 자동 생성
//        File file = new File(uploadPath + "/" + path);
//        인자 1개 - path 만 보냄 , 이렇게 되면 인자 하나만 받는 생성자도 있는걸 알 수 있음
//        static 아님 -> 객체화하고 주소값.(file.)으로 호출했기 때문에
//        메소드명은 exists , 파라미터는 없음 -> 호출 때 인자를 보내지 않았기 때문
        if(!file.exists()) { // exists -> 파일명 체크 / 리턴타입 boolean 타입 , void 면 불가능 why? - return - boolean 이라서
            // exists 로 빈 파일인지 체크 -> 아니면 file 생성
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
    //파일명에서 확장자 추출
    public String getExt(String fileName) {
        int lastIdx = fileName.lastIndexOf(".");
        return fileName.substring(lastIdx);
    }
    //랜덤파일명 생성
    public String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }
    //랜덤파일명 + 확장자 생성하여 리턴
    public String makeRandomFileName(String originalFileName) {
        return makeRandomFileName() + getExt(originalFileName);
        // return , +  조합으로 리턴 메소드인지 구분 가능
    }
    public String makeRandomFileName(MultipartFile file) {
        return makeRandomFileName(file.getOriginalFilename());
        // 원본 파일명을 가져와야만 해당 파일의 확장자를 추출 가능 ->
        // (file.getOriginalFilename()) -> () 안에 리턴하는 값인 데이터가 있어서 리턴 메소드다
        // 확장자 가져옴 위에서 getext 메소드에서 작업 -> 그다음 추출되었는 확장자명을 가져와서 작업
        // getOriginalFilename 원래 등록한 파일의 이름


    }
    //파일을 원하는 경로에 저장
    // 여기서 throws 쓰는 이유는 예외 처리를 내부에서 하기 보다는 외부에서 하는게 좋다
    // 그러면 가독성도 올라가고 유지보수성이 올라간다 + 호출자마다 예외를 처리하는 방식이 다를 수 있다.
    public void transferTo(MultipartFile mf, String path) throws IOException {
        File file = new File(uploadPath, path);
        mf.transferTo(file);
    }
}
class Test {
    public static void main(String[] args) {
        MyFileUtils myFileUtils = new MyFileUtils("C:/temp");
        String randomFileName = myFileUtils.makeRandomFileName("sdvkljsdfajkldsfjkldsfljk.png");
        System.out.println(randomFileName);
    }
}