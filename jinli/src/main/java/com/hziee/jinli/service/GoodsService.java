package com.hziee.jinli.service;

import com.hziee.jinli.enums.GoodsExceptionEnum;
import com.hziee.jinli.enums.PermissionExceptionEnum;
import com.hziee.jinli.exception.JlException;
import com.hziee.jinli.mapper.*;
import com.hziee.jinli.pojo.*;
import com.hziee.jinli.vo.PermissionBusinessObject;
import com.hziee.jinli.vo.PermissionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private VIPMapper vipMapper;
    @Autowired
    private RankMapper rankMapper;

    //查看物品清单
    public List<Goods> selectGoodsList(int club_id){
        Goods goods = new Goods();
        goods.setClubId(club_id);
        List<Goods> GoodsList = goodsMapper.select(goods);
        if (GoodsList.isEmpty()){
            throw new JlException(GoodsExceptionEnum.GOODS_SELECT_ERROR);
        }else{
            return  GoodsList;
        }
    }

    //员工查看物品列表
    public  List<Goods> adminSelectGoodList(int id){
        Admin adminTools = new Admin();
        adminTools.setAdminId(id);
        Admin theAdmin = adminMapper.selectOne(adminTools);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getClubId());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getGoodsInfoSelect()){
            return selectGoodsList(theAdmin.getClubId());
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //Vip查看物品列表
    public  List<Goods> vipSelectGoodList(int id){
        VIP theVip = vipMapper.selectByPrimaryKey(id);
        Rank theRank = rankMapper.selectByPrimaryKey(theVip.getRankId());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRank.getPermissionId());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getGoodsInfoSelect()){
            return selectGoodsList(theVip.getClubId());
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //店长添加商品信息
    public void addGoods(int id,Goods goods){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getGoodsInfoChange()){
            goods.setClubId(theAdmin.getClubId());
            int count = goodsMapper.insert(goods);
            if (count != 1){
                throw new JlException(GoodsExceptionEnum.GOODS_CREATE_ERROR);
            }
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //店长修改商品信息
    public void updateGoods(int id,int goodsId,Goods newGoods){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getGoodsInfoChange()){
            newGoods.setGoodsId(goodsId);
            newGoods.setClubId(theAdmin.getClubId());
            int count = goodsMapper.updateByPrimaryKey(newGoods);
            if (count != 1){
                throw new JlException(GoodsExceptionEnum.GOODS_UPDATE_ERROR);
            }
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //店长删除商品信息
    public void deleteGoods(int id,int goodsId){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getGoodsInfoChange()){
            int count = goodsMapper.deleteByPrimaryKey(goodsId);
            if (count != 1){
                throw new JlException(GoodsExceptionEnum.GOODS_DELETE_ERROR);
            }
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }
}
