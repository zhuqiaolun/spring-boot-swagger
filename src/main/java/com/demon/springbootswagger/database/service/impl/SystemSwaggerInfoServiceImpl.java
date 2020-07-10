package com.demon.springbootswagger.database.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demon.springbootswagger.database.entity.SystemSwaggerInfo;
import com.demon.springbootswagger.database.dao.SystemSwaggerInfoMapper;
import com.demon.springbootswagger.database.service.SystemSwaggerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * swagger 信息 服务实现类
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Service
public class SystemSwaggerInfoServiceImpl extends ServiceImpl<SystemSwaggerInfoMapper, SystemSwaggerInfo> implements SystemSwaggerInfoService {

    @Override
    public JSONArray getInfoListMap() {
        JSONArray siProjects = new JSONArray();
        List<SystemSwaggerInfo> systemSwaggerInfos = this.list();
        if(systemSwaggerInfos != null && systemSwaggerInfos.size() > 0){
            systemSwaggerInfos.forEach(systemSwaggerInfo -> {
                JSONObject si = new JSONObject(true);
                si.put("siId",systemSwaggerInfo.getSiId());
                si.put("siTitle",systemSwaggerInfo.getSiTitle());
                siProjects.add(si);
            });
        }
        return siProjects;
    }
}
