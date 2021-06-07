package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzu.CloudClass.entity.Dictionary;
import com.fzu.CloudClass.entity.Parameter;
import com.fzu.CloudClass.mapper.ParameterMapper;
import com.fzu.CloudClass.service.IParameterService;
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
public class ParameterServiceImpl extends ServiceImpl<ParameterMapper, Parameter> implements IParameterService {

    @Autowired
    ParameterMapper parameterMapper;

    @Override
    public List<Parameter> getAllParameter(int pageNo, int pageSize) {
        QueryWrapper<Parameter> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByAsc("pa_id");
        IPage<Parameter> page = new Page<>(pageNo,pageSize);
        page = parameterMapper.selectPage(page, queryWrapper);
        return page.getRecords();
    }

    @Override
    public void addParameter(String pa_name, String description, double value, int status) {
        Parameter one = new Parameter();
        one.setDescription(description);
        one.setPaName(pa_name);
        one.setValue(value);
        one.setStatus(status);
        parameterMapper.insert(one);

    }

    @Override
    public void delParameter(int pa_id) {
        parameterMapper.deleteById(pa_id);
    }

    @Override
    public void modifyParameter(int pa_id, String pa_name, String description, double value, int status) {
        Parameter one = new Parameter();
        one.setPaId(pa_id);
        one.setDescription(description);
        one.setPaName(pa_name);
        one.setValue(value);
        one.setStatus(status);
        parameterMapper.updateById(one);
    }
}
