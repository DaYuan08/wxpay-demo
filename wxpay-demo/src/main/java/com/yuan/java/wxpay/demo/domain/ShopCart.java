package com.yuan.java.wxpay.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 购物车
 *
 * @author yuan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCart {

    private Integer id;

    private Integer userId;

    private Book book;

    private Integer orderId;

    private Date createTime;

    private Date updateTime;

}
