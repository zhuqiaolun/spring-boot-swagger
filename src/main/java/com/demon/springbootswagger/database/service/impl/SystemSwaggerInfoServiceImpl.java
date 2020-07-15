package com.demon.springbootswagger.database.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demon.springbootswagger.database.entity.SystemSwaggerInfo;
import com.demon.springbootswagger.database.dao.SystemSwaggerInfoMapper;
import com.demon.springbootswagger.database.entity.SystemSwaggerTags;
import com.demon.springbootswagger.database.entity.SystemSwaggerUrl;
import com.demon.springbootswagger.database.service.SystemSwaggerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demon.springbootswagger.database.service.SystemSwaggerTagsService;
import com.demon.springbootswagger.database.service.SystemSwaggerUrlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
public class SystemSwaggerInfoServiceImpl extends ServiceImpl<SystemSwaggerInfoMapper, SystemSwaggerInfo> implements SystemSwaggerInfoService {

    @Resource
    private SystemSwaggerTagsService systemSwaggerTagsService;

    @Resource
    private SystemSwaggerUrlService systemSwaggerUrlService;

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

    @Override
    public void deleteInfoById(String siId) {
        this.removeById(siId);
        systemSwaggerTagsService.remove(new QueryWrapper<SystemSwaggerTags>().setEntity(new SystemSwaggerTags().setStProject(siId)));
        systemSwaggerUrlService.remove(new QueryWrapper<SystemSwaggerUrl>().setEntity(new SystemSwaggerUrl().setSuProject(siId)));
    }
}
