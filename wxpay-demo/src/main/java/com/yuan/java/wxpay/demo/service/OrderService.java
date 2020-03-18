package com.yuan.java.wxpay.demo.service;

import com.yuan.java.wxpay.demo.domain.Order;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单管理 业务层
 *
 * @author yuan
 */
public interface OrderService {

    /**
     * 新增
     * @param shopCartIds
     * @param prices
     * @param token
     * @return
     */
    Object addOrder(Integer[] shopCartIds, BigDecimal prices, String token);

    /**
     * 付款
     * @param order
     * @param token
     * @return
     */
    Object payment(Order order, String token);

    /**
     * 退款
     * @param order
     * @return
     */
    String refund(Order order);

    /**
     * 修改状态
     * @param id
     * @param state
     * @return
     */
    Integer changeState(Integer id, Integer state);

    /**
     * 查询
     * @param token
     * @return
     */
    List<Order> queryOrders(String token);
}
