package com.fzu.CloudClass.service;

import com.fzu.CloudClass.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
public interface IMenuService extends IService<Menu> {

    //获取父级菜单
    List<Menu> getAllMenu();

    List<Menu> getMenuListByParentId(int parent_id);

    //无差别获取menu
    List<Menu> getMenuList(int pageNo,int pageSize);

    //无差别获取menu总数
    Long countMenuList(int pageNo,int pageSize);

    String getNameById(int m_id);

    void modifyMenuById(Menu menu);

    int addMenu(Menu menu);

    void delMenuById(int m_id);

    void delMenuByParentId(int parent_id);





}
