package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.RoleMenu;
import com.fzu.CloudClass.service.impl.RoleMenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CloudClass/role-menu")
public class RoleMenuController {

    @Autowired
    RoleMenuServiceImpl roleMenuService;

    @ResponseBody
    @RequestMapping(value = "/AllRoleMenu", method = RequestMethod.GET)
    public JSONObject getAllRoleMenu(@RequestParam("rId")int r_id){


        List<RoleMenu> data =  roleMenuService.getRoleMenuByRId(r_id);

        JSONObject result = new JSONObject();

        result.put("data",data);
        result.put("msg", "ok");
        result.put("code", "200");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/RoleMenu", method = RequestMethod.PUT)
    public JSONObject modifyRoleMenu(@RequestBody JSONObject jsonParam){

        int m_id = Integer.parseInt((String) jsonParam.get("mId"));
        int r_id = Integer.parseInt((String) jsonParam.get("rId"));
        //int rm_id = Integer.parseInt((String) jsonParam.get("rmId"));
        int status = Integer.parseInt((String) jsonParam.get("status"));

        roleMenuService.modifyRoleMenuStatus(r_id,m_id,status);
        JSONObject result = new JSONObject();

        result.put("msg", "ok");
        result.put("code", "200");
        return result;
    }

}
