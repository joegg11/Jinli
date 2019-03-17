package com.hziee.jinli.utils;

import com.hziee.jinli.enums.StringExceptionEnum;
import com.hziee.jinli.exception.JlException;
import com.hziee.jinli.pojo.Permission;
import com.hziee.jinli.vo.PermissionInfo;

/**
 *
 * PermissionString 工具类
 *
 */

public class StringUnit {

    public static PermissionInfo stringToPermissionInfo(String s){
        PermissionInfo permissionInfo = new PermissionInfo();
        if(s.charAt(0) == '1')
            permissionInfo.setClubInfoChange(true);
        else if(s.charAt(0) == '0')
            permissionInfo.setClubInfoChange(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        if(s.charAt(1) == '1')
            permissionInfo.setClubInfoSelect(true);
        else if(s.charAt(1) == '0')
            permissionInfo.setClubInfoSelect(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        if(s.charAt(2) == '1')
            permissionInfo.setAdminInfoChange(true);
        else if(s.charAt(2) == '0')
            permissionInfo.setAdminInfoChange(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        if(s.charAt(3) == '1')
            permissionInfo.setAdminInfoSelect(true);
        else if(s.charAt(3) == '0')
            permissionInfo.setAdminInfoSelect(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        if(s.charAt(4) == '1')
            permissionInfo.setVipInfoChange(true);
        else if(s.charAt(4) == '0')
            permissionInfo.setVipInfoChange(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        if(s.charAt(5) == '1')
            permissionInfo.setVipInfoSelect(true);
        else if(s.charAt(5) == '0')
            permissionInfo.setVipInfoSelect(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        if(s.charAt(6) == '1')
            permissionInfo.setGoodsInfoChange(true);
        else if(s.charAt(6) == '0')
            permissionInfo.setGoodsInfoChange(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        if(s.charAt(7) == '1')
            permissionInfo.setGoodsInfoSelect(true);
        else if(s.charAt(7) == '0')
            permissionInfo.setGoodsInfoSelect(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        if(s.charAt(8) == '1')
            permissionInfo.setPointsInfoChange(true);
        else if(s.charAt(8) == '0')
            permissionInfo.setPointsInfoChange(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        if(s.charAt(9) == '1')
            permissionInfo.setPointsInfoSelect(true);
        else if(s.charAt(9) == '0')
            permissionInfo.setPointsInfoSelect(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        if(s.charAt(10) == '1')
            permissionInfo.setDiscountInfoChange(true);
        else if(s.charAt(10) == '0')
            permissionInfo.setDiscountInfoChange(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        if(s.charAt(12) == '1')
            permissionInfo.setDiscountInfoSelect(true);
        else if(s.charAt(12) == '0')
            permissionInfo.setDiscountInfoSelect(false);
        else
            throw new JlException(StringExceptionEnum.PERMISSION_STRING_ERROR);

        return permissionInfo;
    }

    public static String permissionInfoToString(PermissionInfo permissionInfo){
        String s = "";
        if(permissionInfo.getClubInfoChange())
            s = s + "1";
        else
            s = s + "0";

        if(permissionInfo.getClubInfoSelect())
            s = s + "1";
        else
            s = s + "0";

        if(permissionInfo.getAdminInfoChange())
            s = s + "1";
        else
            s = s + "0";

        if(permissionInfo.getAdminInfoSelect())
            s = s + "1";
        else
            s = s + "0";


        if(permissionInfo.getVipInfoChange())
            s = s + "1";
        else
            s = s + "0";

        if(permissionInfo.getVipInfoSelect())
            s = s + "1";
        else
            s = s + "0";


        if(permissionInfo.getGoodsInfoChange())
            s = s + "1";
        else
            s = s + "0";

        if(permissionInfo.getGoodsInfoSelect())
            s = s + "1";
        else
            s = s + "0";

        if(permissionInfo.getPointsInfoChange())
            s = s + "1";
        else
            s = s + "0";

        if(permissionInfo.getPointsInfoSelect())
            s = s + "1";
        else
            s = s + "0";

        if(permissionInfo.getDiscountInfoChange())
            s = s + "1";
        else
            s = s + "0";

        if(permissionInfo.getDiscountInfoSelect())
            s = s + "1";
        else
            s = s + "0";

        s = s + "0000";

        return s;
    }
}
