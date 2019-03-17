package com.hziee.jinli.vo;

import com.hziee.jinli.pojo.Club;
import com.hziee.jinli.pojo.Rank;
import com.hziee.jinli.pojo.VIP;
import lombok.Data;

@Data
public class VipLoginInfo {
    private Integer VipId;
    private String name;
    private Integer points;
    private String rankName;
    private Integer rankId;
    private Integer clubId;
    private String clubName;
    private Integer daysEffective;
    private String flashDay;
    private Long telephone;

    public VipLoginInfo(int VipId,String name,int points,String rankName,int rankId,int clubId,String clubName,int daysEffective,String flashDay,long telephone){
        this.VipId = VipId;
        this.name = name;
        this.points = points;
        this.rankId = rankId;
        this.rankName = rankName;
        this.clubId = clubId;
        this.clubName = clubName;
        this.daysEffective = daysEffective;
        this.flashDay = flashDay;
        this.telephone = telephone;
    }

    public VipLoginInfo(VIP vip, Club club, Rank rank){
        this.VipId = vip.getVipId();
        this.name = vip.getName();
        this.points = vip.getPoints();
        this.rankId = vip.getRankId();
        this.rankName = rank.getRankName();
        this.clubId = vip.getClubId();
        this.clubName = club.getClubName();
        this.daysEffective = vip.getDaysEffective();
        this.flashDay = vip.getFlashDay();
        this.telephone = vip.getTelephone();
    }
}