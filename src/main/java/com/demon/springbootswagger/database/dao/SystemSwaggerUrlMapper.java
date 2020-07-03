package com.demon.springbootswagger.database.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demon.springbootswagger.database.entity.SystemSwaggerUrl;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * swagger 的URL 配置 Mapper 接口
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Mapper
public interface SystemSwaggerUrlMapper extends BaseMapper<SystemSwaggerUrl> {

}
