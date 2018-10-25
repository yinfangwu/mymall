package com.mymall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对cookie操作的封装
 */
//使用注解@Slf4j,就不用写private final Logger logger = LoggerFactory.getLogger(CookieUtil.class)
@Slf4j
public class CookieUtil {
    //cookie写入域
    private final static String COOKIE_DOMAIN = ".mymall.com";//一级域名

    //login token
    private final static String COOKIE_NAME = "mymall_lgin_token";

    /**
     * 写入login cookie
     * @param response
     * @param token
     */
    public static void writeLoginToken(HttpServletResponse response, String token){
        Cookie cookie = new Cookie(COOKIE_NAME,token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        //不设置maxAge,只会存入内存，不会写入硬盘
        cookie.setMaxAge(3600*24*10);//有效期10天
        log.info("write cookieName:{}, cookieValue:{}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }

    /**
     * 读取login cookie
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie ck : cookies){
                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if(StringUtils.equals(ck.getName(), COOKIE_NAME)){
                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie ck:cookies){
                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//立即删除cookie

                    log.info("del cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck);
                }
            }
        }
    }
}
