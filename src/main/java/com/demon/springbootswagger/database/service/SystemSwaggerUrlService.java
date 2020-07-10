package com.demon.springbootswagger.database.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demon.springbootswagger.database.entity.SystemSwaggerUrl;

import java.util.Map;

/**
 * <p>
 * swagger 的URL 配置 服务类
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
public interface SystemSwaggerUrlService extends IService<SystemSwaggerUrl> {

    /**
     * 查询 （分页）
     * @param current 当前页
     * @param size  数量
     * @param params 参数
     * @return 返回
     */
    Page<Map<String,Object>> getSwaggerUrlListPage(long current, long size, Map<String,Object> params );
}
