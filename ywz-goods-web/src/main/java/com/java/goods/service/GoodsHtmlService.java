package com.java.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * 生成静态模板
 */
@Service
public class GoodsHtmlService {
    @Autowired
    private TemplateEngine engine;
    @Autowired
    private GoodsService goodsService;

    public void createHtml(Long spuId){

        //初始化运行上下文
        Context context = new Context();
        //设置数据模型
        context.setVariables(this.goodsService.loadData(spuId));
        //设置流


        PrintWriter printWriter = null;
        try {
            File file = new File("D:\\MyProgramFiles\\nginx\\html\\item\\" + spuId + ".html");
            printWriter = new PrintWriter(file);
            this.engine.process("item",context, printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(printWriter != null){
                printWriter.close();
            }
        }

    }

}
