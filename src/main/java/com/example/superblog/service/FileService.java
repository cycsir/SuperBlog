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

@Service
public class FileService {
    private final FileMapper fileMapper;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
            return ResponseVO.buildFail(ResponseCode.ERROR.getCode(), "upload blog failed");
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
        return ResponseVO.buildSuccess("upload blog success");
    }


}
