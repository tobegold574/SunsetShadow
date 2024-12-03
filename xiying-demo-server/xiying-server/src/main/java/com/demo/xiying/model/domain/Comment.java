package com.demo.xiying.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@TableName(value="comment")
@Data
public class Comment {

    @TableId(type=IdType.AUTO)
    private Integer id;

    @TableField(value="user_id")
    private Integer user_id;

    @TableField(value="pic_name")
    private String pic_name;

    @TableField(fill=FieldFill.INSERT)
    private Date time;

    @TableField(value="comment")
    private String comment;
}
