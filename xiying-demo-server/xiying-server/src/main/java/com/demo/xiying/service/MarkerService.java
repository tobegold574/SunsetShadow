package com.demo.xiying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.xiying.model.domain.Marker;
import com.demo.xiying.model.request.MarkerRequest;
import com.demo.xiying.common.R;

public interface MarkerService extends IService<Marker>{
    public R addMarker(MarkerRequest markerRequest);

    public R getAllMarker();

}
