package com.green.greengramver1.feed.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedPicDto {
    // data transfer object
    @Schema(title = "피드 ID")
    private long feedId;
    @Schema(title = "사진")
    private String pic;
}
