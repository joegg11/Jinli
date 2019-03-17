package com.hziee.jinli.vo;

import com.hziee.jinli.pojo.Permission;
import lombok.Data;

import static com.hziee.jinli.utils.StringUnit.stringToPermissionInfo;


@Data
public class PermissionBusinessObject {
    private Integer permissionId;
    private PermissionInfo permissioninfo;
    private Integer clubId;

    public PermissionBusinessObject(Permission permission){
        this.permissionId = permission.getPermissionId();
        this.permissioninfo = stringToPermissionInfo(permission.getPermission());
        this.clubId = permission.getClubId();
    }
}
