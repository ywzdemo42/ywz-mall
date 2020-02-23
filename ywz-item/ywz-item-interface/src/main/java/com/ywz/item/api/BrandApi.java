package com.ywz.item.api;


import com.ywz.common.PageResult;
import com.ywz.item.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("brand")
public interface BrandApi {

    @GetMapping("page")
    public PageResult<Brand> queryBrandByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy",required = false)String sortBy,
            @RequestParam(value = "desc",required = false)Boolean desc
    );

    @PostMapping
    public Void saveBrand(Brand brand, @RequestParam("cids") List<Long> cids);

    @GetMapping("cid/{cid}")
    public List<Brand> queryBreandByCid(@PathVariable("cid") Long cid);

    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable("id")Long id);

}
