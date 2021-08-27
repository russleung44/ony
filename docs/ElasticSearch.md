 # ElasticSearch
 实时的分布式存储 搜索 分析引擎
 - 擅长模糊搜索
 - 可以根据评分过滤
 - 能匹配有相关性的记录
 - Restful接口, Java开发


## 安装
### windows
[官网下载](https://www.elastic.co/cn/downloads/past-releases#elasticsearch)
选择windows版本, 解压, 进入bin
```shell
# 启动
.\elasticsearch
```

#### 设置密码
config/elasticsearch.yml
```yaml
xpack.security.enabled: true
```
重启
```shell
elasticsearch-setup-passwords interactive
```
修改密码
```shell
curl -H "Content-Type:application/json" -XPOST -u elastic 'http://127.0.0.1:9200/_xpack/security/user/elastic/_password' -d '{ "password" : "123456" }'
```

#### 分词器
[IK中文分词器](https://github.com/medcl/elasticsearch-analysis-ik/releases)
下载解压重命名文件夹为 ik
放在 elasticsearch/plugins 目录下
重启

#### 可视化界面
[elasticsearch-head](https://github.com/mobz/elasticsearch-head)
```shell
git clone git://github.com/mobz/elasticsearch-head.git
cd elasticsearch-head
npm install
npm run start
open http://localhost:9100/
```
允许跨域访问
config/elasticsearch.yml
```yaml
http.cors.enabled: true
http.cors.allow-origin: "*"
http.cors.allow-headers: Authorization,X-Requested-With,Content-Type,Content-Length
```