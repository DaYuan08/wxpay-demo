package com.yuan.java.wxpay.demo.service.impl;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.yuan.java.wxpay.demo.domain.Order;
import com.yuan.java.wxpay.demo.mapper.OrderMapper;
import com.yuan.java.wxpay.demo.mapper.ShopCartMapper;
import com.yuan.java.wxpay.demo.mapper.UserMapper;
import com.yuan.java.wxpay.demo.service.OrderService;
import com.yuan.java.wxpay.demo.utils.WxPayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单管理 实现层
 *
 * @author yuan
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Object addOrder(Integer[] shopCartIds, BigDecimal prices, String token) {
        Integer userId = (Integer) redisTemplate.opsForValue().get("token" + token);
        if(userId == null) return 5003;
        Order order = new Order();
        order.setUserId(userId);
        order.setPrices(prices);
        mapper.addOrder(order);
        if(order.getId() == null) return 5004;
        Integer rows = shopCartMapper.updateShopCart(order.getId(), shopCartIds);
        if(rows != shopCartIds.length) throw new RuntimeException("单个购物车修改失败，执行回滚操作");
        Object pay = null;
        try {
            pay = WxPayUtils.wxPay(userMapper.selectUser(userId).getOpenId(), prices.multiply(new BigDecimal(100)).intValue(), order.getId());
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return pay;
    }

    @Override
    public Object payment(Order order, String token) {
        Integer userId = (Integer) redisTemplate.opsForValue().get("token" + token);
        if(userId == null) return 5003;
        Object pay = null;
        try {
            pay = WxPayUtils.wxPay(userMapper.selectUser(userId).getOpenId(), order.getPrices().multiply(new BigDecimal(100)).intValue(), order.getId());
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return pay;
    }

    @Override
    public String refund(Order order) {
        String wxRefund = null;
        try {
            wxRefund = WxPayUtils.wxRefund(order.getId(), order.getPrices().multiply(new BigDecimal(100)).intValue());
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return wxRefund;
    }

    @Override
    public Integer changeState(Integer id, Integer state) {
        return mapper.updateOrder(new Order(id, null, state, null, null, null));
    }

    @Override
    public List<Order> queryOrders(String token) {
        Integer userId = (Integer) redisTemplate.opsForValue().get("token" + token);
        if(userId == null) return null;
        return mapper.selectOrders(userId);
    }
}
