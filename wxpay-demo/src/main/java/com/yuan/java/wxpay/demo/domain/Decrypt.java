package com.yuan.java.wxpay.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Decrypt {

    private String code;
    private String encryptedData;
    private String iv;

}
