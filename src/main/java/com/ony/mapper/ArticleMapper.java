package com.ony.mapper;

import com.ony.domain.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章(Article)表数据库访问层
 *
 * @author Tony
 * @since 2021-08-25 17:30:44
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}

