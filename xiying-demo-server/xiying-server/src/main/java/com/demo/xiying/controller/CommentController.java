package com.demo.xiying.controller;

import com.demo.xiying.common.R;
import com.demo.xiying.model.request.CommentRequest;
import com.demo.xiying.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 评论区
     * @param commentRequest
     * @return
     */
    @PostMapping("/comment/public")
    public R showComments(@RequestBody CommentRequest commentRequest){
        return commentService.findByPic(commentRequest);
    }
    /*
     * 发送评论
     */
    @PostMapping("/comment/public/send")
    public R toComment(@RequestBody CommentRequest commentRequest){
        return commentService.addComment(commentRequest);
    }
    /*
     * 查看自己的评论
     */
    @PostMapping("/comment/private")
    public R showYourComments(@RequestBody CommentRequest commentRequest){
        return commentService.findById(commentRequest);
    }
}
