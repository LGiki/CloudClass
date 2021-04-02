package com.fzu.CloudClass.service.impl;

import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.mapper.PersonMapper;
import com.fzu.CloudClass.service.IPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ʦ 服务实现类
 * </p>
 *
 * @author djk
 * @since 2021-04-02
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements IPersonService {

}
