package com.sixmac.utils;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletRequest;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件到服务器及删除文件工具类
 *
 * @author 龙哲
 * @依赖：spring-core-3.1.2.RELEASE.jar,spring-web-3.1.2.RELEASE.jar
 * @date 2014-10-14 16:07:05
 */
public class FileUploadUtils {

    /**
     * 文件上传(上传的文件保存在项目同级的目录下)
     *
     * @param file ：需要上传的文件
     * @return String:文件上传返回的文件路径
     * @throws IOException
     */
    public static String uploadFile(MultipartFile file, ServletRequest request) throws IOException {
        if (file != null && file.getOriginalFilename().toLowerCase().trim().length() != 0) {
            Random rand = new Random();
            String filename = file.getOriginalFilename().toLowerCase();
            String filetype = filename.substring(filename.indexOf("."));// 获取后缀名
            String path = PathUtils.getProjectName() + "upload/files/" + System.currentTimeMillis() + "_"
                    + rand.nextInt(1000000) + filetype;
            // 获取项目在磁盘上面的物理路径
            File image = new File(ImageUtil.getWebPath(request) + path);
            File dir = image.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 写入文件
            FileCopyUtils.copy(file.getBytes(), image);
            return PathUtils.getRemotePath() + "/" + path;
        } else {
            return null;
        }
    }

    /**
     * 删除文件
     *
     * @return
     * @author Jason
     * filePath 文件磁盘路径
     */
    public static void removeFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists())
                file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}