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
        myFileUtils.makeFolders(middlePath); // myfileutils 객체화 -> middlepath 경로로 파일 생성
        // 파일 저장
        FeedPicDto feedPicDto = new FeedPicDto(); // feedPicDto 에 feedId값 넣어주세요
        // postreq 에서 넘어온 데이터 p로 feedid 가져와서 setter 로 feedpicdto 에 저장
        feedPicDto.setFeedId(p.getFeedId());
        List<String> picsStr = new ArrayList<String>();
        // 폴더 생성 , 각 사진마다 파일에 저장하도록 for 문으로 반복 + throws 예외 처리까지
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
        /*
        picdto 객체화에 setter 로 pic에 이름을 넘김 -> mapper 로 pic에 이름 넘긴걸 쿼리스트링 매핑
        -> list 로 만든 piccstr 에 새로운 저장 파일 이름을 추가 add
         */
    }
    public List<FeedGetRes> getFeedList(FeedGetReq p){
        List<FeedGetRes> list = mapper.selFeedList(p);
        // n + 1 이슈 = 성능 저하 있어서 좋지 않음
        // feed list 가져오는 1회 + 튜플 6개 = 7번 조회 -> 성능 저하
        // but 가져올 select 가 많으면 나누는게 좋음 , 적으면 select 한번에 끝내는게 좋음
        //사진 매핑
        for (FeedGetRes res : list){
            //DB에서 각 피드에 맞는 사진 정보를 가져온다.
            List<String> picList = mapper.selFeedPicList(res.getFeedId());
            res.setPics(picList);
        }
        return list;
    }
}



