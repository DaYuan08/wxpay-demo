package com.yuan.java.wxpay.demo.controller;

import com.yuan.java.wxpay.demo.domain.BizResponse;
import com.yuan.java.wxpay.demo.domain.Book;
import com.yuan.java.wxpay.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 书籍管理接口
 *
 * @author yuan
 */
@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping
    public BizResponse queryBook(@RequestHeader("token") String token) {
        List<Book> data = service.queryBook(token);
        if (data == null) return BizResponse.ofFail(5003);
        if (data.size() == 0) return BizResponse.ofFail();
        return BizResponse.ofSuccess(data);
    }

}
