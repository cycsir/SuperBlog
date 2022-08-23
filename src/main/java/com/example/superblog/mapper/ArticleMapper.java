package com.example.superblog.mapper;

import com.example.superblog.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleMapper extends CrudRepository<Article, Integer> , PagingAndSortingRepository<Article, Integer> {

}
