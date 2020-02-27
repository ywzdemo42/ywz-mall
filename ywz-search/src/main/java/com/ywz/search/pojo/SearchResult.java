package com.ywz.search.pojo;

import com.ywz.common.PageResult;
import com.ywz.item.pojo.Brand;

import java.util.List;
import java.util.Map;

public class SearchResult extends PageResult<Goods> {
    private List<Map<String,Object>> categorys;
    private List<Brand> brands;
    private List<Map<String,Object>> specs;


    public SearchResult() {

    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Map<String, Object>> categorys, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categorys = categorys;
        this.brands = brands;
        this.specs = specs;
    }

    public List<Map<String, Object>> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Map<String, Object>> specs) {
        this.specs = specs;
    }

    public List<Map<String, Object>> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<Map<String, Object>> categorys) {
        this.categorys = categorys;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
