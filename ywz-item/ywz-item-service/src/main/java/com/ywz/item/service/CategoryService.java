package com.ywz.item.service;

import com.ywz.item.mapper.CatetgoryMapper;
import com.ywz.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CatetgoryMapper catetgoryMapper;

    /**
     * 根据父节点查询子节点
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid){
        Category record = new Category();
        record.setParentId(pid);
        return this.catetgoryMapper.select(record);
    }

    public Category updateBrand(Long bid){
        Category category= catetgoryMapper.selectByPrimaryKey(bid);
        return category;
    }

    public List<String> queryNamesByIds(List<Long> ids){
        List<Category> categories = this.catetgoryMapper.selectByIdList(ids);
        return categories.stream().map( category -> category.getName() ).collect(Collectors.toList());
    }
}
