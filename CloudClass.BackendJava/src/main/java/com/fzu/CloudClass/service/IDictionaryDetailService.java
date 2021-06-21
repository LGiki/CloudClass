package com.fzu.CloudClass.service;

import com.fzu.CloudClass.entity.Dictionary;
import com.fzu.CloudClass.entity.DictionaryDetail;
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
public interface IDictionaryDetailService extends IService<DictionaryDetail> {

    List<DictionaryDetail> getAllDictionaryDetailById(int d_id);

    void addDictionaryDetail(int d_id,String dd_name,int isDefault,int sort);

    void delDictionaryDetail(int dd_id);

    void delDictionaryDetailBydId(int d_id);

    void modifyDictionaryDetail(int dd_id,String dd_name,int is_default,int sort);

    void modifyDictionaryDetailDefault(int dd_id);

    int getCountAllDictionaryDetailByDId(int d_id);

}
