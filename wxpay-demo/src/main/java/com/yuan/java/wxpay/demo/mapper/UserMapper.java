package com.yuan.java.wxpay.demo.mapper;

import com.yuan.java.wxpay.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户管理 数据层
 *
 * @author yuan
 */
@Mapper
public interface UserMapper {

    /**
     * 新增
     * @param user
     * @return
     */
    Integer addUser(User user);

    /**
     * 修改
     * @param user
     * @return
     */
    Integer updateUser(User user);

    /**
     * 查询
     * @param id
     * @return
     */
    User selectUser(Integer id);

    /**
     * 判断用户是否存在
     * @param unionId
     * @return
     */
    Integer hasUser(String unionId);

}
