package com.demo.xiying.service.Impl;

import com.demo.xiying.service.MatchService;
import com.demo.xiying.model.request.MatchRequest;
import com.demo.xiying.common.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.xiying.mapper.ProfileMapper;
import com.demo.xiying.service.WebclientService;
import com.demo.xiying.model.domain.BestCandidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.xiying.model.domain.Profile;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {


    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private WebclientService webclientService;

    @Override
    public R match(MatchRequest matchRequest) {
        QueryWrapper<Profile> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("sex", matchRequest.getSex());
        queryWrapper1.eq("location", matchRequest.getLocation());
        System.out.println(matchRequest.getSex());
        System.out.println(matchRequest.getLocation());
       
        QueryWrapper<Profile> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id", matchRequest.getUser_id());

        Profile profile = profileMapper.selectOne(queryWrapper2);

        List<Profile> candidates = profileMapper.selectList(queryWrapper1);
        System.out.println(candidates);

        double bestScore=0;
        Integer bestId=0;
        for(Profile candidate : candidates){
            if(candidate.getUser_id()==matchRequest.getUser_id()){
                continue;
            }
           double similarity =   webclientService.getSimilarity(profile.getIntroduction(),candidate.getIntroduction());

           if(similarity > bestScore){
            bestScore = similarity;
            bestId = candidate.getUser_id();
           }
        }
        BestCandidate bestCandidate = new BestCandidate();
        bestCandidate.setBestId(bestId);
        bestCandidate.setBestScore(bestScore);
        if(bestId==0){
            return R.error("没有找到匹配的候选人");
        }
        return R.success("匹配成功",bestCandidate);
    }
}
