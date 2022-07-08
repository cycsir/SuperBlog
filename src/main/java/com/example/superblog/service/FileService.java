package com.example.superblog.service;

import com.example.superblog.entity.BlogFile;
import com.example.superblog.enums.ResponseCode;
import com.example.superblog.mapper.FileMapper;
import com.example.superblog.util.AliyunOssUtil;
import com.example.superblog.vo.BlogVO;
import com.example.superblog.vo.ResponseVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class FileService {
    private final FileMapper fileMapper;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String rootPath = "superblog/";
    private final AliyunOssUtil aliyunOssUtil;

    public FileService(FileMapper fileMapper, AliyunOssUtil aliyunOssUtil) {
        this.fileMapper = fileMapper;
        this.aliyunOssUtil = aliyunOssUtil;
    }

    public ResponseVO createBlog(Integer userId, MultipartFile blog){
        String dateStr = sdf.format(new Date());
        String path = rootPath +userId + "/" + dateStr;
        File blogFile = AliyunOssUtil.multipartFile2File(blog);
        // 上传阿里云
        String url = aliyunOssUtil.upload(blogFile, path);
        if(url.equals("")){
            return ResponseVO.buildFail(ResponseCode.ERROR.getCode(), "create blog failed");
        }
        String fileName = blogFile.getName();
        String type = AliyunOssUtil.getContentType(fileName.substring(fileName.lastIndexOf('.')));
        BlogFile blogFile1 = new BlogFile();
        blogFile1.setFilename(fileName);
        blogFile1.setIsPicture(0);
        blogFile1.setUserId(userId);
        blogFile1.setCreateTime(dateStr);
        blogFile1.setType(type);
        blogFile1.setUrl(url);
        fileMapper.save(blogFile1);
        return ResponseVO.buildSuccess("create blog success");
    }

//    public ResponseVO getBlogById(Integer BlogId){
//
//    }
//
//    public ResponseVO getBlogsByUserId(){
//
//    }
//
//    public ResponseVO getBlogs(){
//
//    }

    public ResponseVO updateBlog(Integer blogId, MultipartFile newBlog){
        Optional<BlogFile> file = fileMapper.findById(blogId);
        BlogFile oldBlog = new BlogFile();
        if(!file.isPresent() || file.get().getIsPicture() == 1){
            return ResponseVO.buildFail(ResponseCode.ERROR.getCode(), "blog not found");
        }
        oldBlog = file.get();

        // 获取目录路径
        String oldUrl = oldBlog.getUrl();
        String path = oldUrl.substring(0, oldUrl.lastIndexOf('/'));

        // 将新版本的文件上传到旧版本文件目录下
        File blogFile = AliyunOssUtil.multipartFile2File(newBlog);
        String url = aliyunOssUtil.upload(blogFile, path);
        if(url.equals("")){
            return ResponseVO.buildFail(ResponseCode.ERROR.getCode(), "update blog failed");
        }

        // 更新数据库中的url为新的url
        oldBlog.setUrl(url);
        fileMapper.save(oldBlog);

        return ResponseVO.buildSuccess("update blog success");
    }




}
