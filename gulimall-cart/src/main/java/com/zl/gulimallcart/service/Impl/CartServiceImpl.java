package com.zl.gulimallcart.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zl.common.utils.R;
import com.zl.gulimallcart.feign.ProductFeignService;
import com.zl.gulimallcart.interceptor.CartInterCeptor;
import com.zl.gulimallcart.service.CartService;
import com.zl.gulimallcart.to.UserInfoTo;
import com.zl.gulimallcart.vo.CartItemVo;
import com.zl.gulimallcart.vo.CartVo;
import com.zl.gulimallcart.vo.SkuInfoVo;
import com.zl.gulimallcart.vo.SkuSaleAttrValueVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author ZhuLing
 * @date 2021/11/24 - 21:01
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ProductFeignService productFeignService;
    @Autowired
    ThreadPoolExecutor executor;

    private final String CART_PREFIX = "gulimall:cart:";
    @Override
    public CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        BoundHashOperations<String, Object, Object> hashOperations = getCartOps();
        String o = (String) hashOperations.get(skuId.toString());
        if (StringUtils.isEmpty(o)){
            CartItemVo vo = new CartItemVo();
            CompletableFuture<Void> run1 = CompletableFuture.runAsync(() -> {
                R info = productFeignService.info(skuId);
                SkuInfoVo skuInfo = info.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                });
                vo.setSkuId(skuId);
                vo.setCheck(true);
                vo.setImage(skuInfo.getSkuDefaultImg());
                vo.setTitle(skuInfo.getSkuTitle());
                vo.setCount(num);
                vo.setPrice(skuInfo.getPrice());
            }, executor);
            CompletableFuture<Void> run2 = CompletableFuture.runAsync(() -> {
                R r = productFeignService.skuIdInfo(skuId);
                List<SkuSaleAttrValueVo> skuSaleAttrValue = r.getData("skuSaleAttrValue", new TypeReference<List<SkuSaleAttrValueVo>>() {
                });
                List<String> collect = skuSaleAttrValue.stream().map(SkuSaleAttrValueVo::getAttrValue).collect(Collectors.toList());
                vo.setSkuAttrValues(collect);

            }, executor);
            CompletableFuture.allOf(run1,run2).get();
            String s = JSON.toJSONString(vo);
            hashOperations.put(skuId.toString(),s);
            return vo;
        }else {
            CartItemVo vo = JSON.parseObject(o, CartItemVo.class);
            vo.setCount(vo.getCount()+num);
            String s = JSON.toJSONString(vo);
            hashOperations.put(skuId.toString(),s);
            return vo;
        }
    }

    @Override
    public CartItemVo getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> hashOperations = getCartOps();
        String o = (String) hashOperations.get(skuId.toString());
        CartItemVo   vo = JSON.parseObject(o, CartItemVo.class);
        return vo;
    }

    @Override
    public CartVo getCart() throws ExecutionException, InterruptedException {
        UserInfoTo userInfoTo = CartInterCeptor.toThreadLocal.get();
        CartVo vo = new CartVo();
        if (userInfoTo.getUserId()==null){
            List<CartItemVo> cartVo = getCartVo(userInfoTo.getUserKey());
            vo.setItems(cartVo);
        }else{
            List<CartItemVo> cartVo = getCartVo(userInfoTo.getUserKey());
            //redisTemplate.opsForHash().delete();
            clearCart(CART_PREFIX+userInfoTo.getUserKey());
            if (cartVo!=null&&cartVo.size()>0){
                for (CartItemVo cartItem:cartVo) {
                    addToCart(cartItem.getSkuId(),cartItem.getCount());
                }
            }
            List<CartItemVo> cartVo1 = getCartVo(String.valueOf(userInfoTo.getUserId()));
            vo.setItems(cartVo1);
        }
        return vo;
    }

    private List<CartItemVo> getCartVo(String userKey) {
        String cartKey = CART_PREFIX+userKey;
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(cartKey);
        List<Object> values = hashOperations.values();
        List<CartItemVo> list = new ArrayList<CartItemVo>();
        if (values!=null&&values.size()>0){
            list = values.stream().map(value -> {
                String s = (String) value;
                CartItemVo cartItemVo = JSON.parseObject(s, CartItemVo.class);
                return cartItemVo;
            }).collect(Collectors.toList());
            return list;
        }
        return list;
    }


    private BoundHashOperations<String, Object, Object> getCartOps() {
        UserInfoTo userInfoTo = CartInterCeptor.toThreadLocal.get();
        String cartKey ="";
        if (userInfoTo.getUserId()!=null){
           cartKey=CART_PREFIX+userInfoTo.getUserId();

        }else{
            cartKey=CART_PREFIX+userInfoTo.getUserKey();
        }
        return redisTemplate.boundHashOps(cartKey);
    }
    @Override
    public void clearCart(String cartKey){
        redisTemplate.delete(cartKey);
    }

    @Override
    public void checkItem(Long skuId, Integer check) {
        CartItemVo cartItem = getCartItem(skuId);
        cartItem.setCheck(check==1?true:false);
        String s = JSON.toJSONString(cartItem);
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.put(skuId.toString(),s);

    }

    @Override
    public void countItem(Long skuId, Integer num) {
        CartItemVo cartItem = getCartItem(skuId);
        cartItem.setCount(num);
        String s = JSON.toJSONString(cartItem);
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.put(skuId.toString(),s);
    }

    @Override
    public void deleteItem(Long skuId) {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        cartOps.delete(skuId.toString());
    }

    @Override
    public List<CartItemVo> getUserCartItems() {
        UserInfoTo userInfoTo = CartInterCeptor.toThreadLocal.get();
        if (userInfoTo.getUserId()==null){
            return null;
        }else{
            List<CartItemVo> cartVo = getCartVo(userInfoTo.getUserId().toString());
            List<CartItemVo> collect = cartVo.stream().filter(item -> item.getCheck()).map(item->{
                item.setPrice(productFeignService.getPrice(item.getSkuId()));
                return item;
            }).collect(Collectors.toList());
            return collect;
        }


    }
}
