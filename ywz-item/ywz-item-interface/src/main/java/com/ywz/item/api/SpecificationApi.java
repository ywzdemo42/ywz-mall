package com.ywz.item.api;

import com.ywz.item.pojo.SpecGroup;
import com.ywz.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("spec")
public interface SpecificationApi {

    @PostMapping("group")
    public Void saveGroup(@RequestBody SpecGroup specGroup);

    @GetMapping("groups/{cid}")
    public List<SpecGroup> selectGroupsById(@PathVariable("cid") Long cid);

    @GetMapping("params")
    public List<SpecParam> queryParams(
            @RequestParam(value = "gid", required = false)Long gid,
            @RequestParam(value = "cid", required = false)Long cid,
            @RequestParam(value = "generic", required = false)Boolean generic,
            @RequestParam(value = "searching", required = false)Boolean searching
    );
}
