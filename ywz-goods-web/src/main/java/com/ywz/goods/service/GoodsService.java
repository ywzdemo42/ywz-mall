package com.ywz.goods.service;

import com.ywz.goods.client.BrandClient;
import com.ywz.goods.client.CategoryClient;
import com.ywz.goods.client.GoodsClient;
import com.ywz.goods.client.SpecificationClient;
import com.ywz.item.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsService {
    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    public Map<String,Object> loadData(Long spuId){
        Map<String,Object> model = new HashMap<>();
        //根据spuid查询spu
        Spu spu = this.goodsClient.querySpuById(spuId);
        model.put("spu",spu);
        //查询spudetail
        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spuId);
        model.put("spuDetail",spuDetail);
        //查询分类
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> names = this.categoryClient.queryNamesByIds(cids);
        //初始化一个分类的map
        List<Map<String,Object>> categories = new ArrayList<>();
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",cids.get(i));
            map.put("name",names.get(i));
            categories.add(map);
        }
        model.put("categories",categories);
        //查询品牌
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());
        model.put("brand",brand);
        //skus
        List<Sku> skus = this.goodsClient.querySkusBySpuId(spuId);
        model.put("skus",skus);
        List<SpecGroup> groups = this.specificationClient.queryGroupWithParam(spu.getCid3());
        model.put("groups",groups);
        List<SpecParam> params = this.specificationClient.queryParams(null, spu.getCid3(), false, null);

        Map<Long,String> paramMap = new HashMap<>();
        params.forEach(param -> {
            paramMap.put(param.getId(),param.getName());
        });

        model.put("paramMap",paramMap);

        return model;
    }
}
