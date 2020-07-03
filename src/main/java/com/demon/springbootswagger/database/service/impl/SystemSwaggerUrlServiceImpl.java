package com.demon.springbootswagger.database.service.impl;

import com.demon.springbootswagger.database.entity.SystemSwaggerUrl;
import com.demon.springbootswagger.database.dao.SystemSwaggerUrlMapper;
import com.demon.springbootswagger.database.service.SystemSwaggerUrlService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * swagger 的URL 配置 服务实现类
 * </p>
 *
 * @author demon
 * @since 2020-07-02
 */
@Service
public class SystemSwaggerUrlServiceImpl extends ServiceImpl<SystemSwaggerUrlMapper, SystemSwaggerUrl> implements SystemSwaggerUrlService {

}
