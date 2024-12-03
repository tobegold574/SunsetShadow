package com.demo.xiying.model.request;


import lombok.Data;

import java.util.Date;

@Data
public class ProfileRequest {
    private Integer user_id;

    private String sex;

    private String location;

    private String introduction;

    private Date birthday;

    private String avatar;

    private String username;

}
