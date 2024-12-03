package com.demo.xiying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.xiying.common.R;
import com.demo.xiying.model.domain.Registry;
import com.demo.xiying.model.request.RegistryRequest;
import jakarta.servlet.http.HttpSession;

public interface RegistryService extends IService<Registry> {

    R addUser(RegistryRequest registryRequest);

    Registry verityPassword(RegistryRequest loginRequest);

    R findById(Integer Id);

    R updatePassword(RegistryRequest updatePasswordRequest);

    R updateUsername(RegistryRequest updateUsernameRequest);

    R deleteUser(RegistryRequest deleteRequest);

    R loginStatus(RegistryRequest loginRequest, HttpSession session);
}
