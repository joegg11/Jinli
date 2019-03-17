package com.hziee.jinli.vo;

import com.hziee.jinli.enums.*;
import com.hziee.jinli.pojo.VIP;
import lombok.Data;

@Data
public class ExceptionResult {

    private int status;
    private String message;
    private Long timestamp;

    /*public ExceptionResult(VIPExceptionEnum em){
        this.status = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }*/

    public ExceptionResult(AdminExceptionEnum em1){
        this.status = em1.getCode();
        this.message = em1.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(DailyRevenueExceptionEnum em2){
        this.status = em2.getCode();
        this.message = em2.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(DiscountExceptionEnum em3){
        this.status = em3.getCode();
        this.message = em3.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(GoodsExceptionEnum em4){
        this.status = em4.getCode();
        this.message = em4.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(PermissionExceptionEnum em5){
        this.status = em5.getCode();
        this.message = em5.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(UploadExceptionEnum em6){
        this.status = em6.getCode();
        this.message = em6.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(VIPExceptionEnum em7){
        this.status = em7.getCode();
        this.message = em7.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(PointsExceptionEnum em8){
        this.status = em8.getCode();
        this.message = em8.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(StringExceptionEnum em9){
        this.status = em9.getCode();
        this.message = em9.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(ClubExceptionEnum em10){
        this.status = em10.getCode();
        this.message = em10.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(RankExceptionEnum em11){
        this.status = em11.getCode();
        this.message = em11.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(RoleExceptionEnum em12){
        this.status = em12.getCode();
        this.message = em12.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
