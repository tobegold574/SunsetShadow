package com.demo.xiying.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@TableName(value="registry")
@Data
public class Registry {
    @TableId(type=IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    @TableField(fill=FieldFill.INSERT)
    private Date create_time;


}

