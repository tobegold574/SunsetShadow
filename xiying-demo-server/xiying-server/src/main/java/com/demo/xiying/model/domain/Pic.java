package com.demo.xiying.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
@TableName(value="pic")
@Data
public class Pic {

    @TableId(type=IdType.AUTO)
    private Integer id;

    @TableField(value="user_id")
    private Integer user_id;

    @TableField(value="pic_name")
    private String pic_name;

   private String introduction;
    
}
