package com.demon.springbootswagger.support;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName: ResponseBean
 * @Description: 返回的JSON数据结构标准
 * @Author: Demon
 * @Date: 2019/10/5 0:17
 */
@Data
@Accessors(chain = true)
public class ResponseBean implements Serializable {

    private boolean success = false;
    private Object data;
    private String errCode;
    private String errMsg;


}
