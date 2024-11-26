package com.green.greengramver1.feed;

import com.green.greengramver1.common.model.ResultResponse;
import com.green.greengramver1.feed.model.FeedGetReq;
import com.green.greengramver1.feed.model.FeedGetRes;
import com.green.greengramver1.feed.model.FeedPostReq;
import com.green.greengramver1.feed.model.FeedPostRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("feed")
@Tag(name = "2. 피드", description = "피드 관리")
public class FeedController {
    private final FeedService service;

    @PostMapping
    public ResultResponse<FeedPostRes> postFeed(@RequestPart List<MultipartFile> pics ,
                                                @RequestPart FeedPostReq p){
        FeedPostRes res = service.postFeed(pics , p);

        return ResultResponse.<FeedPostRes>builder()
                .resultMessage("피드 등록 완료")
                .resultData(res)
                .build();
    }
    /*
    querystring - url 에 key value 값을 포함한다 .
    key=value&key2=value2 같이 url 작성시 빈칸 절대 금지! why? 빈칸도 데이터로 인식
    html 은 무조건 get 방식
    하이퍼텍스트 : 원하는 문서로 즉시 접근할 수 있는 텍스트(html) 이다.
    @ParameterObject , @RequestParam 처럼 form 만들어줌
    데이터를 받는 방식 2가지 - 쿼리스트링 or body(form data 이고 json이랑 다름) 데이터
     */
    @GetMapping
    public ResultResponse<List<FeedGetRes>> getFeedList(@ParameterObject @ModelAttribute FeedGetReq p){
        log.info("p: {}" , p);
        List<FeedGetRes> list = service.getFeedList(p);

        return ResultResponse.<List<FeedGetRes>>builder()
                .resultMessage(String.format("%d rows" , list.size()))
                .resultData(list)
                .build();
    }
    // http://localhost:8080/api/feed?size=10&page=20 앞에 api 붙여야함
    // why ? WebMvcConfiguration 클래스에서 api 를 붙이는 코드를 작성해놨음
}
