package com.hziee.jinli.service;

import com.hziee.jinli.enums.UploadExceptionEnum;
import com.hziee.jinli.exception.JlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UploadService {

    private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpeg", "image/png", "image/bmp");

    public String uploadImage(MultipartFile file){

        try {
            //校验文件类型
            String contentType = file.getContentType();
            if (!ALLOW_TYPES.contains(contentType)){
                throw new JlException(UploadExceptionEnum.INVALID_FILE_TYPE);
            }
            //校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null){
                throw new JlException(UploadExceptionEnum.INVALID_FILE_TYPE);
            }

            //准备目标路径
            File dest = new File("C:\\Users\\joe\\Desktop\\2018finalwork\\upload", file.getOriginalFilename());
            //保存文件到本地
            file.transferTo(dest);
            //返回路径
            return "http://image.jinli.com/" + file.getOriginalFilename();
        } catch (IOException e) {
            log.error("上传失败", e);
            throw new JlException(UploadExceptionEnum.UPLOAD_FILE_ERROR);
        }

    }
}
