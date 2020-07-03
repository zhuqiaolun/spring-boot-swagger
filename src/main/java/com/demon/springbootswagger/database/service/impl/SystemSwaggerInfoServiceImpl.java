package com.demon.springbootswagger.database.service.impl;

import com.demon.springbootswagger.database.entity.SystemSwaggerInfo;
import com.demon.springbootswagger.database.dao.SystemSwaggerInfoMapper;
import com.demon.springbootswagger.database.service.SystemSwaggerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
