/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.mk.ssm.bean.entity;

import org.apache.shiro.authc.AuthenticationToken;

/**
 *
 *
 * @author howiefh
 */
public class JsonWebToken implements AuthenticationToken {

    private static final long serialVersionUID = 1389130131607655713L;
    private String token;//这里的token是一个字符串

    public JsonWebToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.shiro.authc.AuthenticationToken#getPrincipal()
     */
    @Override
    public Object getPrincipal() {
        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.shiro.authc.AuthenticationToken#getCredentials()
     */
    @Override
    public Object getCredentials() {
        return getToken();
    }

}
