package com.demo.xiying.controller;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.messages.Item;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import io.minio.ListObjectsArgs;
import io.minio.Result;

@RestController
@CrossOrigin(origins = "*")
public class MinioController {
    @Value("${minio.bucket-name}")
    private String bucketname;

    @Autowired
    private MinioClient minioClient;

    @GetMapping("/image/avatars/{userId}")
    public byte[] getAvatar(@PathVariable Integer userId) {

        System.out.println("获取头像");
        String filename = userId + "_avatar"; // Assuming the avatar is stored with this naming convention
        try{
            InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketname)
                        .object("image/avatars/" + filename)
                        .build());

            return IOUtils.toByteArray(stream);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/image/pics/{filename:.+}")
    public byte[] getPic(@PathVariable String filename) {
        try{
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketname)
                        .object("image/pics/" + filename)
                        .build());

            return IOUtils.toByteArray(stream);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/api/images/random")
    public byte[] getImageBlob() {
        try {
            String filename = getRandomImage();
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketname)
                            .object(filename)
                            .build());
            return IOUtils.toByteArray(stream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/api/images/random/filename")
    public String getRandomImageFilename() throws Exception {
        return getRandomImage();
    }

    private String getRandomImage() throws Exception {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketname).prefix("image/pics/").build());

        List<String> images = StreamSupport.stream(results.spliterator(), false)
                .map(itemResult -> {
                    try {
                        return itemResult.get().objectName();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(name -> name != null && !name.endsWith("/"))
                .collect(Collectors.toList());

        if (images.isEmpty()) {
            throw new Exception("No images found in the bucket.");
        }

        Collections.shuffle(images);
        return images.get(0); 
    }
}
