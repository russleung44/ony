package com.ony.repository;

import com.github.pagehelper.PageInfo;
import com.ony.domain.Article;
import com.ony.pojo.vo.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Tony
 * @date 2021/8/25
 */
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

    /**
     * 标题内容模糊查询
     *
     * @param title
     * @param content
     * @param pageable
     * @return
     */
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);

}
