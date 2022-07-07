package com.example.superblog.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class AliyunOSSUtilTest {

    @Test
    public void testGetContentType(){
        String fileName = "开发过程笔记.md";
        String[] strs = fileName.split("\\.");
        System.out.println(strs[1]);
//        System.out.println(AliyunOssUtil.getContentType(fileName.split("/.")[]));
    }
}
