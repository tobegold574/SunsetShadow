package com.demo.xiying.interceptors;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import com.demo.xiying.utils.StompPrincipal;
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request,WebSocketHandler wsHandler,Map<String, Object> attributes) {
        System.out.println("WebSocket Handshake start: " + request.getHeaders());
        URI uri = request.getURI();
        Map<String, String> queryParams = UriComponentsBuilder.fromUri(uri).build().getQueryParams().toSingleValueMap();

        // 从查询参数中获取用户名
        String username = queryParams.get("username");
        return new StompPrincipal(username);
    }
}
