package com.demon.springbootswagger.database.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demon.springbootswagger.database.entity.SystemSwaggerInfo;

/**
 * <p>
 * swagger 信息 服务类
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
public interface SystemSwaggerInfoService extends IService<SystemSwaggerInfo> {

    /**
     * 获取自定义的项目列表
     * @return 返回 集合数组
     */
    JSONArray getInfoListMap();

}
