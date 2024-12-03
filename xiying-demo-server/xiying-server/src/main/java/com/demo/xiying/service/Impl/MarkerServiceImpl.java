package com.demo.xiying.service.Impl;

import com.demo.xiying.service.MarkerService;
import com.demo.xiying.model.domain.Marker;
import com.demo.xiying.model.request.MarkerRequest;
import com.demo.xiying.common.R;
import com.demo.xiying.mapper.MarkerMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkerServiceImpl extends ServiceImpl<MarkerMapper, Marker> implements MarkerService{
    @Autowired
    private MarkerMapper markerMapper;

    @Override
    public R addMarker(MarkerRequest markerRequest){
        Marker marker=new Marker();
        BeanUtils.copyProperties(markerRequest,marker);
        markerMapper.insert(marker);
        return R.success("添加成功");
    }

    @Override
    public R getAllMarker(){
        List<Marker> markerList=markerMapper.selectList(null);
        return R.success("获取成功",markerList);
    }
}
