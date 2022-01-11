package com.zl.gulimallcart.service;

import com.zl.gulimallcart.vo.CartItemVo;
import com.zl.gulimallcart.vo.CartVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author ZhuLing
 * @date 2021/11/24 - 21:01
 */
public interface CartService {
    CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    CartItemVo getCartItem(Long skuId);

    CartVo getCart() throws ExecutionException, InterruptedException;
    void clearCart(String cartKey);

    void checkItem(Long skuId, Integer check);

    void countItem(Long skuId, Integer num);

    void deleteItem(Long skuId);

    List<CartItemVo> getUserCartItems();
}
