package com.fzu.CloudClass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.entity.dto.PersonDto;
import com.fzu.CloudClass.mapper.PersonMapper;
import com.fzu.CloudClass.service.IPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * ʦ 服务实现类
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements IPersonService {

    @Autowired
    PersonMapper personMapper;

    @Override
    public void addPerson(Person person) {
        personMapper.insert(person);
    }

    @Override
    public Person getPersonByPhone(String phone) {
        return personMapper.selectOne(new QueryWrapper<Person>().eq("phone",phone));
    }

    @Override
    public Person getPersonByUsername(String username) {
        return personMapper.selectOne(new QueryWrapper<Person>().eq("username",username));
    }

    @Override
    public Person getPersonByOauth(String token) {
        return personMapper.selectOne(new QueryWrapper<Person>().eq("oauth",token));
    }

    @Override
    public List<Person> getAllPerson(int pageNo, int pageSize) {

        QueryWrapper<Person> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByAsc("pe_id").select("pe_id","pe_name","gender","grade","major","classes","is_teacher","username","phone","type");
        IPage<Person> page = new Page<>(pageNo,pageSize);
        page = personMapper.selectPage(page, queryWrapper);
        return page.getRecords();
    }

    @Override
    public Long countAllPerson(int pageNo, int pageSize) {
        QueryWrapper<Person> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByAsc("pe_id").select("pe_id","pe_name","gender","grade","major","classes","is_teacher","username","phone","type");
        IPage<Person> page = new Page<>(pageNo,pageSize);
        page = personMapper.selectPage(page, queryWrapper);
        return page.getTotal();
    }

    @Override
    public Person getManPersonByPhone(String phone) {
        return personMapper.selectOne(new QueryWrapper<Person>().eq("phone",phone).ne("type",0));
    }

    @Override
    public Person getManPersonByUsername(String username) {
        return personMapper.selectOne(new QueryWrapper<Person>().eq("username",username).ne("type",0));
    }

    @Override
    public Person getPersonById(int pe_id) {
        return personMapper.selectById(pe_id);
    }

    @Override
    public void modifyPerson(Person person) {
        personMapper.updateById(person);
    }
}
