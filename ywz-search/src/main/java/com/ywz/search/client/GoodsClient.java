package com.ywz.search.client;

import com.ywz.item.api.GoodApi;
import com.ywz.item.pojo.SpuDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("item-service")
public interface GoodsClient extends GoodApi {

}
