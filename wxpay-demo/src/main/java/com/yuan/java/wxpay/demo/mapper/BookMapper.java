package com.yuan.java.wxpay.demo.mapper;

import com.yuan.java.wxpay.demo.domain.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 书籍管理 数据层
 *
 * @author yuan
 */
@Mapper
public interface BookMapper {

    /**
     * 查询
     * @return
     */
    List<Book> selectBook();

}
