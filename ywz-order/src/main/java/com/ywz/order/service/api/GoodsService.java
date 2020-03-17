package com.ywz.order.service.api;

import com.ywz.item.api.GoodApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "leyou-gateway", path = "/api/item")
public interface GoodsService extends GoodApi {
}
