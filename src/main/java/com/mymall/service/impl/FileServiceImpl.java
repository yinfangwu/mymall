package com.mymall.service.impl;

import com.google.common.collect.Lists;
import com.mymall.service.FileService;
import com.mymall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("FileService")
@Slf4j
public class FileServiceImpl implements FileService {

    /**
     * 文件上传
     * @param file
     * @param path
     * @return 上传文件名
     */
    @Override
    public String upload(MultipartFile file, String path) {
        //文件名
        //扩展名
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //上传文件名
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始上传文件，上传文件的文件名:{},上传路径:{},新文件名:{}", fileName, path, uploadFileName);

        //创建文件夹路径
        File fileDir = new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdir();//mkdirs递归创建，mkdir只建一个，多级返回false
        }
        File targetFile = new File(path, uploadFileName);
        try{
            //上传文件到应用服务器
            file.transferTo(targetFile);
            //上传文件到FTP服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //上传完成后删除应用上的文件
            targetFile.delete();
        }catch (IOException e){
            log.error("文件上传异常", e);
        }
        return targetFile.getName();
    }
}
