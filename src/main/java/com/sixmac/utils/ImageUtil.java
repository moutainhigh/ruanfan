package com.sixmac.utils;

import com.sixmac.core.Configue;
import com.sixmac.entity.Image;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by wangbin on 2014/12/30.
 */
public class ImageUtil {

    private static String imagePath = PathUtils.getProjectName() + "upload/temp/";
    private static String imageSuoluePath = PathUtils.getProjectName() + "upload/suolue/";

    public static void compactImage(File srcFile, String destDir, String destImage) throws IOException {
        Thumbnails.of(srcFile).scale(1f).outputQuality(0.5).toFile(new File(destDir, destImage));
    }

    public static void thumbImage(File srcFile, String destDir, String destImage, int width, int height) throws IOException {
        BufferedImage srcImage = ImageIO.read(srcFile);
        Thumbnails.of(srcImage)
                .size(width, height)
                .outputQuality(1)
                .keepAspectRatio(false)
                .toFile(new File(destDir, destImage));
    }


    public static void changeImgPath(Image image) {
        if (image == null) {
            return;
        }
        String path = image.getPath();
        if (path.contains(Configue.getUploadUrl())) {
            return;
        }
        image.setPath(Configue.getUploadUrl() + image.getPath());
    }

    public static Map<String, Long> getImgInfo(String imgPath) {
        File file = new File(imgPath);
        return getImgInfo(file);
    }


    public static Map<String, Long> getImgInfo(File imgFile) {
        Map<String, Long> map = new HashMap<String, Long>(3);
        try {
            FileInputStream fis = new FileInputStream(imgFile);
            BufferedImage buff = ImageIO.read(imgFile);
            map.put("width", buff.getWidth() * 1L);
            map.put("height", buff.getHeight() * 1L);
            map.put("size", imgFile.length());
            fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("所给的图片文件" + imgFile.getPath() + "不存在！计算图片尺寸大小信息失败！");
            map = null;
        } catch (IOException e) {
            System.err.println("计算图片" + imgFile.getPath() + "尺寸大小信息失败！");
            map = null;
        }
        return map;
    }

    /**
     * @return BufferedImage 缩放后的图片
     * @Description 缩放图片
     * source 源图片,targetW 缩放后的宽度,targetH 缩放后的长度;
     * @date 2014-7-30 下午03:08:52
     * @author 龙哲
     */
    private static BufferedImage getResizedImage(BufferedImage source, int targetW, int targetH) {
        int type = source.getType();
        BufferedImage target = null;
        // targetW，targetH分别表示目标长和宽
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        //如果目标长度超出现有长度，则返回原图，如果目标长度超出现有长度，则返回原图
        if (targetW >= source.getWidth() || targetH >= source.getHeight()) {
            return source;
        }
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        // 则将下面的if else语句注释即可
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     * @return BufferedImage 缩放后的图片
     * @Description 等比缩放图片
     * file 源图片,targetW 缩放后的宽度,targetH 缩放后的长度;
     * @date 2014-7-30 下午03:08:52
     * @author 龙哲
     */
    public static BufferedImage resize(File file, int width, int height) {
        try {
            BufferedImage srcImage = ImageIO.read(file);
            if (width > 0 || height > 0) {
                srcImage = getResizedImage(srcImage, width, height);
            }
            return srcImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片上传 并保存缩略图(上传的文件保存在项目同级的目录下) imgWidth 原图宽度， imgHeight 原图高度 ，imgURL 原图路径， imgThu 缩略图路径
     *
     * @param request
     * @param imageFile
     * @param needSmallImage
     * @return
     * @throws Exception
     */
    public static Map<String, Object> saveImage(ServletRequest request, MultipartFile imageFile, boolean needSmallImage) throws Exception {
        Random rand = new Random();
        Map<String, Object> pathMap = new HashMap<String, Object>();
        if (null != imageFile && imageFile.getOriginalFilename().toLowerCase().trim().length() != 0) {
            String originalFileName = imageFile.getOriginalFilename().toLowerCase();
            String filetype = originalFileName.substring(originalFileName.indexOf("."));
            String timeMillis = String.valueOf(System.currentTimeMillis());
            String path = imagePath + timeMillis + "_" + rand.nextInt(1000000) + filetype;
            String webpath = getWebPath(request);
            // 获取项目在磁盘上面的物理路径
            File image = new File(webpath + path);
            File dir = image.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileCopyUtils.copy(imageFile.getBytes(), image);

            // 获取图片属性
            BufferedImage imgBufferedImage = ImageIO.read(image);
            // 原图宽度
            pathMap.put("imgWidth", imgBufferedImage.getWidth());
            // 原图高度
            pathMap.put("imgHeight", imgBufferedImage.getHeight());
            // 原图URL
            pathMap.put("imgURL", "/" + path);
            if (needSmallImage) {
                String suoluepath = imageSuoluePath + timeMillis + "_" + rand.nextInt(1000000) + filetype;
                try {
                    FileCopyUtils.copy(imageFile.getBytes(), image);
                    BufferedImage bi = ImageUtil.resize(image, 500, 500);// 根据上传的图片文件生成对应的缩略图
                    File smallfile = new File(new File(webpath) + "/" + suoluepath);
                    if (!smallfile.getParentFile().exists()) {// 如果文件夹不存在
                        smallfile.getParentFile().mkdirs();// 创建上传文件夹
                    }
                    ImageIO.write(bi, "jpg", smallfile);// 将缩略图写入服务器端的制定文件夹中
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 缩略图路径
                pathMap.put("imgThu", "/" + suoluepath);
            } else {
                // 缩略图路径
                pathMap.put("imgThu", "");
            }
        }
        return pathMap;
    }

    /**
     * @return BufferedImage 缩放后的图片
     * @Description 删除图片
     * filePath 图片路径,request request请求;
     * @date 2014-7-30 下午03:08:52
     * @author 龙哲
     */
    public static void removeImage(String filePath, ServletRequest request) throws Exception {
        if (null == filePath || "".equals(filePath.trim()))
            return;
        String webpath = getWebPath(request);
        String tempString = "";
        // 大图
        if (0 < filePath.indexOf(imagePath))
            tempString = filePath.substring(filePath.indexOf(imagePath));
        else if (0 < filePath.indexOf(imageSuoluePath))
            tempString = filePath.substring(filePath.indexOf(imageSuoluePath));
        String serverFilePath = webpath + tempString;

        FileUploadUtils.removeFile(serverFilePath);
    }

    /**
     * @return String 时间格式名称
     * @Description 传入原图名称, 获得一个以时间格式的新名称
     * fileName 原图名称;
     * @date 2014-7-30 下午03:08:52
     * @author 龙哲
     */
    public static String generateFileName(String fileName) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDate = format.format(new Date());
        int random = new Random().nextInt(10000);
        int position = fileName.lastIndexOf(".");
        String extension = fileName.substring(position);
        return formatDate + random + extension;
    }

    /**
     * @return String 时间格式名称
     * @Description 获取项目在tomcat下的地址
     * request request 请求;
     * @date 2014-7-30 下午03:08:52
     * @author 龙哲
     */
    public static String getWebPath(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(true);
        String path = session.getServletContext().getRealPath("/").replace(File.separator, "/");
        String[] paths = path.split("/");
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < paths.length - 1; i++) {
            buff.append(paths[i] + "/");
        }
        String newpath = buff.toString();
        return newpath;
    }


}
