package com.mymall.dao;

import com.mymall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();

    List<Product> selectListByIdAndName(@Param("productId") Integer productId, @Param("productName") String productName);

    List<Product> selectByNameAndCategoryIds(String keyword, List<Integer> categoryIdList);

    Integer selectStockByPrimaryKey(Integer productId);
}