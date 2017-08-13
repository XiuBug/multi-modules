package com.mk.ssm.service.impl;


import com.mk.ssm.redis.cache.RedisCache;
import com.mk.ssm.mapper.dao.RoleDao;
import com.mk.ssm.bean.entity.Role;
import com.mk.ssm.service.ResourceService;
import com.mk.ssm.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Resource: yankee
 * <p>Date: 17-4-3
 * <p>Version: 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RedisCache cache;

    public void createRole(Role role) {
        roleDao.createRole(role);
    }

    public void updateRole(Role role) {
        roleDao.updateRole(role);
    }

    public void deleteRole(Long roleId) {
        roleDao.deleteRole(roleId);
    }

    @Override
    public Role findOne(Long roleId) {
        return roleDao.findOne(roleId);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public List<String> findRoles(List<Long> roleIds) {
        List<String> list = new ArrayList<String>();
        for (Long roleId : roleIds) {
            Role role = findOne(roleId);
            if (role != null) {
                list.add(role.getRole());
            }
        }
        return list;
    }

    @Override
    public List<String> findPermissions(List<Long> roleIds) {
        List<Long> resourceIds = new ArrayList<Long>();
        for (Long roleId : roleIds) {
            Role role = findOne(roleId);
            if (role != null) {
                resourceIds.addAll(role.getResourceIds());
            }
        }
        return resourceService.findPermissions(resourceIds);
    }
}
