package com.mymall.dao;

import com.mymall.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    Order selectByOrderNo(long orderNo);

    List<Order> selectByUserId(Integer userId);

    List<Order> selectAllOrder();

    List<Order> selectOrderByStatusAndStartTime(@Param("status") Integer status,@Param("startTime") String startTime);

    void closeOrderCloseByOrderId(Integer id);
}