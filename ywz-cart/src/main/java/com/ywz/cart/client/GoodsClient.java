package com.ywz.cart.client;

import com.ywz.item.api.GoodApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface GoodsClient extends GoodApi {
}
