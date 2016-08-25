package org.zsl.testmybatis;

import com.cn.hnust.pojo.ThirdPartyStickerInfo;
import com.cn.hnust.service.IThirdPartyStickerInfoService;
import com.cn.hnust.service.IUserService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestMyBatis {
    private static Logger logger = Logger.getLogger(TestMyBatis.class);
    //  private ApplicationContext ac = null;
    @Resource
    private IUserService userService = null;

    @Resource
    private IThirdPartyStickerInfoService thirdPartyStickerInfoService = null;

    @Before
    public void before() {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        userService = (IUserService) ac.getBean("userService");
        thirdPartyStickerInfoService = (IThirdPartyStickerInfoService) ac.getBean("thirdPartyStickerInfoService");
    }

//    @Test
//    public void test1() {
//        User user = userService.getUserById(1);
//         System.out.println(user.getUserName());
//         logger.info("值："+user.getUserName());
//        logger.info(JSON.toJSONString(user));
//    }
//
    @Test
    public void test2(){
        List<ThirdPartyStickerInfo> thirdPartyStickerInfoList = thirdPartyStickerInfoService.getAllInfos();
        for (ThirdPartyStickerInfo thirdPartyStickerInfo : thirdPartyStickerInfoList) {
//            System.out.println(thirdPartyStickerInfo);
            if (thirdPartyStickerInfo.getImgWidth() < 300 || thirdPartyStickerInfo.getImgWidth() > 500) {
                continue;
            }
            //下载
            String downFileName = download(thirdPartyStickerInfo.getImgUrl());
            System.out.println(thirdPartyStickerInfo.getImgUrl());
            System.out.println("downFileName= " + downFileName);
        }
    }

//    @Test
//    public void test3() {
//        String url = "https://facedetection.com/wp-content/uploads/m01-32_gr.jpg";
//        url = "https://www.google.com.hk/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0ahUKEwjT2raxztvOAhXGJJQKHcIbDpAQFggcMAA&url=http%3A%2F%2Fwww.ed.ac.uk%2Ffiles%2Fimports%2FfileManager%2FUNIT_7_Speaking.pdf&usg=AFQjCNEIOGGgbhnVd-JexlQ6W6Zp_EBIJg&sig2=Nl0tDrIQuGzYowbupLjqbQ";
//        download(url);
//    }


    private String getFileName(String url) {
        String fileName = url;
        if (url.indexOf("?") != -1) {
            fileName = fileName.substring(0, url.indexOf("?"));
        }
        if (url.indexOf("/") != -1) {
            fileName = fileName.substring(url.lastIndexOf("/") + 1);
        }
        return fileName;
    }
    private String getPrefix(String fileName) {
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        return prefix;
    }

    private String getSuffix(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        return suffix;
    }

    public String download(String url) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        String dstFileName = "";
        try {
            httpclient = HttpClients.createDefault();
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            // 执行get请求.
            response = httpclient.execute(httpget);
            Header header = response.getFirstHeader("content-length");
            System.out.println(header.getValue());
            String length = header.getValue();
            Integer size = Integer.parseInt(length);

            // 获取响应实体
            HttpEntity entity = response.getEntity();

            // 打印响应状态
            System.out.println(response.getStatusLine().getStatusCode());
            if (entity != null) {
                // 打印响应内容
                //System.out.println("Response content: " + EntityUtils.toString(entity));
                //Save to file
                File file = new File("downFileDir/" + getFileName(url));

                System.out.println("suffix= " + getSuffix(file.getName()));

                if (file.exists()) {
                    file.delete();
                } else {
                    file.createNewFile();
                }
                //save file
                if (entity.getContentLength() != size) {
                    System.out.println("not equal size");
                } else {
                    InputStream is = entity.getContent();
                    FileOutputStream fos = new FileOutputStream(file.getPath());
                    int inByte;
                    while ((inByte = is.read()) != -1)
                        fos.write(inByte);
                    is.close();
                    fos.close();

                    dstFileName = file.getAbsolutePath();
                }
            }
            httpclient.close();
            response.close();
        } catch (Exception e) {
            System.out.println(e);
        }finally{
        }

        return dstFileName;
    }
}  