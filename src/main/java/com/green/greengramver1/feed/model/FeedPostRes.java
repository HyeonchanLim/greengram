package com.green.greengramver1.feed.model;

import com.green.greengramver1.common.MyFileUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
public class FeedPostRes {
    // feed PK값과 파일이름 여러개 리턴할 수 있어야 함.
    private long feedId;
    private List<String> pics;
}
