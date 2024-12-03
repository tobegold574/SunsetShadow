package com.demo.xiying.model.request;

import lombok.Data;

@Data   
public class MatchRequest {
    private Integer user_id;
    private String sex;
    private String location;
}
