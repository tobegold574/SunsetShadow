package com.demo.xiying.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.xiying.common.R;
import com.demo.xiying.mapper.CommentMapper;
import com.demo.xiying.model.domain.Comment;
import com.demo.xiying.model.request.CommentRequest;
import com.demo.xiying.service.CommentService;
import com.demo.xiying.model.domain.Registry;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.demo.xiying.mapper.RegistryMapper;
import java.util.HashMap;
import java.util.ArrayList;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RegistryMapper registryMapper;
    

    @Override
    public R addComment(CommentRequest commentRequest){
        Comment comment=new Comment();

        BeanUtils.copyProperties(commentRequest,comment);

        try{
            if(commentMapper.insert(comment)>0) return R.success("成功评论");
            else return R.error("评论失败");
        }catch (DuplicateKeyException e){
            return R.fatal(e.getMessage());
        }
    }

    @Override
    public R findById(CommentRequest commentRequest) {
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();

        Integer user_id=commentRequest.getUser_id();

        queryWrapper.eq("user_id",user_id);

        List<Comment> comments=commentMapper.selectList(queryWrapper);

        return R.success("成功根据用户id获取评论",comments);
    }

    @Override
    public R findByPic(CommentRequest commentRequest) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();

        String pic_name = commentRequest.getPic_name();
        queryWrapper.eq("pic_name", pic_name);

        List<Comment> comments = commentMapper.selectList(queryWrapper);

        List<String> usernames = new ArrayList<>();
        for (Comment comment : comments) {
            Registry registry = registryMapper.selectOne(new QueryWrapper<Registry>().eq("id", comment.getUser_id()));
            if (registry != null) {
                usernames.add(registry.getUsername());
            } else {
                usernames.add("Unknown User"); // Handle case where user is not found
            }
        }

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("comments", comments);
        responseData.put("usernames", usernames);

        return R.success("根据图片名称成功获取评论", responseData);
    }
}
