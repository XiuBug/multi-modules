package com.mk.ssm.api.web;

import com.auth0.jwt.JWTSigner;
import com.mk.ssm.bean.entity.User;
import com.mk.ssm.common.utils.CollectionUtil;
import com.mk.ssm.common.utils.Constants;
import com.mk.ssm.service.RoleService;
import com.mk.ssm.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>Resource: yankee
 * <p>Date: 17-4-3
 * <p>Version: 1.0
 */
@Api(value = "LoginController",description = "登陆控制器",consumes = "application/json")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "登陆",notes = "传入User对象进行登陆(只需组装username,password)",response = Map.class)
    @ApiImplicitParam(name = "用户",value = "user",required = true,dataType = "Map",paramType = "User")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (username == null) {
            throw new NullPointerException("用户名或密码错误！");
        }
        User u = userService.findByUsername(username);
        if (u == null) {
            throw new UnknownAccountException("该用户不存在！");// 没找到帐号
        }

        if (Boolean.TRUE.equals(u.getLocked())) {
            throw new LockedAccountException("账号已被锁定！"); // 帐号锁定
        }

        if (!userService.passwordsMatch(u,password)) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        /**
         * 前端通过表单传入username和password.
         * 通过JWTSigner.class指定了header,payload,和根据header,payload生成的signature
         * 生成了jwt字符串。
         * 通过return map把jwt字符串返回给了前台用户
         * 以后前台用户在访问后台资源时,需要拿着token
         */
        //加密过程要指定密钥,需要自行定义
        JWTSigner signer = new JWTSigner(Constants.JWT_CONFIG.JWTSIGNER);
        JWTSigner.Options options = new JWTSigner.Options();
        // 7 * 24 * 60 * 60 = 604800
        //设置token失效时间
        options.setExpirySeconds(Constants.JWT_CONFIG.EXPIRYSECONDS);
        Map<String, Object> claims = new HashMap<String, Object>();
        Set<String> roles = CollectionUtil.listToSet(roleService.findRoles(u.getRoleIds()));
        Set<String> permissions = CollectionUtil.listToSet(roleService.findPermissions(u.getRoleIds()));
        //该用户的一些权限信息
        claims.put("perms", permissions);
        //发起请求的用户的信息
        claims.put("iss", u.getUsername());
        //sign()方法把header,payload,signature生成jwt(token)字符串
        String token = signer.sign(claims, options);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", token);
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("id", u.getId());
        userMap.put("username", u.getUsername());
        userMap.put("perms", permissions);
        userMap.put("roles", roles);
        map.put("user", userMap);
        return map;
    }

}
