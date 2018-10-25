package com.mymall.controller.backend;

import com.mymall.common.ServerResponse;
import com.mymall.pojo.Product;
import com.mymall.service.FileService;
import com.mymall.service.ProductService;
import com.mymall.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    /**
     * 分页list
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize, HttpServletRequest request) {
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
//            return iProductService.getManageProductList(pageNum, pageSize);
//        }else {
//            return ServerResponse.createByErrorMessage("没有权限");
//        }

        return productService.getManageProductList(pageNum, pageSize);
    }

    /**
     * 条件查询-分页查询
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse searchList(String productName,Integer productId,@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, HttpServletRequest request) {
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
//            return iProductService.getManageSearchList(productId, productName,pageNum,pageSize);
//        }else {
//            return ServerResponse.createByErrorMessage("没有权限");
//        }

        return productService.getManageSearchList(productId, productName,pageNum,pageSize);
    }

    /**
     * 更新或添加产品
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse saveProduct(Product product, HttpServletRequest request) {
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
//            return iProductService.saveOrUpdateProduct(product);
//        }else {
//            return ServerResponse.createByErrorMessage("没有权限");
//        }

        return productService.saveOrUpdateProduct(product);
    }

    /**
     * 修改产品状态
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(Integer productId,Integer status,HttpServletRequest  request) {
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
//            return iProductService.setSaleStatus(productId,status);
//        }else {
//            return ServerResponse.createByErrorMessage("没有权限");
//        }

        return productService.setSaleStatus(productId,status);
    }


    /**
     * 商品详情
     * @param productId
     * @param request
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getProductDetail(Integer productId,HttpServletRequest request) {
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
//            return iProductService.getManageProductDetail(productId);
//        }else {
//            return ServerResponse.createByErrorMessage("没有权限");
//        }
        return productService.getManageProductDetail(productId);
    }

    /**
     * 文件上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse uploadFile(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request) {

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
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            String uploadFilePath = iFileService.upload(file, path);
//            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + uploadFilePath;
//            Map fileMap = new HashMap();
//            fileMap.put("uri", uploadFilePath);
//            fileMap.put("url", url);
//            return ServerResponse.createBySuccess(fileMap);
//        }else {
//            return ServerResponse.createByErrorMessage("没有权限");
//        }


        String path = request.getSession().getServletContext().getRealPath("upload");
        String uploadFilePath = fileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + uploadFilePath;
        Map fileMap = new HashMap();
        fileMap.put("uri", uploadFilePath);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    /**
     * 富文本上传-------富文本上传根据使用的插件，有固定的返回值设置，这里使用Simditor
     *         {
     *            "success": true/false,
     *                "msg": "error message", # optional
     *            "file_path": "[real file path]"
     *        }
     */
    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richTextUpload(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = new HashMap();
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            resultMap.put("success", false);
//            resultMap.put("msg", "请先登录管理员");
//            return resultMap;
//        }
//        User user = JsonUtil.string2obj(RedisShardedPoolUtil.get(loginToken), User.class);
//
//        if (user==null) {
//            resultMap.put("success", false);
//            resultMap.put("msg", "请先登录管理员");
//            return resultMap;
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            String uploadFilePath = iFileService.upload(file, path);
//            if (StringUtils.isBlank(uploadFilePath)) {
//                resultMap.put("success", false);
//                resultMap.put("msg", "上传失败");
//                return resultMap;
//            }
//            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + uploadFilePath;
//            resultMap.put("success", true);
//            resultMap.put("msg", "上传成功");
//            resultMap.put("file_path", url);
//            //插件约定
//            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
//            return resultMap;
//        }else {
//            resultMap.put("success", false);
//            resultMap.put("msg", "没有权限");
//            return resultMap;
//        }


        String path = request.getSession().getServletContext().getRealPath("upload");
        String uploadFilePath = fileService.upload(file, path);
        if (StringUtils.isBlank(uploadFilePath)) {
            resultMap.put("success", false);
            resultMap.put("msg", "上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + uploadFilePath;
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
        resultMap.put("file_path", url);
        //插件约定
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }
}
