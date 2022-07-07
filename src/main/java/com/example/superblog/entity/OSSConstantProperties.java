package com.example.superblog.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author cyc
 * 描述:创建实例类操作阿里云OSS服务的原子数据存储
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
//获取配置文件内容注入类属性，方便同一修改
public class OSSConstantProperties {

    private String bucketName;

    private String endPoint;

    private String accessKeyId;

    private  String accessKeySecret;

}

