package com.demo.xiying.controller;

import com.demo.xiying.model.request.MarkerRequest;
import com.demo.xiying.service.MarkerService;
import com.demo.xiying.common.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 高德地图坐标标记
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/marker")
public class MarkerController {
    @Autowired
    private MarkerService markerService;

    @PostMapping("/add")
    public R addMarker(@RequestBody MarkerRequest markerRequest){
        return markerService.addMarker(markerRequest);
    }

    @GetMapping("/get")
    public R getAllMarker(){
        return markerService.getAllMarker();
    }
    
}
