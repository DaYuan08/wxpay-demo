package com.yuan.java.wxpay.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author yuan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Integer id;

    private Integer userId;

    private Integer state;

    private BigDecimal prices;

    private Date createTime;

    private Date updateTime;
}
