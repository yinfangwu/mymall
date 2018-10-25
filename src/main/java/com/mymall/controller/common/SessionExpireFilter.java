package com.mymall.controller.common;


import com.github.pagehelper.StringUtil;
import com.mymall.common.Const;
import com.mymall.pojo.User;
import com.mymall.util.CookieUtil;
import com.mymall.util.JsonUtil;
import com.mymall.util.RedisShardedPoolUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 重置session过期时间
 */
public class SessionExpireFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 判断用户是否登录，登录则重置redis里的session时间
     * 1.读取cookie中的loginToken
     * 2.token判空，从redis中获取user信息
     * 3.user判空，刷新redis中对于key的过期时间
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isNotEmpty(loginToken)){
            String userStr = RedisShardedPoolUtil.get(loginToken);
            User user = JsonUtil.string2obj(userStr, User.class);
            if (user != null){
                RedisShardedPoolUtil.expire(loginToken, Const.RedisCacheExTime.REDIS_SESSION_EXTIME);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
