package com.example.superblog.controller;

import com.example.superblog.service.ArticleService;
import com.example.superblog.vo.ResponseVO;
import com.example.superblog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/")
    public ResponseVO listArticles(@RequestBody PageParams pageParams){
        return articleService.listArticlesByPage(pageParams);
    }
}
