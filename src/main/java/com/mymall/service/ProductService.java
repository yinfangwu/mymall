package com.mymall.service;

import com.github.pagehelper.PageInfo;
import com.mymall.common.ServerResponse;
import com.mymall.pojo.Product;
import com.mymall.vo.ProductDetailVo;

public interface ProductService {
    ServerResponse<String> saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse getManageProductDetail(Integer productId);

    ServerResponse getManageProductList(int pageNum, int pageSize);

    ServerResponse getManageSearchList(Integer productId, String productName, int pageNum, int pageSize);

    ServerResponse<ProductDetailVo> getProductList(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(Integer categoryId, String keyword, int pageNum, int pageSize, String orderBy);
}
