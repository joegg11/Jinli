package com.hziee.jinli.controller;

import com.hziee.jinli.pojo.Goods;
import com.hziee.jinli.pojo.TradeLogs;
import com.hziee.jinli.service.ClubService;
import com.hziee.jinli.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    //商家添加商品信息
    @PostMapping("addGoods")
    public ResponseEntity<Void>  addGoods(@RequestParam("id") int id, Goods goods){
        goodsService.addGoods(id, goods);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //商家修改商品信息
    @PutMapping("updateGoods")
    public ResponseEntity<Void>  updateGoods(@RequestParam("id") int id,@RequestParam("goodsId") int goodsId,Goods newGoods){
        goodsService.updateGoods(id, goodsId, newGoods);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //商家删除商品信息
    @DeleteMapping("deleteGoods")
    public ResponseEntity<Void>  deleteGoods(@RequestParam("id") int id,@RequestParam("goodsId") int goodsId){
        goodsService.deleteGoods(id, goodsId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //商家查看商品列表
    @GetMapping("adminGoodsList")
    public ResponseEntity<List<Goods>> adminCheckGoodsList(@RequestParam("id") int id){
       return ResponseEntity.ok(goodsService.adminSelectGoodList(id));
    }

    //会员查看商品列表
    @GetMapping("vipGoodsList")
    public ResponseEntity<List<Goods>> vipCheckGoodsList(@RequestParam("id") int id){
        return ResponseEntity.ok(goodsService.vipSelectGoodList(id));
    }
}
