package com.green.greengramver1.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
// 이뮤터블 방식 -> setter x & final 멤버 & private
public class Paging {
    // final static 은 JsonIgnore 없어도 데이터가 안들어감
    private final static int DEFAULT_PAGE_SIZE = 20;

    @Schema(example = "1", description = "Selected Page")
    private int page;
    @Schema(example = "30", description = "item count per page")
    private int size;
    @JsonIgnore
    private int startIdx;

    public Paging (Integer page , Integer size){
        this.page=page==null || page <= 0 ? 1 : page;
        this.size= size == null || size <= 0 ? DEFAULT_PAGE_SIZE : size;
        this.startIdx = (this.page-1)*this.size;
    }
    // @Setter 를 쓰면 생성자 or setter 를 쓰는데 setter 가 우선순위라 생성자 데이터는 제외 당함
}