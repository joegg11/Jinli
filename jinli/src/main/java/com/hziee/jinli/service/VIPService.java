package com.hziee.jinli.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hziee.jinli.enums.AdminExceptionEnum;
import com.hziee.jinli.enums.PermissionExceptionEnum;
import com.hziee.jinli.enums.VIPExceptionEnum;
import com.hziee.jinli.exception.JlException;
import com.hziee.jinli.mapper.*;
import com.hziee.jinli.pojo.*;
import com.hziee.jinli.utils.DateUtil;
import com.hziee.jinli.utils.EncryptUtil;
import com.hziee.jinli.vo.PageResult;
import com.hziee.jinli.vo.PermissionBusinessObject;
import com.hziee.jinli.vo.VipLoginInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.hziee.jinli.utils.DateUtil.caculate_int_add_date;
import static com.hziee.jinli.utils.EncryptUtil.String_Encrypt;

@Service
public class VIPService {

    @Autowired
    private VIPMapper vipMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ClubMapper clubMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RankMapper rankMapper;
    //private DateUtil datetools;

    //新增会员
    public void addVip(int id, VIP vip){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role roleTool = new Role();
        roleTool.setRoleId(theAdmin.getRole());
        Role theRole = roleMapper.selectOne(roleTool);
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if(permissionBo.getPermissioninfo().getVipInfoChange()){
            vip.setVipId(null);
            vip.setClubId(theAdmin.getClubId());
            vip.setPwd(String_Encrypt(vip.getPwd())); //不确定会员是否也有登陆系统
            vip.setPoints(0);
            vip.setDaysEffective(0);
            VIP vipTool = new VIP();
            vipTool.setName(vip.getName());
            VIP theVip = vipMapper.selectOne(vipTool);
            if(theVip != null) throw new JlException(VIPExceptionEnum.VIP_CREATE_NAME_REPETITIVE_ERROR);
            int count = vipMapper.insert(vip);
            if (count !=1){
                throw new JlException(VIPExceptionEnum.VIP_CREATE_ERROR);
            }
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
//        vip.setVipId(null);
//        int count = vipMapper.insert(vip);
//        if (count !=1){
//            throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
//        }
    }

    //会员删除
    public void deleteVIP(int id, int deletedVIPId){
        Admin adminTool = new Admin();
        adminTool.setAdminId(id);
        Admin theAdmin = adminMapper.selectOne(adminTool);
        //Admin deletedVip = vipMapper.selectByPrimaryKey(deletedVIPId);
        VIP theVip = vipMapper.selectByPrimaryKey(deletedVIPId);
        Role roleTool = new Role();
        roleTool.setRoleId(theAdmin.getClubId());
        Role theRole = roleMapper.selectOne(roleTool);
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if(permissionBo.getPermissioninfo().getVipInfoChange() && (theVip.getClubId().equals(theAdmin.getClubId()))){
            int count = vipMapper.deleteByPrimaryKey(deletedVIPId);
            if (count != 1){
                throw new JlException(VIPExceptionEnum.VIP_DELETE_ERROR);
            }
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //会员信息修改
    public void updateVIP(int id, int updateVipId, VIP vip){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        //Admin deletedVip = vipMapper.selectByPrimaryKey(deletedVIPId);
        VIP theVip = vipMapper.selectByPrimaryKey(updateVipId);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if(permissionBo.getPermissioninfo().getVipInfoChange() && theVip.getClubId().equals(theAdmin.getClubId())){
            vip.setVipId(updateVipId);
            vip.setPwd(String_Encrypt(vip.getPwd()));
            vip.setDaysEffective(theVip.getDaysEffective());
            vip.setRankId(theVip.getRankId());
            vip.setFlashDay(theVip.getFlashDay());
            int count = vipMapper.updateByPrimaryKeySelective(vip);
            if (count != 1){
                throw new JlException(VIPExceptionEnum.VIP_UPDATE_ERROR);
            }
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //根据id查询会员
    public VIP selectVipById(int pid, int id) {
//        VIP t = new VIP();
//        t.setVipId(id);
//        VIP mid = vipMapper.selectOne(t);
//
//        return mid;
        Admin theAdmin = adminMapper.selectByPrimaryKey(pid);
        VIP theVIP = vipMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if (permissionBo.getPermissioninfo().getVipInfoSelect()) {
            VIP t = new VIP();
            t.setVipId(id);
            VIP mid = vipMapper.selectOne(t);
            if(mid.getVipId()!=0 && mid.getClubId() == theVIP.getClubId()) {
                mid.setPwd(null);
                return mid;
            }else
                throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }



    //根据rank查询会员 分页
    public PageResult<VIP> selectAllVipListByPage(int id, Integer page, Integer rows, int rank){
        /*PageHelper.startPage(page, rows);
        VIP t = new VIP();
        t.setRankId(rank);
        List<VIP> list = vipMapper.select(t);
        if(CollectionUtils.isEmpty(list)){
            throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
        }
        PageInfo<VIP> info = new PageInfo<VIP>(list);
        return new PageResult<VIP>(info.getTotal(), list);*/
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if(permissionBo.getPermissioninfo().getVipInfoSelect()){
            VIP t = new VIP();
            t.setRankId(rank);
            t.setClubId(theAdmin.getClubId());  //对于list对象的club映射
            List<VIP> list = vipMapper.select(t);
            if(CollectionUtils.isEmpty(list)){
                throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
            }
            for(int i = 0;i < list.size();i++) list.get(i).setPwd(null);
            PageInfo<VIP> info = new PageInfo<VIP>(list);
            return new PageResult<VIP>(info.getTotal(), list);
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //根据name查询会员
    public VIP selectVipByName(int pid, String name){
        Admin theAdmin = adminMapper.selectByPrimaryKey(pid);
        Role roleTool = new Role();
        roleTool.setRoleId(theAdmin.getRole());
        Role theRole = roleMapper.selectOne(roleTool);
        Permission permissionTool = new Permission();
        permissionTool.setPermissionId(theRole.getPermissionId());
        Permission thePermission = permissionMapper.selectOne(permissionTool);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if (permissionBo.getPermissioninfo().getVipInfoSelect()){

            VIP newVip = new VIP();
            newVip.setName(name);
            VIP vip = vipMapper.selectOne(newVip);

            if (vip!=null && vip.getClubId() == theAdmin.getClubId()){
                vip.setPwd(null);
                return vip;
            }else {
                throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
            }
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }

    }

    //根据rank查询会员
    public List<VIP> selectVipListByRank(int id, int rank){

        /*VIP t = new VIP();
        t.setRankId(rank);
        List<VIP> list = vipMapper.select(t);
        if(CollectionUtils.isEmpty(list)){
            throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
        }
        return list;*/
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if(permissionBo.getPermissioninfo().getVipInfoSelect()){
            VIP t = new VIP();
            t.setRankId(rank);
            t.setClubId(theAdmin.getClubId());
            List<VIP> list = vipMapper.select(t);
            if(CollectionUtils.isEmpty(list)){
                throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
            }
            for(int i = 0;i < list.size();i++) list.get(i).setPwd(null);
            return list;
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //根据会员续费 （改信息）
    public void renewVip(int pid, int renewVipId, int day,int rank) throws ParseException {
        /*VIP m = new VIP();
        m.setVipId(id);
        VIP vip = vipMapper.selectOne(m);
        if(vip == null){
            throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
        }
        vip.setRankId(rank);
        //Date nowday = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date day1 = new Date();
        try {
             day1 = sdf.parse(vip.getFlashDay());
        }catch (ParseException e){
            e.printStackTrace();
        }
        if(day1.compareTo(new Date()) <= 0){
           vip.setFlashDay(sdf.format(caculate_int_add_date(new Date(),day)));
            //vip.setFlash_day(caculate_int_add_date(new Date(),day).toString());
            vip.setDaysEffective(day);
        }else if(day1.compareTo(new Date()) > 0){
            vip.setFlashDay(sdf.format(caculate_int_add_date(day1,day)));
            vip.setDaysEffective(vip.getDaysEffective()+day);
        }
        int count = vipMapper.updateByPrimaryKey(vip);
        if (count !=1){
            throw new JlException(VIPExceptionEnum.VIP_CREATE_ERROR);
        }*/
        Admin theAdmin = adminMapper.selectByPrimaryKey(pid);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole);
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if(permissionBo.getPermissioninfo().getVipInfoChange()){
            VIP vip = vipMapper.selectByPrimaryKey(renewVipId);
            if(vip == null || !theAdmin.getClubId().equals(vip.getClubId())){
                throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
            }
            vip.setRankId(rank);
            //Date nowday = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date day1 = new Date();
            //System.out.println(!vip.getFlashDay().isEmpty());
            if(vip.getDaysEffective() != 0){
                day1 = sdf.parse(vip.getFlashDay());
            }

            if(day1.compareTo(new Date()) <= 0){
               vip.setFlashDay(sdf.format(caculate_int_add_date(new Date(),day)));
                //vip.setFlash_day(caculate_int_add_date(new Date(),day).toString());
                vip.setDaysEffective(day);
            }else if(day1.compareTo(new Date()) > 0){
                vip.setFlashDay(sdf.format(caculate_int_add_date(day1,day)));
                vip.setDaysEffective(vip.getDaysEffective()+day);
            }
            int count = vipMapper.updateByPrimaryKey(vip);
            if (count !=1){
                throw new JlException(VIPExceptionEnum.VIP_UPDATE_ERROR);
            }
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }

    }

    //会员密码重置
    public String resetPasswordVip(int id,int resetVipId){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        VIP resetVip = vipMapper.selectByPrimaryKey(resetVipId);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(permissionMapper.selectByPrimaryKey(theRole.getPermissionId()));
        if(thePermissionBusinessObject.getPermissioninfo().getAdminInfoSelect() && theAdmin.getClubId()==resetVip.getClubId()){
            int newPasswordInt = (int)(Math.random()*1000000);
            String newPasswordString = EncryptUtil.String_Encrypt(Integer.toString(newPasswordInt)).substring(0,6);
            System.out.println(newPasswordString);
            resetVip.setPwd(EncryptUtil.String_Encrypt(newPasswordString));
            int count = vipMapper.updateByPrimaryKey(resetVip);
            if(count != 1) throw new JlException(VIPExceptionEnum.VIP_UPDATE_ERROR);
            return newPasswordString;
        }
        else throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
    }
    //会员登录
    public VipLoginInfo vipLoginInfo(String name,String pwd){
        if(name.equals("")) throw new JlException(VIPExceptionEnum.VIP_USERNAME_NULL_ERROR);
        if(pwd.equals("")) throw new JlException(VIPExceptionEnum.VIP_PASSWORD_NULL_ERROR);
        VIP vipTool = new VIP();
        vipTool.setName(name);
        VIP theVip = vipMapper.selectOne(vipTool);
        if(theVip != null) {
            System.out.println(theVip.getPwd());
            System.out.println(EncryptUtil.String_Encrypt(pwd));
            if (theVip.getPwd().equals(EncryptUtil.String_Encrypt(pwd))) {
                Club theClub = clubMapper.selectByPrimaryKey(theVip.getClubId());
                Rank theRank = rankMapper.selectByPrimaryKey(theVip.getRankId());
                return new VipLoginInfo(theVip, theClub, theRank);
            }
            throw new JlException(VIPExceptionEnum.VIP_PASSWORD_WRONG);
        }else{
            throw new JlException(VIPExceptionEnum.VIP_LOGIN_ERROR);
        }
    }

    //分页呈现会员数据
    public PageResult<VipLoginInfo> selectBrandByPage(int id, Integer page, Integer rows, String sortBy, Boolean desc, String key){
        //分页
        /*PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(VIP.class);
        if (StringUtils.isNoneBlank(key)){
            //只要满足其一，就符合用户搜索的条件，就会呈现数据
            example.createCriteria().orLike("name", "%"+key+"%");
        }
        //排序
        if (StringUtils.isNoneBlank(sortBy)){
            String orderByClause = sortBy + (desc ? "DESC" : "ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<VIP> list = vipMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)){
            throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
        }

        //解析分页结果
        PageInfo<VIP> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);*/
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject permissionBo = new PermissionBusinessObject(thePermission);
        if(permissionBo.getPermissioninfo().getVipInfoSelect()){
            //分页
            PageHelper.startPage(page, rows);
            //过滤
            Example example = new Example(VIP.class);
            if (StringUtils.isNoneBlank(key)){
                //只要满足其一，就符合用户搜索的条件，就会呈现数据
                example.createCriteria().orLike("name", "%"+key+"%");
            }
            //排序
            if (StringUtils.isNoneBlank(sortBy)){
                String orderByClause = sortBy + (desc ? "DESC" : "ASC");
                example.setOrderByClause(orderByClause);
            }
            //查询
            Club theClub = clubMapper.selectByPrimaryKey(theAdmin.getClubId());
            //System.out.println(clubMapper.selectByPrimaryKey(theAdmin.getClubId()).toString());
            //example.selectProperties("clubId");
            List<VIP> list = vipMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(list)){
                throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
            }
            for(int i = 0;i < list.size();i++) list.get(i).setPwd(null);
            //解析分页结果
            Rank theRank;
            List<VipLoginInfo> vipList = new ArrayList<>();
            for(int i = 0;i < list.size();i++){
                //System.out.println(list.get(i));
                theRank = rankMapper.selectByPrimaryKey(list.get(i).getRankId());
                //System.out.println(theRank.getRankName());
                vipList.add(new VipLoginInfo(list.get(i),theClub,theRank));
            }
            PageInfo<VipLoginInfo> info = new PageInfo<>(vipList);

            return new PageResult<>(info.getTotal(), vipList);
        }else {
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }

    }

}
