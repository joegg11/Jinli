package com.hziee.jinli.service;

import com.hziee.jinli.enums.RankExceptionEnum;
import com.hziee.jinli.exception.JlException;
import com.hziee.jinli.mapper.AdminMapper;
import com.hziee.jinli.mapper.PermissionMapper;
import com.hziee.jinli.mapper.RankMapper;
import com.hziee.jinli.mapper.RoleMapper;
import com.hziee.jinli.pojo.Admin;
import com.hziee.jinli.pojo.Permission;
import com.hziee.jinli.pojo.Rank;
import com.hziee.jinli.pojo.Role;
import com.hziee.jinli.vo.PermissionBusinessObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
@Slf4j
public class RankService {

    @Autowired
    private RankMapper rankMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    //返回会员方案列表
    public List<Rank> selectRankList(int id){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getVipInfoSelect() && thePermissionBusinessObject.getPermissioninfo().getDiscountInfoSelect()){
            Example example = new Example(Rank.class);
            List<Rank> rankList = rankMapper.selectByExample(example);
            return rankList;
        }else{
            throw new JlException(RankExceptionEnum.RANK_SELECT_ERROR);
        }
    }
}
