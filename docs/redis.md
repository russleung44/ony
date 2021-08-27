# Redis

## 一些描述
- 空间换时间
- C语言编写, KV 类型 NOSQL 数据库
- 数据存在内存中
- 单线程, 单进程 避免了不必要的上下文切换和线程竞争, 减少CPU消耗, 不存在锁问题(Redis6.0 改为多线程)
- 支持事务 集群
- 读: 110000/s 写: 81000/s
- 多路 I/O 复用模型，非阻塞 IO
  - 多路: 多个网络连接
  - 复用: 复用同一线程
  - 让单个线程高效处理多个连接请求

## 支持的数据类型
- String
- List
- Hash
- Set
- ZSet

## 过期策略

### 定时删除
- 默认每隔100ms随机抽取设定了过期时间的key, 检查其是否过期, 过期则删除
- 保证内存尽快被释放
- 若过期key过多会占用CPU时间
- 定时器创建消耗性能

### 惰性删除
- key过期的时候不删除, 每次去获取key的时候检测是否过期, 若过期则删除返回null
- 对CPU占用较少
- 可能堆积大量key导致内存泄露


## 持久化

### RDB
redis database(默认使用)

在指定的时间内将内存中的数据集快照写入磁盘中(snapshot), 恢复时把 snapshot 读入内存中
fork一个子进程专门做持久化
先将数据写入一个临时文件中, 待持久化进程结束将临时文件替换上一次生成的持久化文件
持久化过程主程序不进行I/O操作, 保障了极高的性能
最后一次持久化后的数据可能丢失
适合大规模的数据恢复
对数据的完整性要求不高

触发机制
- flushall
- 退出 redis
- 操作redis 
  - 900s 1次操作
  - 300s 10次操作
  - 60s  10000次操作
```yml
save 900 1
save 300 10
save 60 10000
```

恢复
将 dump.rdb 放到启动目录下, redis 启动时会将该文件读入到内存中进行数据恢复
```shell
# 查询redis启动目录
redis-cli
config get dir
```

### AOF
APPEND ONLY MODE(默认不启用)
以日记的形式记录每一个操作, 将 redis 执行过的写操作全部记录下来
redis启动的时候会读取 aof 文件重建数据库
每一次redis的操作都会被记录下来, 文件完整性好
每秒同步一次, 最多丢失一秒的数据
文件较大, 恢复速度较慢


```yaml
# 启用 AOF
appendonly yes
# 文件名
appendfilename "appendonly.aof"
# aof文件大于64m时新开一个子进程写入新的aof文件
auto-aof-rewrite-min-size 64mb
```

```shell
# aof文件修复
edis-check-aof --fix appendonly.aof
```
