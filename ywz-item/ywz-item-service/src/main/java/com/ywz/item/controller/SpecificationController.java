package com.ywz.item.controller;

import com.github.pagehelper.ISelect;
import com.ywz.item.pojo.SpecGroup;
import com.ywz.item.pojo.SpecParam;
import com.ywz.item.service.SpecificatiionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificatiionService specificatiionService;

    @PostMapping("group")
    public ResponseEntity<Void> saveGroup(@RequestBody SpecGroup specGroup){
        specificatiionService.saveGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> selectGroupsById(@PathVariable("cid") Long cid){

        if(cid == 0 || cid == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<SpecGroup> list =  specificatiionService.selectGroupById(cid);
        if (CollectionUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> selectParamById(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "generic",required = false) Boolean generic,
            @RequestParam(value = "searching",required = false) Boolean searching
    ){
//        if(gid == 0 || gid == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
        List<SpecParam> specParams = specificatiionService.selectParamById(gid,cid,generic,searching);
        if (CollectionUtils.isEmpty(specParams)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(specParams);

    }

}
