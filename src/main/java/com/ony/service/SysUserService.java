package com.ony.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ony.domain.SysUser;
import com.ony.mapper.SysUserMapper;
import com.ony.pojo.PageParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户表(SysUser)表服务接口
 *
 * @author Tony
 * @since 2021-08-10 17:58:17
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    /**
     * excel标题
     */
    private static final String[] HEADERS = {
            "用户名",
            "密码",
            "真实姓名",
            "性别",
            "手机",
            "邮箱",
            "部门ID",
            "头像",
            "状态 0锁定 1有效",
            "创建时间",
    };

    /**
     * excel标题属性
     */
    private static final String[] FIELD_NAMES = {
            "username",
            "password",
            "realName",
            "sex",
            "phone",
            "email",
            "deptId",
            "avatar",
            "status",
            "createTime",
    };

    /**
     * 分页查询
     *
     * @param pageParam
     * @return
     */
    public PageInfo<SysUser> page(PageParam pageParam) {
        PageHelper.startPage(pageParam);
        List<SysUser> list = this.query().list();
        return PageInfo.of(list);
    }


}

