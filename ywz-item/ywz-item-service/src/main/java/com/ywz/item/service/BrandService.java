package com.ywz.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ywz.common.PageResult;
import com.ywz.item.mapper.BrandMapper;
import com.ywz.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPage(String key,Integer page, Integer rows,String sortBy, Boolean desc){
        //初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        //根据name模糊查询 或者根据首字母查询
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("name","%" + key + "%").orEqualTo("letter",key);
        }
        //添加分页条件
        PageHelper.startPage(page,rows);

        //添加排序条件
        if(StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy + " " + (desc?"desc":"asc"));
        }

        List<Brand> brands = this.brandMapper.selectByExample(example);

        //包装成pageinfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        //包装成分页结果集
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Transactional
    public void saveBrand(Brand brand,List<Long> cids){
        brandMapper.insertSelective(brand);

        cids.forEach(c -> {
            this.brandMapper.insertCategoryAndBrand(c,brand.getId());
        });
    }

    public List<Brand> queryBrandByCid(Long cid){
        return this.brandMapper.queryBrandByCid(cid);
    }

}
