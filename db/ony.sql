DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE ony.sys_user
(
    id          int UNSIGNED AUTO_INCREMENT COMMENT '用户ID'
        PRIMARY KEY,
    username    varchar(50)  DEFAULT ''                NOT NULL COMMENT '用户名',
    password    varchar(100) DEFAULT ''                NOT NULL COMMENT '密码',
    real_name   varchar(255) DEFAULT ''                NULL COMMENT '真实姓名',
    sex         varchar(10)  DEFAULT ''                NULL COMMENT '性别',
    phone       varchar(20)  DEFAULT ''                NULL COMMENT '手机',
    email       varchar(100) DEFAULT ''                NULL COMMENT '邮箱',
    dept_id     bigint       DEFAULT 0                 NOT NULL COMMENT '部门ID',
    avatar      varchar(100) DEFAULT ''                NULL COMMENT '头像',
    status      tinyint(1)   DEFAULT 0                 NULL COMMENT '状态 0锁定 1有效',
    create_time timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
)
    COMMENT '用户表' CHARSET = utf8mb4;

