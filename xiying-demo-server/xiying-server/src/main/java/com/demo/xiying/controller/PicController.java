package com.demo.xiying.controller;

import com.demo.xiying.common.R;
import com.demo.xiying.model.request.PicRequest;
import com.demo.xiying.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pic")
public class PicController {
    @Autowired
    private PicService picService;

    @Autowired
    private MinioUploadController minioUploadController;

    @PostMapping("/setIntroduction")
    public R setIntroduction(@RequestBody PicRequest picRequest){
        return picService.setIntroduction(picRequest);
    }

    @PostMapping("/uploadPic")
    public R uploadPic(@RequestParam("file") MultipartFile file, @RequestParam("user_id") Integer user_id) throws Exception {
        return minioUploadController.uploadPic(file,user_id);
    }
}
