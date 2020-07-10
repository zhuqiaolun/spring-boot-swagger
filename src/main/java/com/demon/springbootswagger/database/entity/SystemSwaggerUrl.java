package com.demon.springbootswagger.database.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * swagger 的URL 配置
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemSwaggerUrl extends Model<SystemSwaggerUrl> {

    private static final long serialVersionUID=1L;

    @TableId(value = "su_id", type = IdType.AUTO)
    private Integer suId;

    /**
     * 项目名
     */
    @TableField("su_project")
    private String suProject;

    /**
     * 标签
     */
    @TableField("su_tags")
    private String suTags;

    @TableField("su_url")
    private String suUrl;

    /**
     * 请求类型
     */
    @TableField("su_method")
    private String suMethod;



    /**
     * 标题
     */
    @TableField("su_summary")
    private String suSummary;

    /**
     * 描述
     */
    @TableField("su_description")
    private String suDescription;

    /**
     * 标识（可写方法名称）
     */
    @TableField("su_operationId")
    private String suOperationid;

    /**
     * 请求参数类型
     */
    @TableField("su_consumes")
    private String suConsumes;

    /**
     * 返回参数类型
     */
    @TableField("su_produces")
    private String suProduces;

    /**
     * 参数
     */
    @TableField("su_parameters")
    private String suParameters;

    /**
     * 响应
     */
    @TableField("su_responses")
    private String suResponses;

    /**
     * 全局api_key
     */
    @TableField("su_security")
    private String suSecurity;

    /**
     * 是否过时
     */
    @TableField("su_deprecated")
    private Byte suDeprecated;

    /** DateTimeFormat页面写入数据库时格式化，JSONField 数据库导出页面时json格式化 */
    @TableField("su_createtime")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date suCreatetime;


    @Override
    protected Serializable pkVal() {
        return this.suId;
    }

}
