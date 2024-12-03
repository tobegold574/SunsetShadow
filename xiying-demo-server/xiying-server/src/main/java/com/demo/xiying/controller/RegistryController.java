    package com.demo.xiying.controller;

import com.demo.xiying.common.R;
import com.demo.xiying.model.request.RegistryRequest;
import com.demo.xiying.service.RegistryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
public class RegistryController {

    @Autowired
    private RegistryService registryService; //只需要autowire接口，实现会自己找的

    /**
     *注册
     */
    @PostMapping("/login/registry")
    public R addUser(@RequestBody RegistryRequest registryRequest){
        return registryService.addUser(registryRequest);
    }

    /**
     * 登录
     */
    @PostMapping("/login/enter")
    public R loginStatus(@RequestBody RegistryRequest registryRequest, HttpSession session){
        return registryService.loginStatus(registryRequest,session);
    }

}
