package com.demo.xiying.model.request;

import lombok.Data;

@Data
public class ApplicationRequest {
    private Integer sender_id;

    private Integer receiver_id;

    private boolean accept;
}
