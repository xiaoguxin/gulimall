package com.atguigu.gulimall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class GulimallThirdPartyApplicationTests {

    @Autowired
    private OSSClient ossClient;

    /*@Test
    public void testUpload() {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "oss-cn-guangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tLBAm4vXZuQrWqGC6Lw";
        String accessKeySecret = "uYhrfk30FUBwaNcumf5FORRJUcE9wh";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "mall-local";
        // 填写文件名。文件名包含路径，不包含Bucket名称。例如exampledir/exampleobject.txt。
        String objectName = "C:/Users/67532/Desktop/problem/comics/20220129195046.jpg";

        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


            //上传文件流
            try {
                InputStream inputStream=new FileInputStream(objectName);
                ossClient.putObject(bucketName, "20220129195046.jpg", inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //String content = "Hello OSS";
            //ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
        } catch (OSSException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
            System.out.println("上传完成。。。。");
        }
    }*/


    @Test
    public void saveFile() {
        String bucketName = "mall-local";
        String objectName = "20220130155507.jpg";
        String path = "C:/Users/67532/Desktop/problem/comics/20220130155507.jpg";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            ossClient.putObject(bucketName, "20220130155507.jpg", inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
            System.out.println("上传完成。。。。");
        }

    }

    @Test
    void contextLoads() {
    }

}
