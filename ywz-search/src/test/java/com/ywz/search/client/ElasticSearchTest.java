package com.ywz.search.client;

import com.ywz.common.PageResult;
import com.ywz.item.bo.SpuBo;
import com.ywz.search.pojo.Goods;
import com.ywz.search.repository.GoodsRepository;
import com.ywz.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticSearchTest {
    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SearchService searchService;
    @Autowired
    private GoodsClient goodsClient;

    @Test
    public void test(){
        this.template.createIndex(Goods.class);
        this.template.putMapping(Goods.class);
        Integer page = 1;
        Integer rows = 100;

        do{
            PageResult<SpuBo> result = this.goodsClient.querySpuByPage(null, null, page, rows);
            //处理List《SpuBo》 == 》 List<Goods>
            List<SpuBo> items = result.getItems();
            List<Goods> goodsList = items.stream().map(spuBo -> {
                try {
                    return  this.searchService.buildGoods(spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());

            //执行新增数据方法
            this.goodsRepository.saveAll(goodsList);
            rows = items.size();
            page ++;
        }while (rows < 100);




    }

}
