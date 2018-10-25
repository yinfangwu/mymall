package com.mymall.controller.portal;

import com.mymall.common.Const;
import com.mymall.common.ServerResponse;
import com.mymall.pojo.User;
import com.mymall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/springsessionTest")
public class UserSpringSessionController {

    @Autowired
    private UserService userService;

    /**
     *用户登录
     */
    @RequestMapping(value = "login.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse){

//        fixme 异常测试
//        int i= 10/0;

        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER,response.getData());
//            //写入cookie
//            CookieUtil.writeLoginToken(httpServletResponse,session.getId());
//            //将登录用户信息存入redis，有效时间为30分钟
//            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2string(response.getData()), Const.RedisCacheExTime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    /**
     * 登出
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        /*String loginToken = CookieUtil.readLoginToken(request);
        CookieUtil.delLoginToken(request, response);
        RedisShardedPoolUtil.del(loginToken);*/
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }



    /**
     * 获取当前用户信息
     * @return
     */
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpServletRequest request,HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        /*String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        User user = JsonUtil.string2obj(RedisShardedPoolUtil.get(loginToken), User.class);*/

        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }
}
