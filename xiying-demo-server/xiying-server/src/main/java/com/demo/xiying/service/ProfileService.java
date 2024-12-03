package com.demo.xiying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.xiying.common.R;
import com.demo.xiying.model.domain.Profile;
import com.demo.xiying.model.request.ProfileRequest;

public interface ProfileService extends IService<Profile> {
    public R updateInfo(ProfileRequest profileRequest);

    public R getInfo(Integer user_id);

}
