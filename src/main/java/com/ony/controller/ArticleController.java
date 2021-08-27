package com.ony.controller;

import com.ony.domain.Article;
import com.ony.repository.ArticleRepository;
import com.ony.service.ArticleService;

import com.github.pagehelper.PageInfo;
import com.ony.pojo.vo.PageParam;
import com.ony.pojo.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 文章(Article)表控制层
 *
 * @author Tony
 * @since 2021-08-25 17:30:44
 */
@Api(tags = "文章")
@RestController
@AllArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;


    @PostMapping("/search")
    @ApiOperation("elasticsearch")
    public R<Page<Article>> search(@Valid @RequestBody PageParam<String> pageParam) {
        return R.ok(articleService.search(pageParam));
    }

    @PostMapping("/page")
    @ApiOperation("条件查询")
    public R<PageInfo<Article>> page(@Valid @RequestBody PageParam<Article> pageParam) {
        return R.ok(articleService.page(pageParam));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询")
    public R<Article> findById(@PathVariable Integer id) {
        return R.ok(articleService.getById(id));
    }

    @PostMapping
    @ApiOperation("新增")
    public R add(@RequestBody Article article) {
        articleService.save(article);
        articleRepository.save(article);
        return R.ok();
    }

    @PutMapping
    @ApiOperation("修改")
    public R update(@RequestBody Article article) {
        articleService.updateById(article);
        articleRepository.save(article);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据ID删除")
    public R delete(@PathVariable String id) {
        articleService.removeById(id);
        articleRepository.deleteById(id);
        return R.ok();
    }
}

