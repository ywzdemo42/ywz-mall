package com.ywz.goods.controller;

import com.ywz.goods.service.GoodsHtmlService;
import com.ywz.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsHtmlService goodsHtmlService;
    /**
     *
     * @param id spuid
     * @param model 数据模型
     * @return
     */
    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id")Long id, Model model){
        Map<String, Object> map = this.goodsService.loadData(id);
        model.addAllAttributes(map);

        this.goodsHtmlService.createHtml(id);
        return "item";
    }
}
