package com.demo.xiying.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.FieldFill;
import java.util.Date;

@TableName(value="application")
@Data
public class Application {

    @TableId(type=IdType.AUTO)
    private Integer id;

    @TableField("sender_id")
    private Integer sender_id;

    @TableField("receiver_id")
    private Integer receiver_id;

    @TableField(fill=FieldFill.INSERT)
    private Date time;

}
