package com.demo.xiying.model.request;

import lombok.Data;

@Data
public class CommentRequest {
    private Integer user_id;

    private String comment;

    private String pic_name;
}
