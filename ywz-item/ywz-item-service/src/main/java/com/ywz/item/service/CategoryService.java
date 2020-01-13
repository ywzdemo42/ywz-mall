package com.ywz.item.service;

import com.ywz.item.mapper.CatetgoryMapper;
import com.ywz.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
