package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzu.CloudClass.entity.Dictionary;
import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.mapper.DictionaryMapper;
import com.fzu.CloudClass.service.IDictionaryService;
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
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

    @Autowired
    DictionaryMapper dictionaryMapper;

    @Override
    public List<Dictionary> getAllDictionary(int pageNo, int pageSize) {

        QueryWrapper<Dictionary> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByAsc("d_id");
        IPage<Dictionary> page = new Page<>(pageNo,pageSize);
        page = dictionaryMapper.selectPage(page, queryWrapper);
        return page.getRecords();
    }

    @Override
    public void addDictionary(String chineseName, String englishName) {
        Dictionary one = new Dictionary();
        one.setChineseName(chineseName);
        one.setEnglishName(englishName);
        dictionaryMapper.insert(one);
    }

    @Override
    public void delDictionary(int d_id) {
        dictionaryMapper.deleteById(d_id);
    }

    @Override
    public void modifyDictionary(int d_id, String chineseName, String englishName) {
        Dictionary one = new Dictionary();
        one.setDId(d_id);
        one.setEnglishName(englishName);
        one.setChineseName(chineseName);
        dictionaryMapper.updateById(one);
    }
}
