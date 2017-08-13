/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.mk.ssm.api.realm;

import com.mk.ssm.bean.entity.JsonWebToken;
import com.mk.ssm.bean.entity.User;
import com.mk.ssm.common.utils.CollectionUtil;
import com.mk.ssm.service.RoleService;
import com.mk.ssm.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 *
 *
 * @author howiefh
 */
public class StatelessRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        // 仅支持JsonWebToken类型的Token
        return token instanceof JsonWebToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 根据用户名查找角色，请根据需求实现
        String username = (String) principals.getPrimaryPrincipal();

        User user = userService.findByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principals;
        principalCollection.clear();
        principalCollection.add(user, getName());

        Set<String> roles = CollectionUtil.listToSet(roleService.findRoles(user.getRoleIds()));
        Set<String> permissions = CollectionUtil.listToSet(roleService.findPermissions(user.getRoleIds()));
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JsonWebToken jsonWebToken = (JsonWebToken) token;
        String jwt = jsonWebToken.getToken();
        // 然后进行客户端消息摘要和服务器端消息摘要的匹配
        return new SimpleAuthenticationInfo("", jwt, getName());
    }
}