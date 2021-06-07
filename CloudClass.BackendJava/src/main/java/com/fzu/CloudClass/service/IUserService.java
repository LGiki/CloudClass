package com.fzu.CloudClass.service;

import com.fzu.CloudClass.entity.Person;
import com.fzu.CloudClass.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author djk
 * @since 2021-04-13
 */
public interface IUserService extends IService<User> {

    User getUserByPhone(String phone);

    User getUserByUsername(String username);

}
