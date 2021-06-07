package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.service.impl.DictionaryDetailServiceImpl;
import com.fzu.CloudClass.service.impl.DictionaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/CloudClass/dictionary-detail")
public class DictionaryDetailController {

    @Autowired
    DictionaryServiceImpl dictionaryService;

    @Autowired
    DictionaryDetailServiceImpl dictionaryDetailService;


    /*
    添加
     */
    @ResponseBody
    @RequestMapping(value = "/DictionaryDetail", method = RequestMethod.POST)
    public JSONObject addDictionaryDetail(@RequestBody JSONObject jsonParam){

        int d_id = Integer.parseInt ((String) jsonParam.get("dId"));
        String dd_name = (String) jsonParam.get("ddName");

        dictionaryDetailService.addDictionaryDetail(d_id,dd_name,0);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/DictionaryDetail", method = RequestMethod.DELETE)
    public JSONObject delDictionaryDetail(@RequestParam("ddId") int dd_id){

        //int dd_id = Integer.parseInt ((String) jsonParam.get("ddId"));


        dictionaryDetailService.delDictionaryDetail(dd_id);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/DictionaryDetail", method = RequestMethod.PUT)
    public JSONObject modifyDictionaryDetail(@RequestBody JSONObject jsonParam){

        int dd_id = Integer.parseInt((String) jsonParam.get("ddId"));
        String ddName = (String) jsonParam.get("ddName");
        dictionaryDetailService.modifyDictionaryDetail(dd_id,ddName);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }



}
