package com.ywz.item.api;

import com.ywz.common.PageResult;
import com.ywz.item.bo.SpuBo;
import com.ywz.item.pojo.Sku;
import com.ywz.item.pojo.Spu;
import com.ywz.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

public interface GoodApi {
    /**
     * 根据spiI的查询spuDetail
     * @param supId
     * @return
     */
    @GetMapping("spu/detail/{supId}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("supId")Long supId);

    /**
     * 分页结果集
     * @param key
     * @param salebale
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("spu/page")
    public PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "saleable",required = false) Boolean salebale,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows
    );

    /**
     * 根据spuI的查询sku几核
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    public List<Sku> querySkusBySpuId(@RequestParam("id")Long id);

    @GetMapping("{id}")
    public Spu querySpuById(@PathVariable("id")Long id);
}
