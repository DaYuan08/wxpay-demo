package com.yuan.java.wxpay.demo.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序配置
 *
 * @author yuan
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxMiniConfig extends WxPayConfig {

    private String appId;

    private String secret;

}
