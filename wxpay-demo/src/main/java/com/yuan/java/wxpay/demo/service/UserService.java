package com.yuan.java.wxpay.demo.service;

import com.yuan.java.wxpay.demo.domain.Decrypt;
import java.util.HashMap;

/**
 * 用户管理 业务层
 *
 * @author yuan
 */
public interface UserService {

    /**
     * 微信小程序授权
     * @param decrypt
     * @return
     */
    HashMap<String, Object> register(Decrypt decrypt);

    /**
     * 微信公众号授权
     * @param code
     * @return
     */
    HashMap<String, Object> registerMp(String code);

    /**
     * 登录
     * @param token
     * @return
     */
    String login(String token);

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    HashMap<String, Object> getInfo(String token);

}
