package com.ywz.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ywz.common.PageResult;
import com.ywz.item.bo.SpuBo;
import com.ywz.item.mapper.*;
import com.ywz.item.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    public PageResult<SpuBo> querySpuByPage(String key,Boolean saleable,Integer page,Integer rows){
        //添加查询条件
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(key)){
            criteria.andLike("title","%" + key + "%");
        }

        if (saleable != null){
            criteria.andEqualTo("saleable",saleable);
        }
        //分页
        PageHelper.startPage(page,rows);
        //查询
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);
        //把spu转化为spubo
        List<SpuBo> spubos = spus.stream().map( spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu,spuBo);
            //查询品牌名称
            Brand brand = this.brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());
            //查询分类名称
            List<String> strings = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(strings,"-"));
            return spuBo;
        }).collect(Collectors.toList());
        //返回一个PageResults
        return new PageResult<>(spuPageInfo.getTotal(), spubos);
    }

    /**
     * 新增商品
     * @param spuBo
     * @return
     */
    @Transactional
    public void saveGoods(SpuBo spuBo){
        //新增sou
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insertSelective(spuBo);
        //新增spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuDetail);
        //新增sku
        saveSkuAndStock(spuBo);
        //消息队列
        sendMsg("insert", spuBo.getId());
    }

    /**
     * RabbitMQ生产者方法
     * @param type
     * @param spuId
     */
    private void sendMsg(String type,Long spuId) {
        this.amqpTemplate.convertAndSend("item." + type, spuId);
    }


    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(sku -> {
            sku.setId(null);
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(spuBo.getCreateTime());
            sku.setLastUpdateTime(spuBo.getLastUpdateTime());
            this.skuMapper.insertSelective(sku);

            //新增stock
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }


    /**
     * 更新商品信息
     * @param spuBo
     */
    @Transactional
    public void updateGoods(SpuBo spuBo){
         Sku record = new Sku();
         record.setSpuId(spuBo.getId());
         List<Sku> skus = this.skuMapper.select(record);
         skus.forEach(sku -> {
             this.stockMapper.deleteByPrimaryKey(sku.getId());
         });

        Sku sku = new Sku();
        sku.setSpuId(spuBo.getId());
        this.skuMapper.delete(sku);

        this.saveSkuAndStock(spuBo);

        spuBo.setCreateTime(null);
        spuBo.setLastUpdateTime(new Date());
        spuBo.setValid(null);
        spuBo.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spuBo);
        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
    }

    public SpuDetail querySpuDetailBySpuId(Long supId){
        return this.spuDetailMapper.selectByPrimaryKey(supId);
    }

    public List<Sku> querySkusBySpuId(Long id){
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skus = this.skuMapper.select(sku);
        skus.forEach(sku1 -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(sku1.getId());
            sku1.setStock(stock.getStock());
        });

        return skus;
    }

    public Spu querySpuById(Long id) {
        return  this.spuMapper.selectByPrimaryKey(id);
    }
}
