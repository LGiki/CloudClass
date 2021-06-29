package com.fzu.CloudClass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.CloudClass.entity.Role;

import java.util.List;

public interface IRoleService extends IService<Role> {

    List<Role> getAllRole();

    void modifyRole(Role role);

}
