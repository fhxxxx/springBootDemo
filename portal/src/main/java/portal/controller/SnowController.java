package portal.controller;/*
 * Copyright (C) 1997-2020 康成投资（中国）有限公司
 *
 * http://www.rt-mart.com
 *
 * 版权归本公司所有，不得私自使用、拷贝、修改、删除，否则视为侵权
 */


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author gangxiang.guan
 * @create 2023/5/5 11:05
 */
@RequestMapping("snow")
@Slf4j
@RestController
public class SnowController {

    @RequestMapping("page")
    public ModelAndView page(ModelAndView modelAndView){
        log.info("打开圣诞树页");
        modelAndView.setViewName("snow/page");
        return modelAndView;
    }
}
