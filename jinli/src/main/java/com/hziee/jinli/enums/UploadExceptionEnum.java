package com.hziee.jinli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UploadExceptionEnum {

    INVALID_FILE_TYPE(400, "无效的文件类型"),
    UPLOAD_FILE_ERROR(500, "文件上传失败");

    private int code;
    private String msg;
}
