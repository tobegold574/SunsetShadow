package com.demo.xiying.model.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@TableName(value="relation")
@Data
public class Relation {

    @TableId(type=IdType.AUTO)
    private Integer id;

    @TableField(value="friend_id")
    private Integer friend_id;

    @TableField(value="user_id")
    private Integer user_id;

    @TableField(fill= FieldFill.INSERT)
    private Date establish_time;
}
