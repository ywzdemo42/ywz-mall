package com.ywz.item.service;

import com.ywz.item.mapper.SpecGroupMapper;
import com.ywz.item.mapper.SpecParamMapper;
import com.ywz.item.pojo.SpecGroup;
import com.ywz.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificatiionService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> selectGroupById(Long cid){
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> select = this.specGroupMapper.select(specGroup);
        return select;
    }

    public List<SpecParam> selectParamById(Long gid,Long cid,Boolean generic,Boolean searching){
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setGeneric(generic);
        specParam.setSearching(searching);
        List<SpecParam> select = specParamMapper.select(specParam);
        return select;
    }


    public void saveGroup(SpecGroup specGroup){
        specGroupMapper.insertSelective(specGroup);
    }
}
