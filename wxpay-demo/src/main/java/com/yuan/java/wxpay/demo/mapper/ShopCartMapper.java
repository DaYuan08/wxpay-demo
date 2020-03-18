package com.yuan.java.wxpay.demo.mapper;

import com.yuan.java.wxpay.demo.domain.ShopCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 购物车管理 数据层
 *
 * @author yuan
 */
@Mapper
public interface ShopCartMapper {

    /**
     * 新增
     * @param shopcart
     * @return
     */
    Integer addShopCart(ShopCart shopcart);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer deleteShopCart(Integer id);

    /**
     * 根据书籍删除
     * @param bookId
     * @param userId
     * @return
     */
    Integer deleteShopCartByBook(Integer bookId, Integer userId);

    /**
     * 修改购物车
     * @param orderId
     * @param shopCartIds
     * @return
     */
    Integer updateShopCart(Integer orderId, Integer[] shopCartIds);

    /**
     * 查询
     * @param userId
     * @return
     */
    List<ShopCart> selectShopCarts(Integer userId);

    /**
     * 查询单条购物车
     * @param userId
     * @param bookId
     * @return
     */
    Integer selectSingle(Integer userId, Integer bookId);

}
