package com.fzu.CloudClass.service;

import com.fzu.CloudClass.entity.Dictionary;
import com.fzu.CloudClass.entity.Parameter;
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
public interface IParameterService extends IService<Parameter> {

    int getCountAll();

    List<Parameter> getAllParameter(int pageNo, int pageSize);

    void addParameter(String pa_name,String description,double value,int status);

    void delParameter(int pa_id);

    void modifyParameter(int pa_id,String pa_name,String description,double value,int status);

    int countByDescription(String description);

}
