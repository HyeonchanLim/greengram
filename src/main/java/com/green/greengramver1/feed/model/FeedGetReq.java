package com.green.greengramver1.feed.model;

import com.green.greengramver1.common.model.Paging;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 상속받아서 getter 생략가능 +
// 상속받은 클래스에서 startidx 를 생성자에서 계산을 다 했기 때문에 setter 생략 가능
public class FeedGetReq extends Paging {
    public FeedGetReq (Integer page , Integer size){
        super(page,size);
    }
}
