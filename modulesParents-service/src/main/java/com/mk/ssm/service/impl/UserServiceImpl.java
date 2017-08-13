package com.mk.ssm.service.impl;


import com.mk.ssm.common.enums.ResultEnum;
import com.mk.ssm.redis.cache.RedisCache;
import com.mk.ssm.mapper.dao.UserDao;
import com.mk.ssm.bean.dto.UserDto;
import com.mk.ssm.bean.entity.User;
import com.mk.ssm.common.exception.BizException;
import com.mk.ssm.service.PasswordHelper;
import com.mk.ssm.service.RoleService;
import com.mk.ssm.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

/**
 * <p>Resource: yankee
 * <p>Date: 17-4-3
 * <p>Version: 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisCache cache;

    /**
     * 创建用户
     *
     * @param user
     */
    public void createUser(User user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        UserDto userDto = new UserDto();
        try {
            BeanUtils.copyProperties(userDto, user);
            userDto.setRole_ids(user.getRoleIdsStr());
            userDao.createUser(userDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUsers(List<User> list) {
        for (User user : list) {
            createUser(user);
        }
    }

    @Override
    public void updateUser(User user) {
        UserDto userDto = new UserDto();
        try {
            BeanUtils.copyProperties(userDto, user);
            userDto.setRole_ids(user.getRoleIdsStr());
            userDao.updateUser(userDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(Long userId) {
        userDao.deleteUser(userId);
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword) {
        User user = userDao.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        updateUser(user);
    }

    @Override
    public User findOne(Long userId) {
        return userDao.findOne(userId);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<String> findRoles(String username) {
        User user = findByUsername(username);
        if (user == null) {
            return Collections.EMPTY_LIST;
        }
        return roleService.findRoles(user.getRoleIds()); // 目的在于：程序健壮性（user.roleIds可不算数，需要去role中验证/获取）
    }

    @Override
    public List<String> findPermissions(String username) {
        User user = findByUsername(username);
        if (user == null) {
            return Collections.EMPTY_LIST;
        }
        return roleService.findPermissions(user.getRoleIds());
    }

    @Override
    public List<User> findUsers(String idOrUsername){
        // 参数处理 （idOrUsername）
        User user = new User();
        if (StringUtils.isNumeric(idOrUsername)) {
            user.setId(Long.parseLong(idOrUsername));
        } else {
            user.setUsername(idOrUsername);
        }

        // 业务处理
        List<User> users = userDao.findUsers(user);
        if (users == null || users.size() == 0) {
            throw new BizException(ResultEnum.DB_SELECTONE_IS_NULL.getMsg());
        }
        return users;
    }

    @Override
    public boolean isValidUser(String username, String password) {
        User user = findByUsername(username);
        if (user == null){
            return false;
        }
        User checkUser = new User();
        checkUser.setUsername(username);
        checkUser.setPassword(password);
        passwordHelper.encryptPassword(checkUser);
        return user.getPassword().equals(checkUser.getPassword());
    }

    @Override
    public boolean passwordsMatch(User user, String password) {
        User cUser = findByUsername(user.getUsername());
        cUser.setPassword(password);
        return passwordHelper.passwordsMatch(user,cUser);
    }

}
