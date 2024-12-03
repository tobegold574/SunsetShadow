package com.demo.xiying.controller;

import com.demo.xiying.common.R;
import com.demo.xiying.model.request.ApplicationRequest;
import com.demo.xiying.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@CrossOrigin(origins = "*")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/application/send")
    public R sendApplication(@RequestBody ApplicationRequest applicationRequest){
        return applicationService.sendFriendRequest(applicationRequest);
    }

    @PostMapping("/application/update")
    public R updateApplication(@RequestBody ApplicationRequest applicationRequest){
        return applicationService.deleteApplication(applicationRequest);
    }

    @PostMapping("/application/list")
    public R getApplicationList(@RequestBody Map<String,Object> map){
        return applicationService.getApplicationList(Integer.parseInt(map.get("receiver_id").toString()));
    }
}
