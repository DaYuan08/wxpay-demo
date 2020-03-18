package com.yuan.java.wxpay.demo.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信公众号配置
 *
 * @author yuan
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpConfig extends WxPayConfig {

    private String appId;

    private String secret;

}
