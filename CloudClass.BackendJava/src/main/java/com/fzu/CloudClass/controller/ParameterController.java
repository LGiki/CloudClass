package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.Parameter;
import com.fzu.CloudClass.service.impl.ParameterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/CloudClass/parameter")
public class ParameterController {

    @Autowired
    ParameterServiceImpl parameterService;

    @ResponseBody
    @RequestMapping(value = "/allParameter", method = RequestMethod.GET)
    public JSONObject getAllParameter(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){


        int count = parameterService.getCountAll();
        List<Parameter> parameters = parameterService.getAllParameter(pageNo,pageSize);

        JSONObject result = new JSONObject();
        result.put("total",count);
        result.put("data", parameters);
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    /*
    添加
     */
    @ResponseBody
    @RequestMapping(value = "/Parameter", method = RequestMethod.POST)
    public JSONObject addDictionary(@RequestBody JSONObject jsonParam){

        JSONObject result = new JSONObject();
        String paName = (String) jsonParam.get("paName");
        String description = (String) jsonParam.get("description");
        double value = Double.valueOf((String) jsonParam.get("value"));
        int status = Integer.parseInt((String) jsonParam.get("status"));

        int countByDescription = parameterService.countByDescription(description);
        if(countByDescription != 0){
            result.put("msg", "关键词已存在！");
            result.put("code", "201");
            return result;
        }
        parameterService.addParameter(paName,description,value,status);

        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/Parameter", method = RequestMethod.DELETE)
    public JSONObject delDictionary(@RequestParam("paId") int pa_id){

        parameterService.delParameter(pa_id);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/Parameter", method = RequestMethod.PUT)
    public JSONObject modifyDictionary(@RequestBody JSONObject jsonParam){

        int pa_id = Integer.parseInt((String) jsonParam.get("paId"));
        String paName = (String) jsonParam.get("paName");
        String description = (String) jsonParam.get("description");
        double value = Double.valueOf((String) jsonParam.get("value"));
        int status = Integer.parseInt((String) jsonParam.get("status"));
        parameterService.modifyParameter(pa_id,paName,description,value,status);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/KeyWord", method = RequestMethod.POST)
    public JSONObject findKeyWord(@RequestBody JSONObject jsonParam){

        JSONObject result = new JSONObject();
        String description = (String) jsonParam.get("description");

        int countByDescription = parameterService.countByDescription(description);
        if(countByDescription != 0){
            result.put("msg", "关键词已存在！");
            result.put("code", "201");
            return result;
        }

        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

}
