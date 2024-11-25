package com.green.greengramver1.feed.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
public class FeedGetRes {
    private long feedId;
    private long writerId;
    private String contents;
    private String location;
    private String writerNm;
    private String writerPic;
    private String createdAt;

    private List<String> pics;
}
