package com.yuan.java.wxpay.demo.service.impl;

import com.yuan.java.wxpay.demo.domain.Book;
import com.yuan.java.wxpay.demo.domain.ShopCart;
import com.yuan.java.wxpay.demo.mapper.ShopCartMapper;
import com.yuan.java.wxpay.demo.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物车管理 实现层
 *
 * @author yuan
 */
@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    private ShopCartMapper mapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Integer addShopCart(String token, Book book) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Integer userId = (Integer) ops.get("token" + token);
        if (null == userId) return null;
        return mapper.addShopCart(new ShopCart(null, userId, book, null, null, null));
    }

    @Override
    public Integer removeShopCart(Integer id) {
        return mapper.deleteShopCart(id);
    }

    @Override
    public Integer removeShopCartByBook(Integer bookId, String token) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Integer userId = (Integer) ops.get("token" + token);
        if (null == userId) return null;
        return mapper.deleteShopCartByBook(bookId, userId);
    }

    @Override
    public List<ShopCart> queryShopCart(String token) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Integer userId = (Integer) ops.get("token" + token);
        if (null == userId) return null;
        return mapper.selectShopCarts(userId);
    }
}
