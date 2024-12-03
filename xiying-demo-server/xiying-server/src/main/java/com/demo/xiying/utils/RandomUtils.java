package com.demo.xiying.utils;


import java.util.Random;

//密码加密，启动
public class RandomUtils {
    public static String code(){
        String characters="twjwcnm6987123";
        int length=5;
        StringBuilder randomString= new StringBuilder();
        Random random=new Random();

        for(int i=0;i!=length;++i){
            int randomIndex=random.nextInt(characters.length());
            char randomChar=characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }
}
