package com.green.greengramver1.user;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.user.model.UserSignInReq;
import com.green.greengramver1.user.model.UserSignInRes;
import com.green.greengramver1.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils myFileUtils;

    public int postSignUp(MultipartFile pic, UserSignUpReq p) {
        //프로필 이미지 파일 처리
        //String savedPicName = myFileUtils.makeRandomFileName(pic.getOriginalFilename());
        // 위와 같은 결과가 나왔으면 좋겠다.
        String savedPicName = (pic != null ? myFileUtils.makeRandomFileName(pic) : null);
        // makeRandomFileName(pic) pic 은 multiparttfile 메소드를 실행
        // 여기서 original 이름 + 확장자

        String hashedPassword = BCrypt.hashpw(p.getUpw(),BCrypt.gensalt());
        log.info("hashedPassword: {}" , hashedPassword);
        p.setUpw(hashedPassword);
        p.setPic(savedPicName);

        int result = mapper.insUser(p);
        // insuser 리턴값 (p) 영향 주는 친구 p 파라미터에서 pk 값을 가져옴

        if (pic == null){
            return result;
        }
        // user/${userId}/${savedPicName}
        // middlepath = user/${userId}
        // filepath = user/${userId}/${savedPicName}
        long userId = p.getUserId(); // user 를 insert 후에 얻을 수 있다.
        // 위에 mapper.insUser(p); 돌아온 pk 값을 여기서 참고
        String middlePath = String.format("user/%d", userId);
        // middilepath 가 usrid 에 pic 등록  ${user}/${userId} 여기 부분
        // 이게 xml 에서 작성한 useGeneratedKeys="true" keyProperty="userId" 이 부분
        myFileUtils.makeFolders(middlePath);
        log.info("middlePath: {}", middlePath);
        String filePath = String.format("%s/%s", middlePath, savedPicName);

        try {
            myFileUtils.transferTo(pic , filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
        // signup 으로 리턴
    }
    public UserSignInRes postSignIn(UserSignInReq p){
        UserSignInRes res = mapper.selUserForSignIn(p);
        if(res == null){
            res = new UserSignInRes();
            res.setMessage("아이디를 확인해 주세요");
            return res;
        }
        if( !BCrypt.checkpw(p.getUpw() , res.getUpw())) {
            // checkpw 메소드는 boolean 타입
            // 암호화 하지 않은 pw 값 - 암호화 됐는 pw 비교해서 true 인지 확인
            // false면 비밀번호 오류
            res = new UserSignInRes();
            res.setMessage("비밀번호를 확인해 주세요");
            return res;
        }
        res.setMessage("로그인 성공");

        return res;
    }
}