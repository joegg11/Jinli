package com.hziee.jinli.utils;

import com.hziee.jinli.enums.*;
import com.hziee.jinli.exception.JlException;
import com.hziee.jinli.pojo.Discount;
import com.hziee.jinli.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(JlException.class)
    public ResponseEntity<ExceptionResult> handleException(JlException e1){
        if(e1.getVipexceptionEnum() != null) {
            VIPExceptionEnum e_vip1 = e1.getVipexceptionEnum();
            return  ResponseEntity.status(e_vip1.getCode())
                    .body(new ExceptionResult(e1.getVipexceptionEnum()));
        }else if(e1.getAdminExceptionEnum() != null){
            AdminExceptionEnum e_admin1 = e1.getAdminExceptionEnum();
            return ResponseEntity.status(e_admin1.getCode())
                    .body(new ExceptionResult(e1.getAdminExceptionEnum()));
        }else if(e1.getGoodsExceptionEnum() != null){
            GoodsExceptionEnum e_good1 = e1.getGoodsExceptionEnum();
            return ResponseEntity.status(e_good1.getCode()).body(new ExceptionResult(e1.getGoodsExceptionEnum()));
        }else if(e1.getDiscountExceptionEnum() != null){
            DiscountExceptionEnum e_discount1 = e1.getDiscountExceptionEnum();
            return ResponseEntity.status(e_discount1.getCode()).body(new ExceptionResult(e1.getDiscountExceptionEnum()));
        }else if(e1.getPermissionExceptionEnum() != null){
            PermissionExceptionEnum e_permission1 = e1.getPermissionExceptionEnum();
            return ResponseEntity.status(e_permission1.getCode()).body(new ExceptionResult(e1.getPermissionExceptionEnum()));
        }else if(e1.getDailyRevenueExceptionEnum() != null){
            DailyRevenueExceptionEnum e_daily_revenue1 = e1.getDailyRevenueExceptionEnum();
            return ResponseEntity.status(e_daily_revenue1.getCode()).body(new ExceptionResult(e1.getDailyRevenueExceptionEnum()));
        }else if (e1.getPointsExceptionEnum() != null){
            PointsExceptionEnum e_points1 = e1.getPointsExceptionEnum();
            return ResponseEntity.status(e_points1.getCode()).body(new ExceptionResult(e1.getPointsExceptionEnum()));
        }else if(e1.getStringExceptionEnum() != null){
            StringExceptionEnum e_string1 = e1.getStringExceptionEnum();
            return ResponseEntity.status(e_string1.getCode()).body(new ExceptionResult(e1.getStringExceptionEnum()));
        }else if(e1.getClubExceptionEnum() != null){
            ClubExceptionEnum e_club1 = e1.getClubExceptionEnum();
            return ResponseEntity.status(e_club1.getCode()).body(new ExceptionResult(e1.getClubExceptionEnum()));
        }else if(e1.getPointsExceptionEnum() != null){
            PointsExceptionEnum e_Points1 = e1.getPointsExceptionEnum();
            return ResponseEntity.status(e_Points1.getCode()).body(new ExceptionResult(e1.getPointsExceptionEnum()));
        }else if(e1.getRankExceptionEnum() != null){
            RankExceptionEnum e_rank1 = e1.getRankExceptionEnum();
            return ResponseEntity.status(e_rank1.getCode()).body(new ExceptionResult(e1.getRankExceptionEnum()));
        }else{
            RoleExceptionEnum e_role1 = e1.getRoleExceptionEnum();
            return ResponseEntity.status(e_role1.getCode()).body(new ExceptionResult(e1.getRoleExceptionEnum()));
        }
    }


    /*VIPExceptionEnum em0 = e.getVipexceptionEnum();
        return ResponseEntity.status(em0.getCode())
                .body(new ExceptionResult(e.getVipexceptionEnum()));*/


}
