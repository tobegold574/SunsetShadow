package com.demo.xiying.model.request;

import lombok.Data;

@Data
public class MarkerRequest {
    private Integer user_id;
    private String description;
    private Double longitude;
    private Double latitude;
}
