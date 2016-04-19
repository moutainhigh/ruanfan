package com.sixmac.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
public class QiNiuUploadImgUtil {
    //设置好账号的ACCESS_KEY和SECRET_KEY
    public static String ACCESS_KEY = "9XfG90z67R7-ynpjOfamAQRss7mR72StTrLWFy2v";
    public static String SECRET_KEY = "0n21SLv9rb5AtPiUrXRrq412iCGsl2wXuaQW5eG0";
    //要上传的空间
    public static String bucketName = "ruanfanimg";
    public static String url = "http://7xt4xe.com2.z0.glb.qiniucdn.com/";

    //密钥配置
    public static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    public static UploadManager uploadManager = new UploadManager();
    //实例化一个BucketManager对象
    public static BucketManager bucketManager = new BucketManager(auth);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static String getUpToken() {
        return auth.uploadToken(bucketName);
    }

    /**
     * 上传文件
     *
     * @param myFile
     */
    public static String upload(MultipartFile myFile) {
        try {
            CommonsMultipartFile cf = (CommonsMultipartFile) myFile; //这个myfile是MultipartFile的
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();
            File file = fi.getStoreLocation();

            //调用put方法上传
            Response res = uploadManager.put(file, null, getUpToken());
            Map<String, Object> map = JsonUtil.jsontoMap(res.bodyString());
            return url + map.get("key");
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                e1.printStackTrace();
            }
            return "";
        }
    }

    /**
     * 上传文件
     *
     * @param file
     */
    public static String upload(File file) {
        try {
            //调用put方法上传
            Response res = uploadManager.put(file, null, getUpToken());
            Map<String, Object> map = JsonUtil.jsontoMap(res.bodyString());
            return url + map.get("key");
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                e1.printStackTrace();
            }
            return "";
        }
    }

    /**
     * 图片的key
     *
     * @param key
     */
    public static void delete(String key) {
        try {
            //调用delete方法移动文件
            bucketManager.delete(bucketName, key);
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            System.out.println(r.toString());
        }
    }

    public static void main(String[] args) {
        upload(new File("F:/Images/动物/QQ图片20141128173523.gif"));
    }
}
