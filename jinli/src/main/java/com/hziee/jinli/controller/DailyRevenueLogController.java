package com.hziee.jinli.controller;

import com.hziee.jinli.pojo.DailyRevenueLogs;
import com.hziee.jinli.service.DailyRevenueLogService;
import com.hziee.jinli.vo.LineChartData;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("dailyRevenueLog")
public class DailyRevenueLogController {

    @Autowired
    private DailyRevenueLogService dailyRevenueLogService;

    //查询最近一周营业额
    @GetMapping("WeekList")
    public ResponseEntity<List<DailyRevenueLogs>> selectWeekDailyRevenueLogsList(@RequestParam("id") int id){
        return ResponseEntity.ok(dailyRevenueLogService.selectWeekDailyRevenueLogsList(id));
    }

    //查询本店有效营业数据
    @GetMapping("List")
    public ResponseEntity<List<DailyRevenueLogs>> selectDailyRevenueLogsList(@RequestParam("id") int id){
        return ResponseEntity.ok(dailyRevenueLogService.selectDailyRevenueLogsList(id));
    }

    //包装后的近一周营业数据
    @GetMapping("WeekListPackaged")
    public ResponseEntity<LineChartData> selectWeekDailyListPackage(@RequestParam("id") int id){
        return ResponseEntity.ok(dailyRevenueLogService.selectWeekDailyListPackage(id));
    }
}
