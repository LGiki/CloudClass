package com.fzu.CloudClass.service;

import com.fzu.CloudClass.entity.Person;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzu.CloudClass.entity.dto.PersonDto;

import java.util.List;

/**
 * <p>
 * ʦ 服务类
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
public interface IPersonService extends IService<Person> {

    void addPerson(Person person);

    Person getPersonByPhone(String phone);

    Person getPersonByUsername(String username);

    Person getPersonByOauth(String token);

    List<Person> getAllPerson(int pageNo, int pageSize);

    Person getManPersonByPhone(String phone);

    Person getManPersonByUsername(String username);

    Person getPersonById(int pe_id);

}
