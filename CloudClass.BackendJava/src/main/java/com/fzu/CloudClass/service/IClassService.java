package com.fzu.CloudClass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.CloudClass.entity.Class;

import java.util.List;

public interface IClassService extends IService<Class> {

    List<Class> getAllClass();

    int addClass(String cc_name);

    int countByName(String cc_name);

    int getIdByName(String cc_name);

    String getNameById(int cc_id);

    void modify(Class one);

}
