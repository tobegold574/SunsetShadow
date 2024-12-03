package com.demo.xiying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.xiying.common.R;
import com.demo.xiying.model.domain.Comment;
import com.demo.xiying.model.request.CommentRequest;


public interface CommentService extends IService<Comment> {
    public R addComment(CommentRequest commentRequest);

    public R findById(CommentRequest commentRequest);

    public R findByPic(CommentRequest commentRequest);

}
