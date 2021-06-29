package com.fzu.CloudClass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.CloudClass.entity.RoleMenu;

import java.util.List;

public interface IRoleMenuService extends IService<RoleMenu> {

    List<RoleMenu> getRoleMenuByRId(int r_id);

    void modifyRoleMenuStatus(int r_id,int m_id,int status);

    void addRoleMenu(int r_id,int m_id);
}
