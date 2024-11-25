package com.green.greengramver1.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
// builder 사용한 클래스 사용할 경우 builder() ~ build() 사용 + ~ 부분에서 멤버필드 전부 사용

public class ResultResponse<T> {
    @Schema(title = "결과 메시지")
    private String resultMessage;
    @Schema(title = "결과 내용")
    private T resultData;

//    @Builder.Default
//    private int count = 0;
//    private String status = "ss";
//     Builder.Default 사용 -> 기본값(Default) 적용 -> 해당 클래스 부를 경우
//     ~ 부분에서 Default 값은 제외해도 괜찮음

}