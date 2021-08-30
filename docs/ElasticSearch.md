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
# 启用 xpack
xpack.security.enabled: true
```

```shell
# 重启 elasticsearch
./elasticsearch
# 新窗口设置密码
elasticsearch-setup-passwords interactive
```
修改密码
```shell
curl -H "Content-Type:application/json" -XPOST -u elastic 'http://127.0.0.1:9200/_xpack/security/user/elastic/_password' -d '{ "password" : "123456" }'
```

## 分词器
[IK中文分词器](https://github.com/medcl/elasticsearch-analysis-ik/releases)
下载解压重命名文件夹为 ik
放在 elasticsearch/plugins 目录下
重启

## elasticsearch-head
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
xpack

http://127.0.0.1:9100/?auth_user=elastic&auth_password=123456


## kibana
[下载]( https://www.elastic.co/cn/downloads/kibana)



## logstash
MySQL导入es

[官网](https://www.elastic.co/cn/downloads/logstash)

下载解压, 新建 mysqletc 文件夹, 进入 mysqlect 新建 mysql.conf 文件

单表配置
```config
input {
  jdbc {
    # 数据库连接语句
    jdbc_connection_string => "jdbc:mysql://127.0.0.1:3306/ony?characterEncoding=UTF8"
    jdbc_user => "root"
    jdbc_password => "root2021"
    # mysql-connection驱动的绝对路径
    jdbc_driver_library => "E:\Download\logstash-7.14.0\mysqletc\mysql-connector-java-8.0.26.jar"
    jdbc_driver_class => "com.mysql.cj.jdbc.Driver"
    jdbc_paging_enabled => "true"
    jdbc_page_size => "50000"
    # SQL查询语句，用于将查询到的数据导入到ElasticSearch, :sql_last_value 上次sql执行的时间
    statement => "select id,title,content from article where deleted = 0 and update_time > :sql_last_value"
    # 定时任务，各自表示：分 时 天 月 年 。全部为 * 默认每分钟执行
    schedule => "* * * * *"
  }
}
output {
  elasticsearch {
    hosts => "localhost:9200"
    # 如果启用了xpack
    user => "elastic"
    password => "123456"
    # 索引名称
    index => "article_index"
    # 自增ID编号
    document_id => "%{id}"
    template_overwrite => true
    template => "E:\Download\logstash-7.14.0\template\template.json"
  }
  stdout {
    # JSON格式输出
    codec => json_lines
  }
}
```

多表配置
```config
input {
  jdbc {
    # 数据库连接语句
    jdbc_connection_string => "jdbc:mysql://127.0.0.1:3306/ony?characterEncoding=UTF8"
    jdbc_user => "root"
    jdbc_password => "root2021"
    # mysql-connection驱动的绝对路径
    jdbc_driver_library => "E:\Download\logstash-7.14.0\mysqletc\mysql-connector-java-8.0.26.jar"
    jdbc_driver_class => "com.mysql.cj.jdbc.Driver"
    jdbc_paging_enabled => "true"
    jdbc_page_size => "50000"
    # SQL查询语句，用于将查询到的数据导入到ElasticSearch
    statement => "select id,title,content from article where deleted = 0 and update_time > :sql_last_value"
    # 定时任务，各自表示：分 时 天 月 年 。全部为 * 默认每分钟执行
    schedule => "* * * * *"
    type => "article"
  }
  jdbc {
    # 数据库连接语句
    jdbc_connection_string => "jdbc:mysql://127.0.0.1:3306/ony?characterEncoding=UTF8"
    jdbc_user => "root"
    jdbc_password => "root2021"
    # mysql-connection驱动的绝对路径
    jdbc_driver_library => "E:\Download\logstash-7.14.0\mysqletc\mysql-connector-java-8.0.26.jar"
    jdbc_driver_class => "com.mysql.cj.jdbc.Driver"
    jdbc_paging_enabled => "true"
    jdbc_page_size => "50000"
    # SQL查询语句，用于将查询到的数据导入到ElasticSearch
    statement => "select id,username,phone from sys_user where deleted = 0"
    # 定时任务，各自表示：分 时 天 月 年 。全部为 * 默认每分钟执行
    schedule => "* * * * *"
    type => "sys_user"
  }
}
output {
  if[type] =="sys_user"{
       elasticsearch {
          hosts => "localhost:9200"
          # 如果启用了xpack
          user => "elastic"
          password => "123456"
          # 索引名称
          index => "sys_user_index"
          # 自增ID编号
          document_id => "%{id}"
          # 文档名称
          # document_type => "_doc"
          template_overwrite => true
          template => "E:\Download\logstash-7.14.0\template\template.json"
      }
  }
  if[type] == "article"{
       elasticsearch {
          hosts => "localhost:9200"
          # 如果启用了xpack
          user => "elastic"
          password => "123456"
          # 索引名称
          index => "article_index"
          # 自增ID编号
          document_id => "%{id}"
          # 文档名称
          # document_type => "_doc"
          template_overwrite => true
          template => "E:\Download\logstash-7.14.0\template\template.json"
      }
  }
  stdout {
    # JSON格式输出
    codec => json_lines
  }
}
```

自定义模板使用ik分词器
```json
{
  "order": 0,
  "version": 50001,
  "template": "logstash-*",
  "settings": {
    "index": {
      "refresh_interval": "5s"
    }
  },
  "mappings": {
    "_default_": {
      "_all": {
        "enabled": true,
        "norms": false
      },
      "dynamic_templates": [
        {
          "message_field": {
            "path_match": "message",
            "match_mapping_type": "string",
            "mapping": {
              "type": "text",
              "norms": false
            }
          }
        },
        {
          "string_fields": {
            "match": "*",
            "match_mapping_type": "string",
            "mapping": {
              "type": "text",
              "norms": false,
              "analyzer": "ik_max_word",
              "fields": {
                "keyword": {
                  "type": "keyword",
                  "ignore_above": 256
                }
              }
            }
          }
        }
      ],
      "properties": {
        "@timestamp": {
          "type": "date",
          "include_in_all": false
        },
        "@version": {
          "type": "keyword",
          "include_in_all": false
        },
        "geoip": {
          "dynamic": true,
          "properties": {
            "ip": {
              "type": "ip"
            },
            "location": {
              "type": "geo_point"
            },
            "latitude": {
              "type": "half_float"
            },
            "longitude": {
              "type": "half_float"
            }
          }
        }
      }
    }
  },
  "aliases": {}
}
```

运行
```shell
logstash -f ../mysqletc/mysql.conf
```
