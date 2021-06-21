package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzu.CloudClass.entity.Dictionary;
import com.fzu.CloudClass.entity.DictionaryDetail;
import com.fzu.CloudClass.mapper.DictionaryDetailMapper;
import com.fzu.CloudClass.service.IDictionaryDetailService;
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
public class DictionaryDetailServiceImpl extends ServiceImpl<DictionaryDetailMapper, DictionaryDetail> implements IDictionaryDetailService {

    @Autowired
    DictionaryDetailMapper dictionaryDetailMapper;

    @Override
    public List<DictionaryDetail> getAllDictionaryDetailById(int d_id) {

        QueryWrapper<DictionaryDetail> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("d_id",d_id);
        queryWrapper.orderByAsc("sort");
        return dictionaryDetailMapper.selectList(queryWrapper);
    }

    @Override
    public void addDictionaryDetail(int d_id, String dd_name, int isDefault,int sort) {
        DictionaryDetail one = new DictionaryDetail();
        one.setDId(d_id);
        one.setDdName(dd_name);
        one.setIsDefault(isDefault);
        one.setSort(sort);
        dictionaryDetailMapper.insert(one);
    }

    @Override
    public void delDictionaryDetail(int dd_id) {
        dictionaryDetailMapper.deleteById(dd_id);

    }

    @Override
    public void delDictionaryDetailBydId(int d_id) {
        QueryWrapper<DictionaryDetail> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("d_id",d_id);
        dictionaryDetailMapper.delete(queryWrapper);
    }

    @Override
    public void modifyDictionaryDetail(int dd_id, String dd_name,int is_default,int sort) {
        DictionaryDetail one = new DictionaryDetail();
        one.setDdId(dd_id);
        one.setDdName(dd_name);
        one.setIsDefault(is_default);
        one.setSort(sort);
        dictionaryDetailMapper.updateById(one);
    }

    @Override
    public void modifyDictionaryDetailDefault(int dd_id) {


    }

    @Override
    public int getCountAllDictionaryDetailByDId(int d_id) {
        QueryWrapper<DictionaryDetail> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("d_id",d_id);
        return dictionaryDetailMapper.selectCount(queryWrapper);
    }


}
