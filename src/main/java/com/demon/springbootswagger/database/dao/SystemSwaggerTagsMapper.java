package com.demon.springbootswagger.database.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demon.springbootswagger.database.entity.SystemSwaggerTags;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * swagger 标签 Mapper 接口
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Mapper
public interface SystemSwaggerTagsMapper extends BaseMapper<SystemSwaggerTags> {

}
