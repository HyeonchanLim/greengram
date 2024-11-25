package com.green.greengramver1.user;

import com.green.greengramver1.common.model.ResultResponse;
import com.green.greengramver1.user.model.UserSignInReq;
import com.green.greengramver1.user.model.UserSignInRes;
import com.green.greengramver1.user.model.UserSignUpReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Tag(name = "유저", description = "회원가입, 로그인")
public class UserController {
    private final UserService service;

    /*
        파일(MultipartFile) + Data
        파일 업로드시에는 RequestBody를 사용할 수 없음.
        RequestPart애노테이션 사용해야 함
        (required = true) null 값 허용 여부 조건부 - true 면 무조건 필수적으로 받아야 함
        false 라면 안받으면 default 값을 자동으로 넣어주고 null값도 허용해줌
        DefaultValue 사용 -> required 작성 안하면 false 가 기본값으로 들어오고
        DefaultValue , required 없으면 true 가 기본값으로 설정
     */
    // MultiPartFile 은 인터페이스 -> 이거를 밑에 파라미터로 받으면서 인터페이스를 구현함
    @PostMapping("sign-up")
    @Operation(summary = "회원 가입")
    public ResultResponse<Integer> signUp(@RequestPart UserSignUpReq p
            , @RequestPart(required = false) MultipartFile pic
    ) {
        log.info("UserInsReq: {}, file: {}", p, pic != null ? pic.getOriginalFilename() : null);
        // pic.getOriginalFilename() 여기서 3항식으로 null 해놓은 이유는 pic 메소드 호출 -> null 일 경우
        // 에러 발생하기 때문에 그걸 막을려고 null 값 허용하는 조건을 만듦
        int result = service.postSignUp(pic, p);
        return ResultResponse.<Integer>builder()
                .resultMessage("회원가입 완료")
                .resultData(result)
                .build();
    }
    @PostMapping("sign-in")
    @Operation(summary = "로그인")
    public ResultResponse<UserSignInRes> singIn(@RequestBody UserSignInReq p){
        UserSignInRes res = service.postSignIn(p);
        return ResultResponse.<UserSignInRes>builder()
                .resultMessage(res.getMessage())
                .resultData(res)
                .build();
    }
}