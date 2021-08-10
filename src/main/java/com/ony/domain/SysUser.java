package com.ony.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户表(SysUser)实体类
 *
 * @author Tony
 * @since 2021-08-10 17:25:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = -63471123557584045L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("部门ID")
    private Long deptId;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("状态 0锁定 1有效")
    private Boolean status;

    @ApiModelProperty("创建时间")
    private Date createTime;

}

