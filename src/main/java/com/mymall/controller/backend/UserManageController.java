package com.mymall.controller.backend;

import com.mymall.common.Const;
import com.mymall.common.ServerResponse;
import com.mymall.pojo.User;
import com.mymall.service.UserService;
import com.mymall.util.CookieUtil;
import com.mymall.util.JsonUtil;
import com.mymall.util.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private UserService userService;

    /**
     * 管理员登录
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpServletResponse httpServletResponse, HttpSession session) {
        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole()== Const.ROLE.MANAGE_USER) {
                CookieUtil.writeLoginToken(httpServletResponse,session.getId());
                RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2string(user), Const.RedisCacheExTime.REDIS_SESSION_EXTIME);
                return ServerResponse.createBySuccess(user);
            }else {
                //不是管理员用户，无法登录
                return ServerResponse.createByErrorMessage("不是管理员，无法登录");
            }
        }
        return ServerResponse.createByErrorMessage("登录失败");
    }
}
