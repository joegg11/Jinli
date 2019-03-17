package com.hziee.jinli.controller;

import com.hziee.jinli.pojo.TradeLogs;
import com.hziee.jinli.pojo.VIP;
import com.hziee.jinli.service.TradeLogsService;
import com.hziee.jinli.vo.PieChartData;
import com.hziee.jinli.vo.TradeLogsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("tradeLogs")
public class TradeLogsController {
    @Autowired
    private TradeLogsService tradeLogsService;

    //添加商品交易记录
    @PostMapping("trade")
    public ResponseEntity<TradeLogsInfo> addTradeLogsInfo(
            @RequestParam(value = "adminId",defaultValue = "-1") int adminId,
            @RequestParam(value = "vipId",defaultValue = "-1") int vipId,
            @RequestParam(value = "goodsId",defaultValue = "-1") int goodsId,
            @RequestParam(value = "goodsNum", defaultValue = "0") int goodsNum,
            @RequestParam(value = "actualPay",defaultValue = "-1") double actualPay){
        return ResponseEntity.ok(tradeLogsService.goodsTradeLogs(adminId, vipId, goodsId, goodsNum, actualPay));
    }

    //店长查看商品交易日志信息
    @GetMapping("tradeLogsList")
    public ResponseEntity<List<TradeLogsInfo>> checkTradeLogsInfoList(@RequestParam("adminId") int id){
        return ResponseEntity.ok(tradeLogsService.checkGoodsTradeLogsList(id));
    }

    //封装完成的每日营业商品
    @GetMapping("dailyTradeLogsList")
    public ResponseEntity<List<PieChartData>> selectGoodsDailyListPackage(@RequestParam("id") int id){
        return ResponseEntity.ok(tradeLogsService.selectGoodsDailyListPackage(id));
    }
    //
}
