package com.hziee.jinli.controller;

import com.hziee.jinli.pojo.Admin;
import com.hziee.jinli.service.AdminService;
import com.hziee.jinli.vo.AdminLoginInfo;
import com.hziee.jinli.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //店长添加员工

    @PostMapping("addAdmin")
    public ResponseEntity<Void> addAdmin(@RequestParam("id") int id,Admin admin){
        adminService.addAdmin(id,admin);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //店长删除员工
    @DeleteMapping("deleteAdmin")
    public ResponseEntity<Void> deleteAdmin(@RequestParam("id") int id,@RequestParam("delete_id") int deleteAdminId){
        adminService.deleteAdmin(id, deleteAdminId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //店长修改员工信息
    @PutMapping("updateAdmin")
    public ResponseEntity<Void> updateAdmin(@RequestParam("id") int id,@RequestParam("update_id") int updateAdminId,Admin admin){
        adminService.updateAdmin(id,updateAdminId,admin);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //根据id查询员工
    @GetMapping("id")
    public ResponseEntity<Admin> selectUserById(@RequestParam("pid") int pid,@RequestParam("id") int id){

        return ResponseEntity.ok(adminService.selectAdminById(pid, id));
    }

    //员工登录
    @PostMapping("login")
    public ResponseEntity<AdminLoginInfo> adminLoginById(@RequestParam(value = "name",defaultValue = "") String name,@RequestParam(value = "pwd",defaultValue = "")  String pwd){
        return ResponseEntity.ok(adminService.adminLogin(name,pwd));
    }

    //员工密码重置
    @PutMapping("resetPassword")
    public ResponseEntity<String> resetPasswordAdmin(@RequestParam("id") int id,@RequestParam("reset") int reset_id){
        return ResponseEntity.ok(adminService.resetPasswordAdmin(id, reset_id));
    }

    //根据username查询员工
    @GetMapping("username")
    public ResponseEntity<Admin> selectAdminByName(@RequestParam("id") int id,@RequestParam("name") String name){
        return ResponseEntity.ok(adminService.selectAdminByName(id, name));
    }

    //根据会所ID查看会所内员工信息
    @GetMapping("club_id")
    public  ResponseEntity<List<Admin>> selectAdminByClubId(@RequestParam("id") int id,@RequestParam(value = "club_id") int club_id){
        return ResponseEntity.ok(adminService.selectAdminByClubId(id,club_id));
    }

    //根据会所名查看会所内员工信息
    @GetMapping("club_name")
    public  ResponseEntity<List<Admin>> selectAdminByClubName(@RequestParam("id") int id,@RequestParam("club_name") String club_name){
        return ResponseEntity.ok(adminService.selectAdminByClubName(id,club_name));
    }

    //根据会所名查看会所内员工信息
    @GetMapping("role")
    public  ResponseEntity<List<Admin>> selectAdminByRole(@RequestParam("id") int id,@RequestParam("role_id") int role_id){
        return ResponseEntity.ok(adminService.selectAdminListByRole(id, role_id));
    }

    //遍历所有员工信息列表
    @GetMapping("admin_list")
    public ResponseEntity<PageResult<Admin>> queryAllAdminList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows, //每页显示条数
            @RequestParam(value = "sortBy", required = false) String sortBy,  //排序方式
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,  //是否降序 布尔类型 不传的话默认为升序
            @RequestParam("id") int id){
        return ResponseEntity.ok(adminService.selectAllAdminListByPage( page, rows, sortBy, desc, id));
    }

    //用户数据分页列表呈现
    @GetMapping("page")
    public ResponseEntity<PageResult<Admin>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows, //每页显示条数
            @RequestParam(value = "sortBy", required = false) String sortBy,  //排序方式
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,  //是否降序 布尔类型 不传的话默认为升序
            @RequestParam(value = "key", required = false) String key
    ){
        return ResponseEntity.ok(adminService.selectBrandByPage(page, rows, sortBy, desc, key));
    }
}
