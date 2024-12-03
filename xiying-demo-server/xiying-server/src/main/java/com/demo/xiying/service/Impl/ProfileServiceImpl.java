package com.demo.xiying.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.xiying.common.R;
import com.demo.xiying.mapper.ProfileMapper;
import com.demo.xiying.model.domain.Profile;
import com.demo.xiying.model.request.ProfileRequest;
import com.demo.xiying.service.ProfileService;



import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl extends ServiceImpl<ProfileMapper, Profile>
    implements ProfileService {
    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public R updateInfo(ProfileRequest profileRequest){
        Profile profile=new Profile();

        BeanUtils.copyProperties(profileRequest,profile);

        UpdateWrapper<Profile> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("user_id",profileRequest.getUser_id());
        System.out.println(profile.getAvatar());

        if(profileMapper.update(profile,updateWrapper)>0) return R.success("个人信息成功修改");
        else if(profileMapper.insert(profile)>0) return R.success("初始个人信息设置成功");
        else return R.error("个人信息修改失败");
    }


    @Override
    public R getInfo(Integer user_id) {
        QueryWrapper<Profile> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user_id);

        try {
            Profile profile = profileMapper.selectOne(queryWrapper);
            if (profile != null) {
                return R.success("找到用户（更新后）的信息", profile);
            } else {
                return R.error("未找到用户信息");
            }
        } catch (Exception e) {
            return R.error("获取用户信息失败: " + e.getMessage());
        }
    }

    
}