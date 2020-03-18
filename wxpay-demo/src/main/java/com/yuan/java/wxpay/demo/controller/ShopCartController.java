package com.yuan.java.wxpay.demo.controller;

import com.yuan.java.wxpay.demo.domain.BizResponse;
import com.yuan.java.wxpay.demo.domain.Book;
import com.yuan.java.wxpay.demo.domain.ShopCart;
import com.yuan.java.wxpay.demo.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车管理接口
 *
 * @author yuan
 */
@CrossOrigin
@RestController
@RequestMapping("/shopCart")
public class ShopCartController {

    @Autowired
    private ShopCartService service;

    @PostMapping
    public BizResponse addShopCart(@RequestHeader("token") String token, @RequestBody Book book) {
        Integer rows = service.addShopCart(token, book);
        if (null == rows) return BizResponse.ofFail(5003);
        if (rows == 0) return BizResponse.ofFail();
        return BizResponse.ofSuccess();
    }

    @DeleteMapping("/{id}")
    public BizResponse removeShopCart(@PathVariable Integer id) {
        Integer rows = service.removeShopCart(id);
        if (rows == 0) return BizResponse.ofFail();
        return BizResponse.ofSuccess();
    }

    @DeleteMapping("/book/{id}")
    public BizResponse removeShopCart(@RequestHeader("token") String token, @PathVariable Integer id) {
        Integer rows = service.removeShopCartByBook(id, token);
        if (null == rows) return BizResponse.ofFail(5003);
        if (rows == 0) return BizResponse.ofFail();
        return BizResponse.ofSuccess();
    }

    @GetMapping
    public BizResponse queryShopCarts(@RequestHeader("token") String token) {
        List<ShopCart> data = service.queryShopCart(token);
        if (null != data) return BizResponse.ofSuccess(data);
        return BizResponse.ofFail(5003);
    }

}
