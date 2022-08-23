package com.example.superblog.vo;

import lombok.Data;

@Data
public class ArticleVO {
    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;

    private String createTime;

    private String author;

//    private ArticleBodyVO body;

//    private List<TagVO> tags;
}



