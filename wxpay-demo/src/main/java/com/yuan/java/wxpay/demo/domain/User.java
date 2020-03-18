package com.yuan.java.wxpay.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户信息
 *
 * @author yuan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;

    private String unionId;

    private String openId;

    private String name;

    private String gender;

    private String avatar;

    private Date createTime;

    private Date updateTime;

}
