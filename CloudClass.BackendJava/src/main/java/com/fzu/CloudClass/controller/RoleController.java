package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.fzu.CloudClass.entity.Role;
import com.fzu.CloudClass.service.impl.PersonServiceImpl;
import com.fzu.CloudClass.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/CloudClass/role")
public class RoleController {

    @Autowired
    PersonServiceImpl personService;

    @Autowired
    RoleServiceImpl roleService;

    @ResponseBody
    @RequestMapping(value = "/AllRole", method = RequestMethod.GET)
    public JSONObject getAllRole(){

        JSONObject result = new JSONObject();

        result.put("data",roleService.getAllRole());
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/Role", method = RequestMethod.PUT)
    public JSONObject modifyRole(@RequestBody JSONObject jsonParam){

        int r_id = Integer.parseInt((String) jsonParam.get("rId"));
        String r_name = (String) jsonParam.get("rName");

        Role role = new Role();
        role.setRName(r_name);
        role.setRId(r_id);

        roleService.modifyRole(role);

        JSONObject result = new JSONObject();
        
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

}
