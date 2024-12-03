package com.demo.xiying.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.xiying.common.R;
import com.demo.xiying.mapper.RelationMapper;
import com.demo.xiying.mapper.RegistryMapper;
import com.demo.xiying.model.domain.Relation;
import com.demo.xiying.model.domain.Registry;
import com.demo.xiying.model.request.RelationRequest;
import com.demo.xiying.service.RelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation>
        implements RelationService {

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private RegistryMapper registryMapper;

    @Override
    public R addRelation(RelationRequest relationRequest) {
        Relation relation = new Relation();
        BeanUtils.copyProperties(relationRequest, relation);

        if (relationMapper.insert(relation) > 0) return R.success("新好友添加成功");
        else return R.error("添加失败");
    }

    @Override
    public R deleteRelation(RelationRequest relationRequest) {
        Relation relation = new Relation();
        BeanUtils.copyProperties(relationRequest, relation);

        if (relationMapper.deleteById(relation) > 0) return R.success("删除成功");
        else return R.error("删除失败");
    }

    @Override
    public R findById(Integer user_id) {
        QueryWrapper<Relation> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", user_id);

        QueryWrapper<Relation> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("friend_id", user_id);

        List<Relation> relations1 = relationMapper.selectList(queryWrapper1);
        List<Relation> relations2 = relationMapper.selectList(queryWrapper2);
        List<Registry> result = new ArrayList<>();

        for (Relation relation : relations1) {
            QueryWrapper<Registry> registryQueryWrapper = new QueryWrapper<Registry>();
            registryQueryWrapper.eq("id", relation.getFriend_id());
            Registry registry = registryMapper.selectOne(registryQueryWrapper);
            if (registry != null) {
                result.add(registry);
            }
        }

        for (Relation relation : relations2) {
            QueryWrapper<Registry> registryQueryWrapper = new QueryWrapper<Registry>();
            registryQueryWrapper.eq("id", relation.getUser_id());
            Registry registry = registryMapper.selectOne(registryQueryWrapper);
            if (registry != null) {
                result.add(registry);
            }
        }

        return R.success("好友列表加载成功", result);
    }
}
