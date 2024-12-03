package com.demo.xiying.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.Data;
import java.util.Date;

@TableName(value="marker")
@Data
public class Marker {

    @TableId(type=IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer user_id;

    @TableField("description")
    private String description;

    @TableField(fill=FieldFill.INSERT)
    private Date shootingTime;

    @TableField("longitude")
    private Double longitude;

    @TableField("latitude")
    private Double latitude;
    
}
