package com.green.greengramver1.feed;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final MyFileUtils myFileUtils;

    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {
        int result = mapper.insFeed(p);
        // 파일 저장
        // 위치 : feed/#{feedId}/
        long feedId = p.getFeedId();
        String middlePath = String.format("feed/%d", p.getFeedId());
        log.info("middle: {}", middlePath);
        //폴더 만들기
        myFileUtils.makeFolders(middlePath);
        // 파일 저장
        FeedPicDto feedPicDto = new FeedPicDto(); // feedPicDto 에 feedId값 넣어주세요
        feedPicDto.setFeedId(p.getFeedId());
        List<String> picsStr = new ArrayList<String>();
        // 폴더 생성
        for (MultipartFile pic : pics) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            // 사진 이름 다르게 바뀌도록 랜덤 이름 출력
            String filePath = String.format("%s/%s", middlePath, savedPicName);
            try {
                myFileUtils.transferTo(pic, filePath);
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
            feedPicDto.setPic(savedPicName);
            mapper.selUserForSignIn(feedPicDto);
            picsStr.add(savedPicName);
        }
        FeedPostRes res = new FeedPostRes();
        res.setFeedId(p.getFeedId());
        res.setPics(picsStr);
        return res;
    }
    public List<FeedGetRes> getFeedList(FeedGetReq p){
        List<FeedGetRes> list = mapper.selFeedList(p);
        for (FeedGetRes res : list){
            List<String> picList = mapper.selFeedPicList(res.getFeedId());
            res.setPics(picList);
        }
        return list;
    }
}