package com.ywz.item.mapper;

import com.ywz.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    @Insert("INSERT INTO tb_category_brand (category_id,brand_id) values (#{cid},#{bid})")
    public void insertCategoryAndBrand(@Param("cid") Long cid,@Param("bid") Long id);

    @Select("SELECT * FROM tb_brand A INNER JOIN tb_category_brand B ON A.id = B.brand_id WHERE B.category_id = #{cid}")
    public List<Brand> queryBrandByCid(@Param("cid") Long cid);

}
