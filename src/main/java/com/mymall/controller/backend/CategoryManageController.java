package com.mymall.controller.backend;

import com.mymall.common.ServerResponse;
import com.mymall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "get_category.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCategoryChildren(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId, HttpServletRequest request) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录");
//        }
//        User user = JsonUtil.string2obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//        //判断是否为管理员
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            return iCategoryService.getChildrenParallelCategory(categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("不是管理员用户，没有操作权限");
//        }
        return categoryService.getChildrenParallelCategory(categoryId);
    }

    /**
     * 添加节点
     */
    @RequestMapping(value = "add_category.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse addCategory(String categoryName, @RequestParam(value = "parentId",defaultValue = "0") Integer parentId, HttpServletRequest request) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录");
//        }
//        User user = JsonUtil.string2obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//        //判断是否为管理员
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //添加品类
//            return iCategoryService.addCategory(categoryName, parentId);
//        } else {
//            return ServerResponse.createByErrorMessage("不是管理员用户，没有操作权限");
//        }

        return categoryService.addCategory(categoryName, parentId);

    }

    /**
     * 修改节点名称
     */
    @RequestMapping(value = "set_category_name.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse setCategoryName(String categoryName, Integer categoryId, HttpServletRequest request) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录");
//        }
//        User user = JsonUtil.string2obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//        //判断是否为管理员
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            return iCategoryService.setCategory(categoryName, categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("不是管理员用户，没有操作权限");
//        }

        return categoryService.setCategory(categoryName, categoryId);

    }

    /**
     * 遍历当前节点所有子节点
     * @param categoryId
     * @param request
     * @return
     */
    @RequestMapping(value = "get_deep_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getAllCategoryChildrenById(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId, HttpServletRequest request) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录");
//        }
//        User user = JsonUtil.string2obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            return iCategoryService.selectCategoryAndChildrenById(categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("不是管理员用户，没有操作权限");
//        }

        return categoryService.selectCategoryAndChildrenById(categoryId);

    }
}
