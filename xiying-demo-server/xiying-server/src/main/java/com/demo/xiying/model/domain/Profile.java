package com.demo.xiying.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.IdType; 
import lombok.Data;

import java.util.Date;

@TableName(value="profile")
@Data
public class Profile {

    @TableId(type=IdType.AUTO)
    private Integer id;

    @TableField(value="user_id")
    private Integer user_id;

    @TableField(value="sex")
    private String sex;

    @TableField(value="location")
    private String location;

    @TableField(value="introduction")
    private String introduction;

    @TableField(value="birthday")
    private String birthday;

    @TableField(value="avatar")
    private String avatar;

    @TableField(value="username")
    private String username;

    @TableField(fill=FieldFill.INSERT_UPDATE)
    private Date update_time;


}
