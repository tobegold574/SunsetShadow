package com.demo.xiying.service.Impl;

import com.demo.xiying.common.R;
import com.demo.xiying.mapper.PicMapper;
import com.demo.xiying.model.domain.Pic;
import com.demo.xiying.model.request.PicRequest;
import com.demo.xiying.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class PicServiceImpl extends ServiceImpl<PicMapper, Pic> implements PicService{
    @Autowired
    private PicMapper picMapper;

    @Override
    public R setIntroduction(PicRequest picRequest){
        Pic pic=new Pic();
        BeanUtils.copyProperties(picRequest, pic);
        
        if(picMapper.insert(pic)>0){
            return R.success("上传成功");
        }
        return R.error("上传失败");
    }
}
