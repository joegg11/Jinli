package com.hziee.jinli.service;

import com.hziee.jinli.enums.PermissionExceptionEnum;
import com.hziee.jinli.exception.JlException;
import com.hziee.jinli.mapper.AdminMapper;
import com.hziee.jinli.mapper.DailyRevenueLogsMapper;
import com.hziee.jinli.mapper.PermissionMapper;
import com.hziee.jinli.mapper.RoleMapper;
import com.hziee.jinli.pojo.Admin;
import com.hziee.jinli.pojo.DailyRevenueLogs;
import com.hziee.jinli.pojo.Permission;
import com.hziee.jinli.pojo.Role;
import com.hziee.jinli.vo.LineChartData;
import com.hziee.jinli.vo.PermissionBusinessObject;
import com.hziee.jinli.vo.PieChartData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class DailyRevenueLogService {
    @Autowired
    private DailyRevenueLogsMapper dailyRevenueLogsMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;


    //返回近七天营业信息列表
    public List<DailyRevenueLogs> selectWeekDailyRevenueLogsList(int id){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getClubInfoSelect()) {
            List<DailyRevenueLogs> dailyRevenueLogsList = new ArrayList<DailyRevenueLogs>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            Calendar nowCalendar = Calendar.getInstance();
            nowCalendar.setTime(now);
            nowCalendar.add(Calendar.DATE,-7);
            String str;
            for(int i = 0;i < 7;i++){
                nowCalendar.add(Calendar.DATE,1);
                str = sdf.format(nowCalendar.getTime());
                DailyRevenueLogs dailyRevenueLogs = dailyRevenueLogsMapper.selectByPrimaryKey(str);
                if(dailyRevenueLogs == null){
                    dailyRevenueLogsList.add(new DailyRevenueLogs(str,0,null,theAdmin.getClubId()));
                }else{
                    dailyRevenueLogsList.add(dailyRevenueLogs);
                }
            }
            //System.out.println(sdf.format(nowCalendar.getTime()));
            return dailyRevenueLogsList;
        }
        else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //返回有记录的营业信息列表
    public List<DailyRevenueLogs> selectDailyRevenueLogsList(int id){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getClubInfoSelect()) {
            List<DailyRevenueLogs> dailyRevenueLogsList = new ArrayList<DailyRevenueLogs>();
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Example example = new Example(DailyRevenueLogs.class);
            dailyRevenueLogsList = dailyRevenueLogsMapper.selectByExample(example);
            return dailyRevenueLogsList;
        }
        else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //封装完近一周的营业信息
    public LineChartData selectWeekDailyListPackage(int id){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getClubInfoSelect()) {
            List<DailyRevenueLogs> dailyRevenueLogsList = selectWeekDailyRevenueLogsList(id);
            LineChartData newDailyInfoPackagedList = new LineChartData(dailyRevenueLogsList);
            return newDailyInfoPackagedList;
        }
        else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }


}
