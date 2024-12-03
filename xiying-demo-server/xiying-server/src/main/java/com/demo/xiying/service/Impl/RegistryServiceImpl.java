package com.demo.xiying.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.xiying.common.R;
import com.demo.xiying.constant.Constants;
import com.demo.xiying.mapper.RegistryMapper;
import com.demo.xiying.model.domain.Registry;
import com.demo.xiying.model.request.ProfileRequest;
import com.demo.xiying.model.request.RegistryRequest;
import com.demo.xiying.service.RegistryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.nio.charset.StandardCharsets;

import com.demo.xiying.service.ProfileService;

@Service
public class RegistryServiceImpl extends ServiceImpl<RegistryMapper, Registry>
        implements RegistryService {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RegistryMapper registryMapper;

    @Override
    public R findById(Integer Id){
        QueryWrapper<Registry> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",Id);
        Registry result=registryMapper.selectOne(queryWrapper);
        return R.success("成功根据id找到对应用户",result);
    }

    @Override
    public R addUser(RegistryRequest registryRequest){
        Registry registry=new Registry();

        QueryWrapper<Registry> queryWrapper=new QueryWrapper<>();

        BeanUtils.copyProperties(registryRequest,registry);

        String password= DigestUtils.md5DigestAsHex((Constants.SALT+registryRequest.getPassword()).getBytes(StandardCharsets.UTF_8));

        queryWrapper.eq("username",registryRequest.getUsername())
                    .eq("password",password);

        registry.setPassword(password);

        try{
            if(registryMapper.insert(registry)>0) {
                ProfileRequest profileRequest=new ProfileRequest();
                int id=registryMapper.selectOne(queryWrapper).getId();
                String username=registryMapper.selectOne(queryWrapper).getUsername();
                profileRequest.setUser_id(id);
                profileRequest.setUsername(username);
                profileService.updateInfo(profileRequest);
                return R.success("注册成功", new HashMap<String, Object>() {{
                    put("id", id);
                    put("username", username);
                }});
            }
            else return R.error("注册失败");
        }catch (DuplicateKeyException e){
            return R.fatal(e.getMessage());
        }
    }

    @Override
    public Registry verityPassword(RegistryRequest loginRequest){
        QueryWrapper<Registry> queryWrapper=new QueryWrapper<>();

        String password=DigestUtils.md5DigestAsHex((Constants.SALT+loginRequest.getPassword()).getBytes(StandardCharsets.UTF_8));

        queryWrapper.eq("username",loginRequest.getUsername())
                .eq("password",password);

        Registry result=registryMapper.selectOne(queryWrapper);

        if(result!=null) return result  ;
        else return null;
    }

    @Override
    public R loginStatus(RegistryRequest loginRequest, HttpSession session){
        Registry registry=new Registry();

        registry=verityPassword(loginRequest);

        if(registry!=null){
            session.setAttribute("Id",registry.getId());
            return R.success("登陆成功",registry);
        }else{
            return R.error("登录失败：请检查密码或用户名");
        }
    }

    @Override
    public R updatePassword(RegistryRequest updatePasswordRequest){
        Registry registry=new Registry();

        registry.setId(updatePasswordRequest.getId());

        String password=DigestUtils.md5DigestAsHex((Constants.SALT+updatePasswordRequest.getPassword()).getBytes(StandardCharsets.UTF_8));

        registry.setPassword(password);

        if(registryMapper.updateById(registry)>0){
            return R.success("修改密码成功");
        }else{
            return R.error("修改密码失败");
        }
    }

    @Override
    public  R updateUsername(RegistryRequest updateUsernameRequest){
        Registry registry=new Registry();

        registry.setId(updateUsernameRequest.getId());

        String password=DigestUtils.md5DigestAsHex((Constants.SALT+updateUsernameRequest.getPassword()).getBytes(StandardCharsets.UTF_8));

        registry.setPassword(password);

        if(registryMapper.updateById(registry)>0){
            return R.success("修改用户名成功");
        }else{
            return R.error("修改用户名失败");
        }
    }

    @Override
    public R deleteUser(RegistryRequest deleteRequest){
        Registry registry=new Registry();

        registry.setId(deleteRequest.getId());

        if(registryMapper.deleteById(registry)>0){
            return R.success("注销成功");
        }else{
            return R.error("注销失败");
        }
    }
}
