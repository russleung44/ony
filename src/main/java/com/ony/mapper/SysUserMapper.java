package com.ony.mapper;

import com.ony.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author Tony
 * @since 2021-08-10 17:27:11
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}

