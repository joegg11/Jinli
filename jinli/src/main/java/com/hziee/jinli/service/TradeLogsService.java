package com.hziee.jinli.service;

import com.hziee.jinli.enums.*;
import com.hziee.jinli.exception.JlException;
import com.hziee.jinli.mapper.*;
import com.hziee.jinli.pojo.*;
import com.hziee.jinli.vo.PermissionBusinessObject;
import com.hziee.jinli.vo.PieChartData;
import com.hziee.jinli.vo.TradeLogsInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.SpanShapeRenderer;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class TradeLogsService {

    @Autowired
    private TradeLogsMapper tradeLogsMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private VIPMapper vipMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private DiscountMapper discountMapper;
    @Autowired
    private RankMapper rankMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private PointsMapper pointsMapper;
    @Autowired
    private DailyRevenueLogsMapper dailyRevenueLogsMapper;

    //交易信息记录
    public TradeLogsInfo goodsTradeLogs(int adminId,int vipId,int goodsId,int goodsNum,double actualPay){
        if(adminId == -1) throw new JlException(AdminExceptionEnum.ADMIN_NOT_FOUND);
        if(vipId == -1) throw new JlException(VIPExceptionEnum.VIP_NOT_FOUND);
        if(goodsId == -1) throw new JlException(GoodsExceptionEnum.GOODS_NOT_FOUND);
        if(goodsNum == 0) throw new JlException(GoodsExceptionEnum.GOODS_NUM_ZERO_ERROR);
        if(actualPay == -1) throw new JlException(GoodsExceptionEnum.ACTUAL_PAY_ZERO_ERROR);
        Admin theAdmin = adminMapper.selectByPrimaryKey(adminId);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole);
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getPointsInfoChange()) {
            VIP theVip = vipMapper.selectByPrimaryKey(vipId);
            Rank theRank = rankMapper.selectByPrimaryKey(theVip.getRankId());
            System.out.println(theRank.getDiscountId());
            Discount theDiscount = discountMapper.selectByPrimaryKey(theRank.getDiscountId());
            Goods theGoods = goodsMapper.selectByPrimaryKey(goodsId);
            Points thePoint = pointsMapper.selectByPrimaryKey(theGoods.getPointsId());
            theVip.setPoints(theVip.getPoints() + goodsNum * thePoint.getPointValue());
            TradeLogs newTradeLogs = new TradeLogs();
            newTradeLogs.setAdminId(adminId);
            newTradeLogs.setVipId(vipId);
            newTradeLogs.setGoodsId(goodsId);
            newTradeLogs.setGoodsNum(goodsNum);
            newTradeLogs.setShouldPay(theGoods.getPrice() * goodsNum * theDiscount.getDiscount());
            newTradeLogs.setPoints(goodsNum * thePoint.getPointValue());
            newTradeLogs.setActualPay(actualPay);
            newTradeLogs.setClubId(theAdmin.getClubId());
            Date nowTime = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String time = sdf.format(nowTime);
            newTradeLogs.setDate(time);
            int count = tradeLogsMapper.insert(newTradeLogs);
            if (count != 1){
                throw new JlException(GoodsExceptionEnum.GOODS_TRADE_ERROR);
            }else{
                count = vipMapper.updateByPrimaryKey(theVip);
                if (count != 1) throw new JlException(VIPExceptionEnum.VIP_UPDATE_ERROR);
                else {
                    String day = sdf2.format(nowTime);
                    DailyRevenueLogs theDailyRevenueLogs = dailyRevenueLogsMapper.selectByPrimaryKey(day);
                    if (theDailyRevenueLogs == null) {
                        DailyRevenueLogs theDailyRevenueLogs2 = new DailyRevenueLogs();
                        theDailyRevenueLogs2.setDate(day);
                        theDailyRevenueLogs2.setClubId(theAdmin.getClubId());
                        theDailyRevenueLogs2.setSum(theGoods.getPrice() * goodsNum * theDiscount.getDiscount());
                        count = dailyRevenueLogsMapper.insert(theDailyRevenueLogs2);
                        if (count != 1) {
                            throw new JlException(DailyRevenueExceptionEnum.INSERT_DAILY_INFORMATION_ERROR);
                        }
                    } else {
                        theDailyRevenueLogs.setSum(theDailyRevenueLogs.getSum() + theGoods.getPrice() * goodsNum * theDiscount.getDiscount());
                        count = dailyRevenueLogsMapper.updateByPrimaryKey(theDailyRevenueLogs);
                        if (count != 1) {
                            throw new JlException(DailyRevenueExceptionEnum.UPDATE_DAILY_INFORMATION_ERROR);
                        }
                    }
                    return new TradeLogsInfo(theAdmin, theVip, theGoods, newTradeLogs);
                }
            }
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //查看店铺交易记录列表
    public List<TradeLogsInfo> checkGoodsTradeLogsList(int adminId){
        Admin theAdmin = adminMapper.selectByPrimaryKey(adminId);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole);
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getGoodsInfoSelect() && thePermissionBusinessObject.getPermissioninfo().getVipInfoSelect()){
            TradeLogs tradeLogs = new TradeLogs();
            tradeLogs.setClubId(theAdmin.getClubId());
            List<TradeLogs> tradeLogsList = tradeLogsMapper.select(tradeLogs);
            if(tradeLogsList.isEmpty()){
                throw new JlException(AdminExceptionEnum.TRADE_LOGS_SELECT_ERROR);
            }else{
                List<TradeLogsInfo> tradeLogsInfoList = new ArrayList<TradeLogsInfo>();
                Admin adminTools = new Admin();
                VIP vipTools = new VIP();
                Goods goodsTools = new Goods();
                for(int i = 0;i < tradeLogsList.size();i++){
                    adminTools = adminMapper.selectByPrimaryKey(tradeLogsList.get(i).getAdminId());
                    vipTools = vipMapper.selectByPrimaryKey(tradeLogsList.get(i).getVipId());
                    goodsTools = goodsMapper.selectByPrimaryKey(tradeLogsList.get(i).getGoodsId());
                    tradeLogsInfoList.add(new TradeLogsInfo(adminTools,vipTools,goodsTools,tradeLogsList.get(i)));
                }
                return tradeLogsInfoList;
            }
        }else{
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
        }
    }

    //封装今天的物品营业结果
    public List<PieChartData> selectGoodsDailyListPackage(int id){
        Admin theAdmin = adminMapper.selectByPrimaryKey(id);
        Role theRole = roleMapper.selectByPrimaryKey(theAdmin.getRole());
        Permission thePermission = permissionMapper.selectByPrimaryKey(theRole.getPermissionId());
        PermissionBusinessObject thePermissionBusinessObject = new PermissionBusinessObject(thePermission);
        if(thePermissionBusinessObject.getPermissioninfo().getClubInfoSelect()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String nowStr = sdf.format(now);
            Example example = new Example(TradeLogs.class);
            List<TradeLogs> tradeLogsList = tradeLogsMapper.selectByExample(example);
            Map<String,Integer> m = new HashMap<>();
            Goods theGoods;
            List<PieChartData> pieChartDataList = new ArrayList<>();
            for(int i = 0;i < tradeLogsList.size();i++){
                if(nowStr.equals(tradeLogsList.get(i).getDate().substring(0,10))){
                    theGoods = goodsMapper.selectByPrimaryKey(tradeLogsList.get(i).getGoodsId());
                    if(m.containsKey(theGoods.getName())){
                        pieChartDataList.get(m.get(theGoods.getName())).setData( pieChartDataList.get(m.get(theGoods.getName())).getData() + tradeLogsList.get(i).getGoodsNum());
                    }else{
                        m.put(theGoods.getName(),pieChartDataList.size());
                        pieChartDataList.add(new PieChartData(theGoods.getName(),tradeLogsList.get(i).getGoodsNum()));
                    }
                }
            }
            return pieChartDataList;
        }
        else
            throw new JlException(PermissionExceptionEnum.ADMIN_NO_SUCH_PERMISSION);
    }
}
