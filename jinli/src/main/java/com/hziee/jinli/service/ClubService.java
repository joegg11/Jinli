package com.hziee.jinli.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.hziee.jinli.enums.AdminExceptionEnum;
import com.hziee.jinli.enums.ClubExceptionEnum;
import com.hziee.jinli.enums.PermissionExceptionEnum;
import com.hziee.jinli.exception.JlException;
import com.hziee.jinli.mapper.AdminMapper;
import com.hziee.jinli.mapper.ClubMapper;
import com.hziee.jinli.mapper.PermissionMapper;
import com.hziee.jinli.mapper.RoleMapper;
import com.hziee.jinli.pojo.Admin;
import com.hziee.jinli.pojo.Club;
import com.hziee.jinli.pojo.Permission;
import com.hziee.jinli.pojo.Role;
import com.hziee.jinli.utils.EncryptUtil;
import com.hziee.jinli.vo.PageResult;
import com.hziee.jinli.vo.PermissionBusinessObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ClubService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ClubMapper clubMapper;

    //店铺注册
    public void clubRegister(String ClubName, String ClubType, String name, String pwd,long telephone){
        Club newClub = new Club();
        newClub.setClubName(ClubName);
        newClub.setClubType(ClubType);
        Date nowTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String registerTime  = sdf.format(nowTime);
        newClub.setClubRegisterTime(registerTime);
        int count = clubMapper.insert(newClub);
        if(count != 1){
            throw new JlException(ClubExceptionEnum.CLUB_INFORMATION_CREATE_ERROR);
        }

        Admin newAdmin = new Admin();
        newAdmin.setName(name);
        newAdmin.setPwd(EncryptUtil.String_Encrypt(pwd));
        newAdmin.setRole(1);
        newAdmin.setClubId(clubMapper.selectOne(newClub).getClubId());
        newAdmin.setTelephone(telephone);
        count = adminMapper.insert(newAdmin);

        if(count != 1){
            throw new JlException(AdminExceptionEnum.ADMIN_CREATE_ERROR);
        }
    }

    //根据id查询店铺
    public Club selectClubById(int id){

        Club cid = clubMapper.selectByPrimaryKey(id);
        if (cid.getClubId()!=0){
            return cid;
        }else {
            throw new JlException(ClubExceptionEnum.CLUB_INFORMATION_CHECK_ERROR);
        }
    }

    //根据name查询店铺
    public Club selectClubByName(String name){
        Club clubTool = new Club();
        clubTool.setClubName(name);
        Club club = clubMapper.selectOne(clubTool);

        if(club != null){
            return club;
        }else {
            throw new JlException(ClubExceptionEnum.CLUB_INFORMATION_CHECK_ERROR);
        }
    }

    //更新店铺信息
    public void updateClub(int id, String clubName, String clubType){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getClubInfoChange()){
            Club theClub = clubMapper.selectByPrimaryKey(theAdmin.getClubId());
            theClub.setClubName(clubName);
            theClub.setClubType(clubType);
            int count = clubMapper.updateByPrimaryKey(theClub);
            if(count != 1)
                throw new JlException(ClubExceptionEnum.CLUB_INFORMATION_UPDATE_ERROR);
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //分页呈现店铺信息
    public PageResult<Club> queryClubByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key){

        //分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(Admin.class);
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
        List<Club> list = clubMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)){
            throw new JlException(ClubExceptionEnum.CLUB_INFORMATION_CHECK_ERROR);
        }

        //解析分页结果
        PageInfo<Club> info = new PageInfo<Club>(list);
        return new PageResult<Club>(info.getTotal(), list);
    }
}
