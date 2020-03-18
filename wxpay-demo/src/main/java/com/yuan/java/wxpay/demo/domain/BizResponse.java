package com.yuan.java.wxpay.demo.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一错误码
 *
 * @author yuan
 */
@Data
public class BizResponse<T> {

    public Integer code;

    public String desc;

    private T data;

    private final static Map<Integer, String> CODE_DESCS = new HashMap<Integer, String>();

    static {
        CODE_DESCS.put(2000, "操作成功");
        CODE_DESCS.put(5001, "操作失败");
        CODE_DESCS.put(5002, "授权失败");
        CODE_DESCS.put(5003, "登录过期");
        CODE_DESCS.put(5004, "生成订单失败");
    }

    public static BizResponse ofCode(Integer code) {
        BizResponse bizResponse = new BizResponse();
        bizResponse.code = code;
        bizResponse.desc = CODE_DESCS.get(code);
        return bizResponse;
    }

    public static BizResponse ofData(Integer code, Object data) {
        BizResponse bizResponse = new BizResponse();
        bizResponse.code = code;
        bizResponse.desc = CODE_DESCS.get(code);
        bizResponse.data = data;
        return bizResponse;
    }

    public static BizResponse ofSuccess() {
        return ofCode(2000);
    }

    public static BizResponse ofSuccess(Object data) {
        return ofData(2000, data);
    }

    public static BizResponse ofFail() {
        return ofCode(5001);
    }

    public static BizResponse ofFail(Integer code) {
        return ofCode(code);
    }
}