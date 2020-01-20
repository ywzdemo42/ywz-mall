package com.ywz.item.mapper;

import com.ywz.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {
    @Insert("INSERT INTO tb_category_brand (category_id,brand_id) values (#{cid},#{bid}) ")
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);
}
