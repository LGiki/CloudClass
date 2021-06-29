package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzu.CloudClass.entity.Dictionary;
import com.fzu.CloudClass.entity.Menu;
import com.fzu.CloudClass.mapper.MenuMapper;
import com.fzu.CloudClass.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> getAllMenu() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",-1);
        return menuMapper.selectList(queryWrapper);
    }

    @Override
    public List<Menu> getMenuListByParentId(int parent_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",parent_id);
        return menuMapper.selectList(queryWrapper);
    }

    @Override
    public List<Menu> getMenuList(int pageNo,int pageSize) {
        QueryWrapper<Menu> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByAsc("m_id");
        IPage<Menu> page = new Page<>(pageNo,pageSize);
        page = menuMapper.selectPage(page, queryWrapper);
        return page.getRecords();
    }

    @Override
    public Long countMenuList(int pageNo, int pageSize) {
        QueryWrapper<Menu> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByAsc("m_id");
        IPage<Menu> page = new Page<>(pageNo,pageSize);
        page = menuMapper.selectPage(page, queryWrapper);
        return page.getTotal();
    }

    @Override
    public String getNameById(int m_id) {
        return menuMapper.selectById(m_id).getMenuName();
    }

    @Override
    public void modifyMenuById(Menu menu) {
        menuMapper.updateById(menu);
    }

    @Override
    public int addMenu(Menu menu) {
        menuMapper.insert(menu);
        return menu.getMId();
    }

    @Override
    public void delMenuById(int m_id) {
        menuMapper.deleteById(m_id);
    }

    @Override
    public void delMenuByParentId(int parent_id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",parent_id);
        menuMapper.delete(queryWrapper);
    }
}
