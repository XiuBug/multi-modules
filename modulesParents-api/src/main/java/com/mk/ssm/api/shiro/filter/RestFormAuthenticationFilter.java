/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.mk.ssm.api.shiro.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *
 * @author howiefh
 */

/**
 * 表单认证过滤器
 */
public class RestFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    // http://stackoverflow.com/questions/7906647/override-shiro-unauthorized-page
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        sendChallenge(request, response);
    }

    protected void sendChallenge(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String loginUrl = getLoginUrl();

        // org.apache.shiro.web.util.WebUtils.issueRedirect(ServletRequest,
        // ServletResponse, String, Map, boolean, boolean)
        // org.apache.shiro.web.util.RedirectView.renderMergedOutputModel(Map,
        // HttpServletRequest, HttpServletResponse)
        // Prepare name URL.
        StringBuilder targetUrl = new StringBuilder();
        if (loginUrl.startsWith("/")) {
            // Do not apply context path to relative URLs.
            targetUrl.append(httpRequest.getContextPath());
        }
        targetUrl.append(loginUrl);

        String url = targetUrl.toString();
        url = httpResponse.encodeRedirectURL(url);
        httpResponse.setHeader("Location", url);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
