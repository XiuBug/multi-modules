package com.mk.ssm.mapper.dao;



import com.mk.ssm.bean.dto.UserDto;
import com.mk.ssm.bean.entity.User;

import java.util.List;

/**
 * <p>Resource: yankee
 * <p>Date: 17-4-3
 * <p>Version: 1.0
 */
public interface UserDao {

    void createUser(UserDto userDto);

    void updateUser(UserDto userDto);

    void deleteUser(Long userId);


    User findOne(Long userId);


    List<User> findAll();


    User findByUsername(String username);


    List<User> findUsers(User user);
}
