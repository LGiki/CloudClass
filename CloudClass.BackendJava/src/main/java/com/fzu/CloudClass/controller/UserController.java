package com.fzu.CloudClass.controller;


import com.alibaba.fastjson.JSONObject;
import com.fzu.CloudClass.entity.User;
import com.fzu.CloudClass.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djk
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/CloudClass/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @ResponseBody
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public JSONObject addUser(@RequestBody JSONObject jsonParam){

        User user = new User();

        user.setStatus(1);

        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("code", "200");

        return result;
    }

}
