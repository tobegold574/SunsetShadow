package com.demo.xiying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.xiying.common.R;
import com.demo.xiying.model.domain.Application;
import com.demo.xiying.model.request.ApplicationRequest;

public interface ApplicationService extends IService<Application>{
    public R getApplicationList(Integer receiver_id);

    public R sendFriendRequest(ApplicationRequest applicationRequest);

    public R deleteApplication(ApplicationRequest applicationRequest);
}
