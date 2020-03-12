package com.ywz.goods.client;

import com.ywz.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {

}
