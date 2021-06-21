package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Class;
import com.fzu.CloudClass.service.impl.ClassServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/CloudClass/class")
public class ClassController {

    @Autowired
    ClassServiceImpl classService;

    @ResponseBody
    @RequestMapping(value = "/Class", method = RequestMethod.GET)
    public JSONObject getAllClass(){

        JSONObject result = new JSONObject();
        List<Class> data = classService.getAllClass();

        result.put("data", data);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/Class", method = RequestMethod.POST)
    public JSONObject addAllClass(){

        JSONObject result = new JSONObject();
        List<Class> data = classService.getAllClass();

        result.put("data", data);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

}
