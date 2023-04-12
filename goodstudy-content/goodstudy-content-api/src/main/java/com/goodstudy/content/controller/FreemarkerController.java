package com.goodstudy.content.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description: FreemarkerController 测试
 *
 * @Author: Jack
 * Date: 2023/04/11 22:09
 * Version: 1.0
 */
@Controller
public class FreemarkerController {

    @GetMapping("/testfreemarker")
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView();
        // 设置模型数据
        modelAndView.addObject("name","Jack");
        // 设置视图名称
        modelAndView.setViewName("test");
        return modelAndView;
    }
}
