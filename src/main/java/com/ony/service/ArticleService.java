package com.ony.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ony.domain.Article;
import com.ony.mapper.ArticleMapper;
import com.ony.pojo.PageParam;
import com.ony.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * 文章(Article)表服务接口
 *
 * @author Tony
 * @since 2021-08-25 17:30:44
 */
@Slf4j
@Service
@AllArgsConstructor
public class ArticleService extends ServiceImpl<ArticleMapper, Article> {

    private final ArticleRepository articleRepository;

    /**
     * excel标题
     */
    private static final String[] HEADERS = {
            "",
            "",
            "",
            "",
            "",
    };

    /**
     * excel标题属性
     */
    private static final String[] FIELD_NAMES = {
            "title",
            "content",
            "createTime",
            "updateTime",
            "deleted",
    };

    /**
     * 分页查询
     *
     * @param pageParam
     * @return
     */
    public PageInfo<Article> page(PageParam<Article> pageParam) {
        PageHelper.startPage(pageParam);
        Article article = pageParam.getData();
        return PageInfo.of(this.query().list());
    }

    /**
     * 分页查询
     *
     * @param pageParam
     * @return
     */
    public Page<Article> search(PageParam<String> pageParam) {
        PageRequest pageRequest = PageRequest.of(pageParam.getPageNum() - 1, pageParam.getPageSize());
        String keyword = pageParam.getData();
        return articleRepository.findByTitleOrContentLike(keyword, keyword, pageRequest);
    }
}

