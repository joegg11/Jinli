package com.hziee.jinli.controller;

import com.hziee.jinli.pojo.Admin;
import com.hziee.jinli.pojo.VIP;
import com.hziee.jinli.service.VIPService;
import com.hziee.jinli.vo.PageResult;
import com.hziee.jinli.vo.VipLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("vip")
public class VIPController {

    @Autowired
    private VIPService vipService;

    //高级员工新增会员
    @PostMapping("add")
    public ResponseEntity<Void> addVip(@RequestParam("id")int id,VIP vip){
        vipService.addVip(id,vip);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //会员登录
    @PostMapping("login")
    public ResponseEntity<VipLoginInfo> vipLogin(@RequestParam(value = "name",defaultValue = "") String name,@RequestParam(value = "pwd",defaultValue = "") String pwd){
        return ResponseEntity.ok(vipService.vipLoginInfo(name, pwd));
    }

    //员工密码重置
    @PutMapping("resetPassword")
    public ResponseEntity<String> resetPasswordVip(@RequestParam("id") int id,@RequestParam("reset") int reset_id){
        return ResponseEntity.ok(vipService.resetPasswordVip(id, reset_id));
    }

    //高级员工删除会员信息
    @DeleteMapping("deleteVip")
    public ResponseEntity<Void> deleteVip(@RequestParam("id") int id,@RequestParam("delete_id") int deleteVipId){
        vipService.deleteVIP(id,deleteVipId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //高级员工修改会员信息
    @PutMapping("updateVip")
    public ResponseEntity<Void> updateVip(@RequestParam("id") int id,@RequestParam("update_id") int updateVipId,VIP vip){
        vipService.updateVIP(id,updateVipId,vip);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //根据id查询会员
    @GetMapping("id")
    public ResponseEntity<VIP> selectVipById(@RequestParam("pid")int pid,@RequestParam("id")int id){
        return ResponseEntity.ok(vipService.selectVipById(pid,id));
    }

    //根据会员name查询会员
    @GetMapping("name")
    public ResponseEntity<VIP> selectVipByName(@RequestParam("pid")int pid, @RequestParam("name")String name){
        return ResponseEntity.ok(vipService.selectVipByName(pid, name));
    }

    //根据rank查询会员
    @GetMapping("rank")
    public ResponseEntity<List<VIP>> selectVipListByRank(@RequestParam("id")int id,@RequestParam("rank") int rank){
        return ResponseEntity.ok(vipService.selectVipListByRank(id,rank));
    }

    //会员续费
    @PutMapping("renew")
    public ResponseEntity<Void> renewVip(@RequestParam("pid") int pid ,@RequestParam("id")int id,@RequestParam("day") int day,@RequestParam("rank") int rank) throws ParseException {
        vipService.renewVip(pid,id,day,rank);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("rank_page")
    public ResponseEntity<PageResult<VIP>> selectBrandByRankPage(
            @RequestParam("id")int id,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows, //每页显示条数
            @RequestParam("rank") int rank)
    {
        return ResponseEntity.ok(vipService.selectAllVipListByPage(id, page, rows, rank));
    }

    //会员数据分页列表呈现
    @GetMapping("page")
    public ResponseEntity<PageResult<VipLoginInfo>> selectBrandByPage(
            @RequestParam("id") int id,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows, //每页显示条数
            @RequestParam(value = "sortBy", required = false) String sortBy,  //排序方式
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,  //是否降序 布尔类型 不传的话默认为升序
            @RequestParam(value = "key", required = false) String key
    ){
        return ResponseEntity.ok(vipService.selectBrandByPage( id, page, rows, sortBy, desc, key));
    }
}
