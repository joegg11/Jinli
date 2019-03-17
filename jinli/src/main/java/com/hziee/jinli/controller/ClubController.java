package com.hziee.jinli.controller;

import com.hziee.jinli.pojo.Admin;
import com.hziee.jinli.pojo.Club;
import com.hziee.jinli.service.ClubService;
import com.hziee.jinli.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    //商家注册
    @PostMapping("register")
    public ResponseEntity<Void> merchantRegistration(
            @RequestParam("clubName") String ClubName,
            @RequestParam("clubType") String ClubType,
            @RequestParam("name")      String name,
            @RequestParam("pwd")      String pwd,
            @RequestParam("telephone")      long telephone
        ){
        clubService.clubRegister(ClubName, ClubType, name, pwd, telephone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //根据Id查找会所
    @GetMapping("id")
    public ResponseEntity<Club> selectClubById(@RequestParam("id") int id){
        return ResponseEntity.ok(clubService.selectClubById(id));
    }

    //根据Id查找会所
    @GetMapping("name")
    public ResponseEntity<Club> selectClubByName(@RequestParam("name") String name){
        return ResponseEntity.ok(clubService.selectClubByName(name));
    }

    //修改会所信息
    @PutMapping("update")
    public ResponseEntity<Void> updateClubInfo(@RequestParam("id") int id,@RequestParam("clubName") String clubName,@RequestParam("clubType") String clubType){//验权后才可修改会所信息....
        clubService.updateClub(id, clubName, clubType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //用户数据分页列表呈现
    @GetMapping("page")
    public ResponseEntity<PageResult<Club>> queryClubByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows, //每页显示条数
            @RequestParam(value = "sortBy", required = false) String sortBy,  //排序方式
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,  //是否降序 布尔类型 不传的话默认为升序
            @RequestParam(value = "key", required = false) String key
    ){
        return ResponseEntity.ok(clubService.queryClubByPage(page, rows, sortBy, desc, key));
    }
}
