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
 * swagger 标签
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemSwaggerTags extends Model<SystemSwaggerTags> {

    private static final long serialVersionUID=1L;

    @TableId(value = "st_id", type = IdType.AUTO)
    private Integer stId;

    /**
     * 项目名
     */
    @TableField("st_project")
    private String stProject;

    /**
     * 标签名称
     */
    @TableField("st_name")
    private String stName;

    /**
     * 标签描述
     */
    @TableField("st_description")
    private String stDescription;

    /**
     * 标签备注
     */
    @TableField("st_remark")
    private String stRemark;

    @TableField("st_createtime")
    private Date stCreatetime;


    @Override
    protected Serializable pkVal() {
        return this.stId;
    }

}
