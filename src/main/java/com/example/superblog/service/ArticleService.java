package com.example.superblog.service;

import com.example.superblog.entity.Article;
import com.example.superblog.mapper.ArticleMapper;
import com.example.superblog.vo.ArticleVO;
import com.example.superblog.vo.ResponseVO;
import com.example.superblog.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class ArticleService {

    private ArticleMapper articleMapper;

    /**
     * 1.分页查询，得到文章列表
     * */

    public ResponseVO listArticlesByPage(PageParams pageParams){

        PageRequest pageRequest = PageRequest.of(pageParams.getPage()-1, pageParams.getPageSize(), Sort.Direction.DESC, "create_time", "weights");
        Page<Article> page = articleMapper.findAll(pageRequest);
        Iterator<Article> iterator = page.iterator();
        List<ArticleVO> list = new ArrayList<>();
        while (iterator.hasNext()){
            Article article = iterator.next();
            list.add(copy(article));
        }
        return ResponseVO.success(list);
    }

    private List<ArticleVO> copyList(List<Article> articles){
        List<ArticleVO> res = new ArrayList<>();
        for(Article article : articles){
            res.add(copy(article));
        }
        return res;
    }

    private ArticleVO copy(Article article){
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        return articleVO;
    }
}
