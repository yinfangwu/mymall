package com.mymall.dao;

import com.mymall.pojo.Cart;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByUserIdAndProductId(Integer userId, Integer productId);

    List<Cart> selectByUserId(Integer userId);

    int selectUnCheckedStatusCountByUserId(Integer userId);

    int deleteByUserIdProductIds(Integer userId, List<String> productIdList);

    int selectOrUnSelectByUserId(Integer userId, Integer productId, Integer status);

    int selectCartProductCount(Integer userId);

    List<Cart> selectCheckedCartListByUserId(Integer userId);
}