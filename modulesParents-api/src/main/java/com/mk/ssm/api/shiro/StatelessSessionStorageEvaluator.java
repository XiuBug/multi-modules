/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.mk.ssm.api.shiro;

import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.subject.Subject;

/**
 *
 *
 * @author howiefh
 */
public class StatelessSessionStorageEvaluator implements SessionStorageEvaluator {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.shiro.mgt.SessionStorageEvaluator#isSessionStorageEnabled(
     * org.apache.shiro.subject.Subject)
     */
    @Override
    public boolean isSessionStorageEnabled(Subject subject) {
        return false;
    }

}
