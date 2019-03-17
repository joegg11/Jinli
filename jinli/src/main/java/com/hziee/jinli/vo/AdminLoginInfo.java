package com.hziee.jinli.vo;

import com.hziee.jinli.pojo.Admin;
import lombok.Data;

@Data
public class AdminLoginInfo {
    private int adminId;
    private String clubName;
    private int clubId;
    private String clubType;
    private String name;
    private int role;
    private String roleName;
    private Long telephone;
    /**
     *     0.超级管理员
     *     1.店长
     *     2.高级员工
     *     3.员工
     */

    public AdminLoginInfo(int adminId,String clubName,int clubId,String clubType,String name,int role,String roleName){
        this.adminId = adminId;
        this.clubName = clubName;
        this.clubId = clubId;
        this.clubType = clubType;
        this.name = name;
        this.role = role;
        this.roleName = roleName;
    }

    public AdminLoginInfo(Admin admin,String clubName,String clubType,String roleName){
        this.adminId = admin.getAdminId();
        this.clubName = clubName;
        this.clubType = clubType;
        this.clubId = admin.getClubId();
        this.name = admin.getName();
        this.role = admin.getRole();
        this.roleName = roleName;
    }
}
