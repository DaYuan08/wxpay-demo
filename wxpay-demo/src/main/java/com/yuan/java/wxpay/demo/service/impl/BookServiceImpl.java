package com.yuan.java.wxpay.demo.service.impl;

import com.yuan.java.wxpay.demo.domain.Book;
import com.yuan.java.wxpay.demo.mapper.BookMapper;
import com.yuan.java.wxpay.demo.mapper.ShopCartMapper;
import com.yuan.java.wxpay.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 书籍管理 实现层
 *
 * @author yuan
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper mapper;

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<Book> queryBook(String token) {
        List<Book> books = mapper.selectBook();
        if (token != null) {
            Integer userId = (Integer) redisTemplate.opsForValue().get("token" + token);
            if(userId == null) return null;
            books.forEach(book -> {
                if(book.getId().equals(shopCartMapper.selectSingle(userId, book.getId()))) book.setIsAdd(true);
            });
        }
        return books;
    }
}
