package com.hziee.jinli.exception;

import com.hziee.jinli.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JlException extends RuntimeException{

    private DailyRevenueExceptionEnum dailyRevenueExceptionEnum;
    private DiscountExceptionEnum discountExceptionEnum;
    private GoodsExceptionEnum goodsExceptionEnum;
    private PermissionExceptionEnum permissionExceptionEnum;
    private VIPExceptionEnum vipexceptionEnum;
    private UploadExceptionEnum uploadExceptionEnum;
    private AdminExceptionEnum adminExceptionEnum;
    private PointsExceptionEnum pointsExceptionEnum;
    private StringExceptionEnum stringExceptionEnum;
    private ClubExceptionEnum clubExceptionEnum;
    private RankExceptionEnum rankExceptionEnum;
    private RoleExceptionEnum roleExceptionEnum;

    public JlException(DailyRevenueExceptionEnum dailyRevenueExceptionEnum){
        this.dailyRevenueExceptionEnum = dailyRevenueExceptionEnum;
    }

    public JlException(DiscountExceptionEnum discountExceptionEnum){
        this.discountExceptionEnum = discountExceptionEnum;
    }

    public JlException(GoodsExceptionEnum goodsExceptionEnum){
        this.goodsExceptionEnum = goodsExceptionEnum;
    }

    public JlException(PermissionExceptionEnum permissionExceptionEnum){
        this.permissionExceptionEnum = permissionExceptionEnum;
    }

    public JlException(VIPExceptionEnum vipexceptionEnum){
        this.vipexceptionEnum = vipexceptionEnum;
    }

    public JlException(AdminExceptionEnum adminExceptionEnum){
        this.adminExceptionEnum = adminExceptionEnum;
    }

    public JlException(UploadExceptionEnum uploadExceptionEnum){
        this.uploadExceptionEnum = uploadExceptionEnum;
    }

    public JlException(PointsExceptionEnum pointsExceptionEnum){
        this.pointsExceptionEnum = pointsExceptionEnum;
    }

    public JlException(StringExceptionEnum stringExceptionEnum){
        this.stringExceptionEnum = stringExceptionEnum;
    }

    public JlException(ClubExceptionEnum clubExceptionEnum){
        this.clubExceptionEnum = clubExceptionEnum;
    }
    public  JlException(RankExceptionEnum rankExceptionEnum) {this.rankExceptionEnum = rankExceptionEnum;}
    public  JlException(RoleExceptionEnum roleExceptionEnum) {this.roleExceptionEnum = roleExceptionEnum;}
}
