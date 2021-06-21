package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.School;
import com.fzu.CloudClass.service.impl.SchoolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/CloudClass/school")
public class SchoolController {

    @Autowired
    SchoolServiceImpl schoolService;

    @ResponseBody
    @RequestMapping(value = "/AllCollege", method = RequestMethod.GET)
    public JSONObject findKeyWord(){

        JSONObject result = new JSONObject();

        List<Map<String,Object>> datas = new ArrayList<>();

        List<School> universities = schoolService.getAllUniversity();

        for(int i = 0; i < universities.size(); i++){
            Map<String, Object> data = new HashMap<>();
            data.put("title",universities.get(i).getSName());
            data.put("s_id",universities.get(i).getSId());
            data.put("depart",schoolService.getAllCollegeByParentId(universities.get(i).getSId()));
            datas.add(data);
        }

        result.put("data",datas);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

}
