package com.green.greengramver1.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(title = "피드 등록")
public class FeedPostReq {
    @JsonIgnore
    private long feedId;
    // long feedId 는 사용 x ! - why ? 프론트가 pk를 건들면 안돼 !
    @Schema(title = "로그인 유지 PK")
    private long writerUserId;
    @Schema(title = "내용")
    private String contents;
    @Schema(title = "위치")
    private String location;

}
