package com.yuan.java.wxpay.demo.service;

import com.yuan.java.wxpay.demo.domain.Book;

import java.util.List;

/**
 * 书籍管理 业务层
 *
 * @author yuan
 */
public interface BookService {

    /**
     * 查询
     * @param token
     * @return
     */
    List<Book> queryBook(String token);
}
