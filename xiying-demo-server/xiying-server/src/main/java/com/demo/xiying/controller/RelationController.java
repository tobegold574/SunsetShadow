package com.demo.xiying.controller;

import com.demo.xiying.common.R;
import com.demo.xiying.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class RelationController {

    @Autowired
    private RelationService relationService;

    @PostMapping("/relation/getFriends")
    public R findById(@RequestBody Map<String,Object> map) {
        return relationService.findById(Integer.parseInt(map.get("user_id").toString()));
    }
}
