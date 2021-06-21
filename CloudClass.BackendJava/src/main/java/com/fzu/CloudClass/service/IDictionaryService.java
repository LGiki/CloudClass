package com.fzu.CloudClass.service;

import com.fzu.CloudClass.entity.Dictionary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.CloudClass.entity.Person;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
public interface IDictionaryService extends IService<Dictionary> {

    int getCountAll();

    List<Dictionary> getAllDictionary(int pageNo, int pageSize);

    int addDictionary(String chineseName,String englishName);

    void delDictionary(int d_id);

    void modifyDictionary(int i_id,String chineseName,String englishName);

}
