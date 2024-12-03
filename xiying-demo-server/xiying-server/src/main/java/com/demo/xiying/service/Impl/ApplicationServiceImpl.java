package com.demo.xiying.service.Impl;

import com.demo.xiying.service.ApplicationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.xiying.common.R;
import com.demo.xiying.mapper.ApplicationMapper;
import com.demo.xiying.model.domain.Application;
import com.demo.xiying.model.request.ApplicationRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.xiying.mapper.RegistryMapper;
import com.demo.xiying.model.domain.Registry;

import java.util.ArrayList;
import java.util.List;

import com.demo.xiying.service.RelationService;
import com.demo.xiying.model.request.RelationRequest;
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService{
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private RegistryMapper registryMapper;
    
    @Autowired
    private RelationService relationService;

    @Override
    public R getApplicationList(Integer receiver_id){

        QueryWrapper<Application> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("receiver_id", receiver_id);
        
        List<Application> applications=applicationMapper.selectList(queryWrapper);

        List<Registry> users = new ArrayList<>();

    for (Application application : applications) {
        // Create a new QueryWrapper to find the username for each sender_id
        QueryWrapper<Registry> registryQuery = new QueryWrapper<>();
        registryQuery.eq("id", application.getSender_id());
        Registry registry = registryMapper.selectOne(registryQuery);

        if (registry != null) {
            // Add the username to the list
            users.add(registry);
        }
    }

    return R.success("获取成功",users);

        
    }


@Override
public R sendFriendRequest(ApplicationRequest applicationRequest){
    Application application=new Application();
    BeanUtils.copyProperties(applicationRequest, application);
    System.out.println(applicationRequest.getSender_id());
    if(applicationMapper.insert(application)>0){
        return R.success("发送成功");
    }
    return R.error("发送失败");
}

@Override
public R deleteApplication(ApplicationRequest applicationRequest){
    QueryWrapper<Application> queryWrapper=new QueryWrapper<>();
    queryWrapper.eq("sender_id", applicationRequest.getSender_id());
    queryWrapper.eq("receiver_id", applicationRequest.getReceiver_id());
    if(applicationRequest.isAccept()){
        RelationRequest relationRequest=new RelationRequest();
        relationRequest.setUser_id(applicationRequest.getSender_id());
        relationRequest.setFriend_id(applicationRequest.getReceiver_id());
        relationService.addRelation(relationRequest);
    }
    System.out.println("开始删除application");
    if(applicationMapper.delete(queryWrapper)>0){
        System.out.println("删除成功");
        return R.success("删除成功");
    }
    return R.error("删除失败");
    }

}