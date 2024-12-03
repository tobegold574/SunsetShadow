package com.demo.xiying.controller;

import com.demo.xiying.common.R;
import com.demo.xiying.model.request.ProfileRequest;
import com.demo.xiying.service.ProfileService;
import com.demo.xiying.service.RegistryService;
import com.demo.xiying.service.MatchService;
import com.demo.xiying.model.request.MatchRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RegistryService registryService;

    @Autowired
    private MinioUploadController minioUploadController;

    @Autowired
    private MatchService matchService;

    @PostMapping("/profile/updateInfo")
    public R updateInfo(@RequestBody ProfileRequest profileRequest){
        return profileService.updateInfo(profileRequest);
    }

    @PostMapping("/profile/getInfo")
    public R getInfo(@RequestBody Map<String,Object> map){
        return profileService.getInfo(Integer.parseInt(map.get("user_id").toString()));
    }

    @PostMapping("/profile/uploadAvatar")
    public R uploadAvatar(@RequestParam("file") MultipartFile file,@RequestParam("user_id") Integer user_id, @RequestParam("oldFilename") String oldFilename) throws Exception {
        return minioUploadController.uploadAvatar(file,user_id,oldFilename);
    }

    @PostMapping("/profile/getById")
    public R findById(@RequestBody Map<String,Object> map){
        return registryService.findById(Integer.parseInt(map.get("user_id").toString()));
    }   

    @PostMapping("/profile/match")
    public R match(@RequestBody MatchRequest matchRequest){
        return matchService.match(matchRequest);
    }
}