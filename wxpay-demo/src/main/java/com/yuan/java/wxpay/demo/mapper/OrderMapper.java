package com.yuan.java.wxpay.demo.mapper;

import com.yuan.java.wxpay.demo.domain.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单管理 数据层
 *
 * @author yuan
 */
@Mapper
public interface OrderMapper {

    /**
     * 新增
     * @param order
     * @return
     */
    Integer addOrder(Order order);

    /**
     * 修改
     * @param order
     * @return
     */
    Integer updateOrder(Order order);

    /**
     * 查询
     * @param userId
     * @return
     */
    List<Order> selectOrders(Integer userId);

}
