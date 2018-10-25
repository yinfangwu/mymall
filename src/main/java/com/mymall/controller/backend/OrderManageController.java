package com.mymall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mymall.common.Const;
import com.mymall.common.ServerResponse;
import com.mymall.service.OrderService;
import com.mymall.util.CookieUtil;
import com.mymall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpServletRequest request,
                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录");
//        }
//        User user = JsonUtil.string2obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if (user==null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            return iOrderService.getManageList(pageNum, pageSize);
//        }else {
//            return ServerResponse.createByErrorMessage("没有权限");
//        }

        return orderService.getManageList(pageNum, pageSize);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> list(HttpServletRequest request, Long orderNo) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录");
//        }
//        User user = JsonUtil.string2obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if (user==null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            return iOrderService.getManageDetail(orderNo);
//        }else {
//            return ServerResponse.createByErrorMessage("没有权限");
//        }

        return orderService.getManageDetail(orderNo);
    }


    /**
     * 后台搜索，需要扩展
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> search(HttpServletRequest request, Long orderNo,
                                           @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录");
//        }
//        User user = JsonUtil.string2obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if (user==null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            return iOrderService.getManageSearch(orderNo,pageNum,pageSize);
//        }else {
//            return ServerResponse.createByErrorMessage("没有权限");
//        }

        return orderService.getManageSearch(orderNo,pageNum,pageSize);

    }

    //发货
    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderSendGoods(HttpServletRequest request, Long orderNo) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录");
//        }
//        User user = JsonUtil.string2obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if (user==null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            return iOrderService.getManageSendGoods(orderNo);
//        }else {
//            return ServerResponse.createByErrorMessage("没有权限");
//        }

        return orderService.getManageSendGoods(orderNo);

    }
}
