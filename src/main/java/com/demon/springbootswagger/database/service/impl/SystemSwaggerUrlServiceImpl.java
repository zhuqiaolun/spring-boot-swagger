package com.demon.springbootswagger.database.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demon.springbootswagger.database.dao.SystemSwaggerUrlMapper;
import com.demon.springbootswagger.database.entity.SystemSwaggerUrl;
import com.demon.springbootswagger.database.service.SystemSwaggerUrlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * swagger 的URL 配置 服务实现类
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Service
@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
public class SystemSwaggerUrlServiceImpl extends ServiceImpl<SystemSwaggerUrlMapper, SystemSwaggerUrl> implements SystemSwaggerUrlService {

    @Override
    public Page<Map<String, Object>> getSwaggerUrlListPage(long current, long size, Map<String, Object> params) {
        // 新建分页
        IPage<Map<String,Object>> page = new Page<>(current, size);
        return this.baseMapper.selectSystemSwaggerUrlListPage(page, params);
    }
}
