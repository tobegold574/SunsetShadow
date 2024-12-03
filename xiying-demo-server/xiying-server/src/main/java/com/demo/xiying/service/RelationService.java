package com.demo.xiying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.xiying.common.R;
import com.demo.xiying.model.domain.Relation;
import com.demo.xiying.model.request.RelationRequest;

public interface RelationService extends IService<Relation> {
    public R findById(Integer user_id);

    public R addRelation(RelationRequest relationRequest);

    public R deleteRelation(RelationRequest relationRequest);


}
