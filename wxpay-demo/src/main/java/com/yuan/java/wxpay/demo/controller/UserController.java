package com.yuan.java.wxpay.demo.controller;

import com.yuan.java.wxpay.demo.domain.BizResponse;
import com.yuan.java.wxpay.demo.domain.Decrypt;
import com.yuan.java.wxpay.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 用户管理接口
 *
 * @author yuan
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/auth")
    public BizResponse auth(@RequestBody Decrypt decrypt) {
        HashMap<String, Object> data = service.register(decrypt);
        if(data != null) return BizResponse.ofSuccess(data);
        else return BizResponse.ofFail(5002);
    }

    @PostMapping("/authMp")
    public BizResponse authMp(@RequestBody String code) {
        HashMap<String, Object> data = service.registerMp(code);
        if(data != null) return BizResponse.ofSuccess(data);
        else return BizResponse.ofFail(5002);
    }

    @PostMapping("/login")
    public BizResponse login(@RequestHeader("token") String token) {
        String newToken = service.login(token);
        if (null != newToken) return BizResponse.ofSuccess(newToken);
        return BizResponse.ofFail(5003);
    }

    @GetMapping
    public BizResponse getInfo(@RequestHeader("token") String token) {
        HashMap<String, Object> info = service.getInfo(token);
        if(null != info) return BizResponse.ofSuccess(info);
        else return BizResponse.ofFail(5003);
    }

    @GetMapping("/redirect")
    public BizResponse test(String data) {
        System.out.println(data);
        return BizResponse.ofSuccess(data);
    }
}
