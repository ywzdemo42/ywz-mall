package com.java.goods.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GoodsController {

    /**
     *
     * @param id spuid
     * @param model 数据模型
     * @return
     */
    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id")Long id, Model model){

        return "item";
    }
}
