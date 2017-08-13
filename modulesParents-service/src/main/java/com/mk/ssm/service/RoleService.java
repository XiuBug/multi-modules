package com.mk.ssm.service;


import com.mk.ssm.bean.entity.Role;

import java.util.List;

/**
 * <p>Resource: yankee
 * <p>Date: 17-4-3
 * <p>Version: 1.0
 */
public interface RoleService {


    public void createRole(Role role);
    public void updateRole(Role role);
    public void deleteRole(Long roleId);

    public Role findOne(Long roleId);
    public List<Role> findAll();

    /**
     * 根据角色编号得到角色标识符列表
     * @param roleIds
     * @return
     */
    List<String> findRoles(List<Long> roleIds);

    /**
     * 根据角色编号得到权限字符串列表
     * @param roleIds
     * @return
     */
    List<String> findPermissions(List<Long> roleIds);
}
