package com.demo.xiying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.xiying.common.R;
import com.demo.xiying.model.domain.Pic;
import com.demo.xiying.model.request.PicRequest;

public interface PicService extends IService<Pic>{
    public R setIntroduction(PicRequest picRequest);
}
