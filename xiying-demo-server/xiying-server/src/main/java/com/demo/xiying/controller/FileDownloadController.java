package com.demo.xiying.controller;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/*
 * 未实装
 */
@Controller
@RequestMapping("/download")
public class FileDownloadController {
    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketname;

    @GetMapping("/{*filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest  request, InputStream inputStream) throws Exception{

        GetObjectArgs args=GetObjectArgs.builder()
                .bucket(bucketname)
                .object(filename)
                .build();

        InputStream stream=minioClient.getObject(args);

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();

        byte[] buffer=new byte[1024];
        int bytesRead;
        while((bytesRead=stream.read(buffer))!=-1){
            outputStream.write(buffer,0,bytesRead);
        }

        byte[] picBytes=outputStream.toByteArray();
        ByteArrayResource resource=new ByteArrayResource(picBytes);

        HttpHeaders headers=new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename"+filename);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(picBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
