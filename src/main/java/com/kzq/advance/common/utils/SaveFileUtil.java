package com.kzq.advance.common.utils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public  class SaveFileUtil {

    public   List<String>  saveFile(MultipartFile file, HttpServletRequest request, List imglist , String imgPath){
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 保存的文件路径(如果用的是Tomcat服务器，\
                String imgFolder = request.getSession().getServletContext().getRealPath(imgPath);
                //获取图片原来的名字
                String oldName = file.getOriginalFilename();
                //获取文件后缀名称
                String exeName = oldName.substring(oldName.lastIndexOf("."), oldName.length());
                //重命名
                String imgUrl = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + new Random().nextInt(1000) + exeName;
                imglist.add(file.getOriginalFilename());
                imglist.add(imgUrl);
                File targetImg = new File(imgFolder,imgUrl);
                if (!targetImg.exists() && !targetImg.isDirectory()) {
                    //不存在新建
                    targetImg.mkdirs();
                }
                //保存targetImg
                file.transferTo(targetImg);
                return  imglist;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imglist;

    }



}
