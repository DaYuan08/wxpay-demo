package com.yuan.java.wxpay.demo.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.yuan.java.wxpay.demo.domain.BizResponse;
import com.yuan.java.wxpay.demo.domain.Decrypt;
import com.yuan.java.wxpay.demo.domain.User;
import com.yuan.java.wxpay.demo.mapper.UserMapper;
import com.yuan.java.wxpay.demo.service.UserService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;

/**
 * 用户管理 实现层
 *
 * @author yuan
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private WxMpService wxMpService;

    private WxMpUser wxMpUser;

    private WxMaUserInfo wxMaUserInfo;

    private WxMpOAuth2AccessToken wxMpOAuth2AccessToken;

    private WxMaJscode2SessionResult wxMaJscode2SessionResult;

    @Override
    public HashMap<String, Object> register(Decrypt decrypt) {
        try {
            wxMaJscode2SessionResult = wxMaService.jsCode2SessionInfo(decrypt.getCode());
            wxMaUserInfo = wxMaService.getUserService().getUserInfo(wxMaJscode2SessionResult.getSessionKey(), decrypt.getEncryptedData(), decrypt.getIv());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return verification(new User(null, wxMaUserInfo.getUnionId(), wxMaUserInfo.getOpenId(), wxMaUserInfo.getNickName(), wxMaUserInfo.getGender(), wxMaUserInfo.getAvatarUrl(), null, null));
    }

    @Override
    public HashMap<String, Object> registerMp(String code) {
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return verification(new User(null, wxMpUser.getUnionId(), wxMpUser.getOpenId(), wxMpUser.getNickname(), wxMpUser.getSex().toString(), wxMpUser.getHeadImgUrl(), null, null));
    }

    @Override
    public String login(String token) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Object id = ops.get("token" + token);
        if (null == id) {
            return null;
        } else {
            redisTemplate.delete(token);
            String newToken = getToken();
            ops.set("token" + token, id, Duration.ofHours(1));
            return newToken;
        }
    }

    @Override
    public HashMap<String, Object> getInfo(String token) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Integer id = (Integer) ops.get("token" + token);
        if (null == id) {
            return null;
        } else {
            User user = mapper.selectUser(id);
            HashMap<String, Object> data = new HashMap<>();
            data.put("name", user.getName());
            data.put("avatar", user.getAvatar());
            return data;
        }
    }

    private String getToken() {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        while (true) {
            String token = UUID.randomUUID().toString().replace("-", "");
            if (null == ops.get("token" + token)) return token;
        }
    }

    private HashMap<String, Object> verification(User user) {
        if(user.getUnionId() == null) return null;
        Integer id = mapper.hasUser(user.getUnionId());
        if (null == id) {
            user.setId(mapper.addUser(user));
        }else {
            user.setId(id);
            mapper.updateUser(user);
        }
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String token = getToken();
        ops.set("token" + token, user.getId(), Duration.ofHours(1));
        HashMap<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("name", user.getName());
        data.put("avatar", user.getAvatar());
        return data;
    }
}
