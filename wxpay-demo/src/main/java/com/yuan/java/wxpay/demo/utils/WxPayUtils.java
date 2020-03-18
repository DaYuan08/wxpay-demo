package com.yuan.java.wxpay.demo.utils;

import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.yuan.java.wxpay.demo.config.WxMiniConfig;
import com.yuan.java.wxpay.demo.config.WxMpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 微信支付
 *
 * @author yuan
 */
@Component
public class WxPayUtils {

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private static WxMpConfig wxMpConfig;

    @Autowired
    private static WxMiniConfig wxMiniConfig;

    private static WxPayUtils wxPayUtil;

    @PostConstruct
    public void init() {
        wxPayUtil = this;
        wxPayUtil.wxPayService = this.wxPayService;
    }

    public static Object wxPay(String openId, Integer price, Integer orderId, String state) throws WxPayException {
        String timeStart = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String TimeExpire = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis() + (1000 * 60 * 15)));
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setBody("购物订单 for yuan");
        request.setOutTradeNo("yuan-" + String.valueOf(orderId));
        request.setTotalFee(price);
        request.setOpenid(openId);
        request.setTimeStart(timeStart);
        request.setTimeExpire(TimeExpire);
        request.setTradeType("JSAPI");
        request.setNotifyUrl("http://9eb7437c.ngrok.io/order/notifyurl/pay");
        request.setSpbillCreateIp("117.132.192.103");
        if (state == "mini") wxPayUtil.wxPayService.setConfig(wxMiniConfig);
        else wxPayUtil.wxPayService.setConfig(wxMpConfig);
        return wxPayUtil.wxPayService.createOrder(request);
    }

    public static String wxRefund(Integer orderId, Integer price) throws WxPayException {
        WxPayRefundRequest request = new WxPayRefundRequest();
        String out_refund_no = UUID.randomUUID().toString().replaceAll("-", "");
        request.setOutRefundNo(out_refund_no);
        request.setOutTradeNo("yuan-" + String.valueOf(orderId));
        request.setTotalFee(price);
        request.setRefundFee(price);
        request.setNotifyUrl("http://9eb7437c.ngrok.io/order/notifyurl/refund");
        WxPayRefundResult result = wxPayUtil.wxPayService.refund(request);
        if (result.getReturnCode().equals("SUCCESS")) return result.getReturnCode();
        return null;
    }

}
