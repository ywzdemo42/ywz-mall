package com.ywz.item.mapper;

import com.ywz.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CatetgoryMapper extends Mapper<Category>, SelectByIdListMapper<Category,Long> {

}
