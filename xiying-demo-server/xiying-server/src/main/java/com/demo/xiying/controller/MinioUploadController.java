package com.demo.xiying.controller;

import com.demo.xiying.common.R;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioUploadController {
    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketname;

    public R uploadAvatar( MultipartFile file, Integer user_id, String oldFilename) throws Exception {
        try {
            // Delete the old avatar if it exists
            if (oldFilename != null && !oldFilename.isEmpty()) {
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucketname)
                                .object("image/avatars/" + oldFilename)
                                .build()
                );
            }

            System.out.println("上传一次");
            // Upload the new avatar
            InputStream inputStream = file.getInputStream();
            String filename = user_id + "_avatar";
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketname)
                            .object("image/avatars/" + filename)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return R.success("头像上传成功", filename);
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return R.error("上传失败");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public R uploadPic( MultipartFile file, Integer user_id) throws Exception {
        try {
            InputStream inputStream = file.getInputStream();
            String filename =user_id + "_" + file.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketname)
                            .object("image/pics/" + filename)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return R.success("图片上传成功");
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return R.error("上传失败");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}