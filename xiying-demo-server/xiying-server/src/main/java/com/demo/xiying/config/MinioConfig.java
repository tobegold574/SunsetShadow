package com.demo.xiying.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//minio配置
@Configuration
public class MinioConfig {

    //minio的endpoint
    @Value("${minio.endpoint}")
    private String minioEndpoint;

    //minio的access-key
    //可以通过minio的web界面创建
    //也可以通过cli创建
    @Value("${minio.access-key}")
    private String minioAccessKey;

    //minio的secret-key
    //同上
    @Value("${minio.secret-key}")
    private String minioSecretKey;

    //创建minio客户端
    //所有其他服务都可以通过@Autowired注入minioClient
    //这样就可以避免在每个服务中都创建一个MinioClient实例
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioEndpoint)
                .credentials(minioAccessKey, minioSecretKey)
                .build();
    }
}
