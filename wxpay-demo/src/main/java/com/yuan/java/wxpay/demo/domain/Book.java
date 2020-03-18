package com.yuan.java.wxpay.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 书籍
 *
 * @author yuan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Integer id;

    private String title;

    private String author;

    private Double rating;

    private BigDecimal price;

    private String cover;

    private Boolean isAdd;

    private Date createTime;

    private Date updateTime;

}
