package com.hziee.jinli.controller;

import com.hziee.jinli.pojo.Rank;
import com.hziee.jinli.pojo.VIP;
import com.hziee.jinli.service.RankService;
import com.hziee.jinli.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("rank")
public class RankController {

    @Autowired
    private RankService rankService;

    //查看会员方案列表
    @GetMapping("rankList")
    public ResponseEntity<List<Rank>> selectRankList(@RequestParam("id") int id){
        return ResponseEntity.ok(rankService.selectRankList(id));
    }
}
