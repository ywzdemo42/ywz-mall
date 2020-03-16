package com.ywz.cart.service;

import com.ywz.cart.client.GoodsClient;
import com.ywz.cart.interceptor.LoginInterceptor;
import com.ywz.cart.pojo.Cart;
import com.ywz.common.pojo.UserInfo;
import com.ywz.common.utils.JsonUtils;
import com.ywz.item.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.KeyBoundCursor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private GoodsClient goodsClient;
    private static final String KEY_PREFIX = "user:cart:";

    //添加购物车
    public void addCart(Cart cart) {
        //获取用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //查询购物车记录
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        String key = cart.getSkuId().toString();
        Integer num = cart.getNum();
        //判断当前商品是否在购物车中
        if(hashOps.hasKey(key)){
            //在 更新数量
            String cartJson = hashOps.get(key).toString();
            cart = JsonUtils.parse(cartJson, Cart.class);
            cart.setNum(cart.getNum() + num);

        }else{
            //不在 新增商品
            Sku sku = this.goodsClient.querySkuBySkuId(cart.getSkuId());
            cart.setUserId(userInfo.getId());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
        }
        hashOps.put(key,JsonUtils.serialize(cart));
    }

    public List<Cart> queryCart() {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //判断用户是否有购物车
        if(this.redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())){
            return null;
        }

        //获取用户购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        List<Object> cartsJson = hashOperations.values();

        if(CollectionUtils.isEmpty(cartsJson)){
            return null;
        }

        return cartsJson.stream().map(obj -> {
            return JsonUtils.parse(obj.toString(),Cart.class);
        }).collect(Collectors.toList());



    }

    public void updateCarts(Cart cart) {


    }
}
