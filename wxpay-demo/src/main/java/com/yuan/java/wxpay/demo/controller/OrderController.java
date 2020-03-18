package com.yuan.java.wxpay.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.yuan.java.wxpay.demo.domain.BizResponse;
import com.yuan.java.wxpay.demo.domain.Order;
import com.yuan.java.wxpay.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单管理接口
 *
 * @author yuan
 */
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private WxPayService wxPayService;

    @PostMapping
    public BizResponse addOrder(@RequestHeader("token") String token, @RequestBody String req) {
        JSONObject object = JSONObject.parseObject(req);
        Integer[] shopCartIds = object.getJSONArray("shopCartIds").toArray(new Integer[0]);
        BigDecimal prices = object.getBigDecimal("prices");
        Object data = service.addOrder(shopCartIds, prices, token);
        if (data == null) return BizResponse.ofFail();
        if (data.getClass().equals(Integer.class)) return BizResponse.ofFail((Integer) data);
        return BizResponse.ofSuccess(data);
    }

    @PostMapping("/pay")
    public BizResponse pay(@RequestHeader("token") String token, @RequestBody Order order) {
        Object data = service.payment(order, token);
        if (data.getClass().equals(Integer.class)) return BizResponse.ofFail((Integer) data);
        if (data == null) return BizResponse.ofFail();
        return BizResponse.ofSuccess(data);
    }

    @PostMapping("/refund")
    public BizResponse refund(@RequestBody Order order) {
        String data = service.refund(order);
        if (data == null) return BizResponse.ofFail();
        return BizResponse.ofSuccess(data);
    }

    @GetMapping
    public BizResponse query(@RequestHeader("token") String token) {
        List<Order> data = service.queryOrders(token);
        if(data == null) return BizResponse.ofFail(5003);
        return BizResponse.ofSuccess(data);
    }

    @PostMapping("/notifyurl/pay")
    public String payNotifyUrl(@RequestBody String body) {
        try {
            WxPayOrderNotifyResult notifyResult  = wxPayService.parseOrderNotifyResult(body);
            Integer id = Integer.valueOf(notifyResult.getOutTradeNo().split("yuan-")[1]);
            service.changeState(id, 1);
        } catch (WxPayException e) {
            return WxPayNotifyResponse.fail("失败");
        }
        return WxPayNotifyResponse.success("成功");
    }

    @PostMapping("/notifyurl/refund")
    public Object refundNotifyUrl(@RequestBody String body) {
        try {
            WxPayRefundNotifyResult notifyResult = wxPayService.parseRefundNotifyResult(body);
            WxPayRefundNotifyResult.ReqInfo reqInfo = notifyResult.getReqInfo();
            Integer id = Integer.valueOf(reqInfo.getOutTradeNo().split("yuan-")[1]);
            service.changeState(id, 2);
        } catch (WxPayException e) {
            return WxPayNotifyResponse.fail("失败");
        }
        return WxPayNotifyResponse.success("成功");
    }

}
