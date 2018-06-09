package com.spmb.controller.chapter07.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 动态页面跳转控制器
 * @Author: leiyulin
 * @date: 2018/6/9
 */
@Controller
@RequestMapping(value = "/chapter07/interceptor")
public class FormController {

    private static final String PREFIX = "/chapter07/interceptor/";

    @RequestMapping(value = "/{formName}")
    public String loginForm(@PathVariable String formName) {
        // 动态跳转页面
        return PREFIX + formName;
    }
}
