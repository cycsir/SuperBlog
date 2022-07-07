package com.example.superblog.util;


import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import com.example.superblog.entity.OSSConstantProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author cyc
 * 描述与 aliyun SDK直接相互作用的工具实现类
 */

@Component
public class AliyunOssUtil {

    @Autowired
    private OSSConstantProperties ossConstantProperties;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 处理文件类型,用于文件存储时设置对应存储类型和访问权限
     * @param FilenameExtension: 文件扩展名
     */
    public static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".md")){
            return "test/md";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        if (FilenameExtension.equalsIgnoreCase(".zip")) {
            return "application/zip";
        }
        if (FilenameExtension.equalsIgnoreCase(".xls") ||
                FilenameExtension.equalsIgnoreCase(".xlsx")) {
            return "application/vnd.ms-excel";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "application/octet-stream";
    }

    //简单的文件上传测试
    //文件上传接口需要的参数是被上传文件的本地绝对地址
    //文件上传返回值应该是在bucket中的位置信息。
    public String upload(File file, String path) {

        String endpoint = this.ossConstantProperties.getEndPoint();
        String accessKeyId = this.ossConstantProperties.getAccessKeyId();
        String accessKeySecret = this.ossConstantProperties.getAccessKeySecret();
        String bucketName = this.ossConstantProperties.getBucketName();
        //上传到阿里云的位置和项目名称，默认从bucket开始。
        //    String objectName="文件/test01";
        //本地被上传文件的地址，要求为绝对路路径
        // String filePath="D:\\Game\\test.txt";
//        File file = new File("D:\\企业应用开发\\笔记\\img\\图片11.png");
        //创建OSSClient实例
        String dateStr = sdf.format(new Date());
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            //容器不存在，就创建,权限为私有的
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.Private);
                ossClient.createBucket(createBucketRequest);
            }
            //上传时设置存储类型和访问权限
            ObjectMetadata objectMetadata = new ObjectMetadata();
            String type = getContentType(file.getName().substring(file.getName().lastIndexOf('.')));
            String fileUrl = "";

            objectMetadata.setContentType(getContentType(file.getName().substring(file.getName().lastIndexOf("."))));
            objectMetadata.setObjectAcl(CannedAccessControlList.Default);

            //创建文件路径: 用户id/blog创建时间戳/文件名；
            fileUrl = (path + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());

            //上传文件,附带有桶、文件地址、文件、文件存储属性，
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file, objectMetadata));
            System.out.println(result.toString());
            if (result != null) {
                System.out.println(("==========>OSS文件上传成功,OSS地址：" + fileUrl));
                return fileUrl;
            }
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "";
    }


    //从bucket中下载文件到本地，有文件直接下载、流式下载、断点下载
    //参考文档代码 https://help.aliyun.com/document_detail/84824.html
    //1、参数一：objectName:文件在bucket中的位置
    //2、参数二：pathName:下载到本地的保存路径
    public void exportOssFile() {
        String endpoint = ossConstantProperties.getEndPoint();
        String accessKeyId = ossConstantProperties.getAccessKeyId();
        String accessKeySecret = ossConstantProperties.getAccessKeySecret();
        String bucketName = ossConstantProperties.getBucketName();
        // 填写不包含Bucket名称在内的Object完整路径，例如testfolder/exampleobject.txt。
        //这里需要考虑一下文件路径加名称问题
        String objectName = "文件/计算机系统实践.doc";
        String pathName = "E:\\小组学习\\测试下载\\计算机系统实践.doc";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
            // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
            ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(pathName));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    //从bucket中根据url删除指定文件
    //参数就一个被删除文件的地址
    public void deletBlog() {
        String endpoint = this.ossConstantProperties.getEndPoint();
        String accessKeyId = this.ossConstantProperties.getAccessKeyId();
        String accessKeySecret = this.ossConstantProperties.getAccessKeySecret();
        String bucketName = this.ossConstantProperties.getBucketName();
        // 填写文件完整路径。文件完整路径中不能包含Bucket名称。
        String objectName = "exampledir/exampleobject.txt";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(bucketName, objectName);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }


    public static File multipartFile2File(MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        File file = new File(fileName);
        OutputStream out = null;
        try {
            // InputStream in = multipartFile.getInputStream();
            out = Files.newOutputStream(file.toPath());
            byte[] ss = multipartFile.getBytes();
            for (byte s : ss) {
                out.write(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null){
                try {
                    out.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return file;
    }
}



