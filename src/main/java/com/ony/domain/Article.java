package com.ony.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * 文章(Article)实体类
 *
 * @author Tony
 * @since 2021-08-25 17:30:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("article")
@Document(indexName = "article_index")
public class Article implements Serializable {
    private static final long serialVersionUID = -29097798836788594L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Boolean deleted;

}

