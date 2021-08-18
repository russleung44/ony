package com.ony.controller;

import com.github.houbb.resubmit.api.annotation.Resubmit;
import com.ony.domain.SysUser;
import com.ony.service.SysUserService;

import com.github.pagehelper.PageInfo;
import com.ony.pojo.vo.PageParam;
import com.ony.pojo.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户表(SysUser)表控制层
 *
 * @author Tony
 * @since 2021-08-10 18:05:04
 */
@Api(tags = "用户表")
@RestController
@AllArgsConstructor
@RequestMapping("/sysUser")
public class SysUserController {

    private final SysUserService sysUserService;

    @PostMapping("/page")
    @ApiOperation("条件查询")
    public R<PageInfo<SysUser>> list(@Valid @RequestBody PageParam pageParam) {
        PageInfo<SysUser> page = sysUserService.page(pageParam);
        return R.ok(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询")
    public R<SysUser> findById(@PathVariable Long id) {
        return R.ok(sysUserService.getById(id));
    }

    @Resubmit(ttl = 5)
    @PostMapping
    @ApiOperation("新增")
    public R add(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return R.ok();
    }

    @PutMapping
    @ApiOperation("修改")
    public R update(@RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据ID删除")
    public R delete(@PathVariable Long id) {
        sysUserService.removeById(id);
        return R.ok();
    }
}

