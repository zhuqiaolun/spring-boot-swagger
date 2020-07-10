package com.demon.springbootswagger.database.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demon.springbootswagger.database.entity.SystemSwaggerTags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

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

    /**
     * 自定义多表的分页查询 注入 @Param("params")  必须需要
     * @param page 分页标志
     * @param params 参数
     * @return 返回
     */
    Page<Map<String,Object>> selectSystemSwaggerTagsListPage(IPage<Map<String,Object>> page, @Param("params")Map<String, Object> params);

}
