package com.example.superblog.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "file", schema = "superblog")
@Getter
@Setter
public class BlogFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "userid")
    private Integer userId;

    @Column(name = "name")
    private String filename;

    @Column(name = "type")
    private String type; // 文件类型：博客 或 博客图片

    @Column(name = "parent")
    private Integer parent;// 属于哪一个博客

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "url")
    private String url;

    @Column(name = "is_picture")
    private Integer isPicture;


}
