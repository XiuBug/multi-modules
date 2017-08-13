package com.mk.ssm.common.utils;

/**
 * @author yq
 *
 */
public class Constants {

    public static final String RESOURCE_SERVER_NAME = "jeews-server";
    public static final String INVALID_CLIENT_DESCRIPTION = "客户端验证失败，如错误的client_id/client_secret。";
    public static final String INVALID_OAUTH_CODE = "授权码错误";
    public static final String INVALID_REFRESH_TOKEN = "refresh_token 错误";
    public static final String INVALID_USER_PASSWORD = "用户名或密码错误";

    /**
     * ABLE
     */
    public interface ABLE_CONFIG{
        public static final Integer ABLE = 1;
        public static final Integer UNABLE = 0;
        public static final Integer DEFAULT_ABLE = 1;
        public static final Integer NUM=-1;
    }
    public interface PAGE_CONFIG{
        public static final Integer PageNum=1;
        public static final Integer Pagesize=20;
    }

    public interface JWT_CONFIG{
        public static final Integer EXPIRYSECONDS = 60*2;
        public static final String JWTSIGNER = "Mj2/TPTG7qRqlN7vsmDyOA==";//自定义密钥字符串,编码时用。
    }
}
