package com.fzu.CloudClass.service;

import com.fzu.CloudClass.entity.Person;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * ʦ 服务类
 * </p>
 *
 * @author djk
 * @since 2021-04-02
 */
public interface IPersonService extends IService<Person> {

    void addPerson(Person person);

}
