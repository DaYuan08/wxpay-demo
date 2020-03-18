package com.yuan.java.wxpay.demo.service;

import com.yuan.java.wxpay.demo.domain.Book;
import com.yuan.java.wxpay.demo.domain.ShopCart;

import java.util.List;

/**
 * 购物车管理 业务层
 *
 * @author yuan
 */
public interface ShopCartService {

    /**
     * 新增
     * @param token
     * @param book
     * @return
     */
    Integer addShopCart(String token, Book book);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer removeShopCart(Integer id);

    /**
     * 根据书籍删除
     * @param bookId
     * @param token
     * @return
     */
    Integer removeShopCartByBook(Integer bookId, String token);

    /**
     * 查询
     * @param token
     * @return
     */
    List<ShopCart> queryShopCart(String token);

}
