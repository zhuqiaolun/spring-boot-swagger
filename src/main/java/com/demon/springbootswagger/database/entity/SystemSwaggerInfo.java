package com.demon.springbootswagger.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * swagger 信息
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemSwaggerInfo extends Model<SystemSwaggerInfo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "si_id", type = IdType.AUTO)
    private Integer siId;

    /**
     * 系统标题
     */
    @TableField("si_title")
    private String siTitle;

    /**
     * 系统版本
     */
    @TableField("si_version")
    private String siVersion;

    /**
     * 系统描述
     */
    @TableField("si_description")
    private String siDescription;

    /**
     * 请求协议
     */
    @TableField("si_schemes_http")
    private boolean siSchemeshttp;

    @TableField("si_schemes_https")
    private boolean siSchemeshttps;

    /**
     * HostAddress
     */
    @TableField("si_serverhost")
    private String siServerhost;

    @TableField("si_serverport")
    private Integer siServerport;

    @TableField("si_serverpath")
    private String siServerpath;

    /**
     * 全局变量
     */
    @TableField("si_securityDefinitions")
    private String siSecuritydefinitions;

    @TableField("si_contact_name")
    private String siContactName;

    @TableField("si_contact_url")
    private String siContactUrl;

    @TableField("si_contact_email")
    private String siContactEmail;

    @TableField("si_createtime")
    private Date siCreatetime;


    @Override
    protected Serializable pkVal() {
        return this.siId;
    }

}
