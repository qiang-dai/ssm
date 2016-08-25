package org.zsl.testmybatis;

import com.cn.hnust.pojo.imoji.ImojiStickerDoc;
import com.cn.hnust.service.IEsService;
import com.cn.hnust.service.IThirdPartyStickerInfoService;
import com.cn.hnust.service.IUserService;
import com.cn.hnust.service.impl.EsServiceImpl;
import com.google.gson.Gson;
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
import java.util.ArrayList;
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
//    @Test
//    public void test2(){
//        List<ThirdPartyStickerInfo> thirdPartyStickerInfoList = thirdPartyStickerInfoService.getAllInfos();
//        for (ThirdPartyStickerInfo thirdPartyStickerInfo : thirdPartyStickerInfoList) {
//            if (thirdPartyStickerInfo.getImgWidth() < 300 || thirdPartyStickerInfo.getImgWidth() > 500) {
//                continue;
//            }
//            //下载
//            String downFileName = download(thirdPartyStickerInfo.getImgUrl());
//            System.out.println(thirdPartyStickerInfo.getImgUrl());
//            System.out.println("downFileName= " + downFileName);
//        }
//    }

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

    @Test
    public void test4() {
        IEsService esService = new EsServiceImpl();
        ImojiStickerDoc doc = getDoc();
        List<String> tags = new ArrayList<>();
        tags.add("test");

        doc.setTags(tags);
        esService.Add("sticker_index", "sticker_type", doc);
    }

    private ImojiStickerDoc getDoc() {
        String doc = "{\n" +
                "  \"id\": \"sticker-crawl-test-0000\",\n" +
                "  \"tags\": [\n" +
                "    \"vodafone\",\n" +
                "    \"heart\",\n" +
                "    \"support\",\n" +
                "    \"#bestrong\",\n" +
                "    \"be strong\",\n" +
                "    \"compassion\",\n" +
                "    \"hug\",\n" +
                "    \"valentine\",\n" +
                "    \"i love you\",\n" +
                "    \"in love\"\n" +
                "  ],\n" +
                "  \"licenseStyle\": \"nonCommercial\",\n" +
                "  \"images\": {\n" +
                "    \"bordered\": {\n" +
                "      \"png\": {\n" +
                "        \"150\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/bordered-150.png?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 150,\n" +
                "          \"height\": 124,\n" +
                "          \"fileSize\": 16732\n" +
                "        },\n" +
                "        \"320\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/bordered-320.png?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 320,\n" +
                "          \"height\": 264,\n" +
                "          \"fileSize\": 45725\n" +
                "        },\n" +
                "        \"512\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/bordered-512.png?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 512,\n" +
                "          \"height\": 423,\n" +
                "          \"fileSize\": 75066\n" +
                "        },\n" +
                "        \"1200\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/bordered-1200.png?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 1100,\n" +
                "          \"height\": 908,\n" +
                "          \"fileSize\": 30848\n" +
                "        }\n" +
                "      },\n" +
                "      \"webp\": {\n" +
                "        \"150\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/bordered-150.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 150,\n" +
                "          \"height\": 124,\n" +
                "          \"fileSize\": 4178\n" +
                "        },\n" +
                "        \"320\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/bordered-320.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 320,\n" +
                "          \"height\": 264,\n" +
                "          \"fileSize\": 10040\n" +
                "        },\n" +
                "        \"512\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/bordered-512.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 512,\n" +
                "          \"height\": 423,\n" +
                "          \"fileSize\": 18094\n" +
                "        },\n" +
                "        \"1200\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/bordered-1200.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 1100,\n" +
                "          \"height\": 908,\n" +
                "          \"fileSize\": 22254\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"unbordered\": {\n" +
                "      \"png\": {\n" +
                "        \"150\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-150.png?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 150,\n" +
                "          \"height\": 124,\n" +
                "          \"fileSize\": 16732\n" +
                "        },\n" +
                "        \"240\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-240.png?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 240,\n" +
                "          \"height\": 198,\n" +
                "          \"fileSize\": 32054\n" +
                "        },\n" +
                "        \"320\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-320.png?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 320,\n" +
                "          \"height\": 264,\n" +
                "          \"fileSize\": 45725\n" +
                "        },\n" +
                "        \"512\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-512.png?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 512,\n" +
                "          \"height\": 423,\n" +
                "          \"fileSize\": 75066\n" +
                "        },\n" +
                "        \"960\": {\n" +
                "          \"url\": \"https://render.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-960.png?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\"\n" +
                "        },\n" +
                "        \"1200\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-1200.png?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 1100,\n" +
                "          \"height\": 908,\n" +
                "          \"fileSize\": 30848\n" +
                "        }\n" +
                "      },\n" +
                "      \"webp\": {\n" +
                "        \"150\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-150.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 150,\n" +
                "          \"height\": 124,\n" +
                "          \"fileSize\": 4178\n" +
                "        },\n" +
                "        \"240\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-240.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 240,\n" +
                "          \"height\": 198,\n" +
                "          \"fileSize\": 7128\n" +
                "        },\n" +
                "        \"320\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-320.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 320,\n" +
                "          \"height\": 264,\n" +
                "          \"fileSize\": 10040\n" +
                "        },\n" +
                "        \"512\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-512.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 512,\n" +
                "          \"height\": 423,\n" +
                "          \"fileSize\": 18094\n" +
                "        },\n" +
                "        \"960\": {\n" +
                "          \"url\": \"https://render.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-960.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\"\n" +
                "        },\n" +
                "        \"1200\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/unbordered-1200.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 1100,\n" +
                "          \"height\": 908,\n" +
                "          \"fileSize\": 22254\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"animated\": {\n" +
                "      \"gif\": {\n" +
                "        \"150\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/animated-150.gif?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 150,\n" +
                "          \"height\": 150,\n" +
                "          \"fileSize\": 123172\n" +
                "        },\n" +
                "        \"320\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/animated-320.gif?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 320,\n" +
                "          \"height\": 320,\n" +
                "          \"fileSize\": 401678\n" +
                "        },\n" +
                "        \"512\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/animated-512.gif?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 512,\n" +
                "          \"height\": 512,\n" +
                "          \"fileSize\": 800485\n" +
                "        },\n" +
                "        \"1200\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/animated-1200.gif?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 1200,\n" +
                "          \"height\": 1200,\n" +
                "          \"fileSize\": 2027122\n" +
                "        }\n" +
                "      },\n" +
                "      \"webp\": {\n" +
                "        \"150\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/animated-150.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 150,\n" +
                "          \"height\": 150,\n" +
                "          \"fileSize\": 97606\n" +
                "        },\n" +
                "        \"320\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/animated-320.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 320,\n" +
                "          \"height\": 320,\n" +
                "          \"fileSize\": 270374\n" +
                "        },\n" +
                "        \"512\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/animated-512.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 512,\n" +
                "          \"height\": 512,\n" +
                "          \"fileSize\": 493100\n" +
                "        },\n" +
                "        \"1200\": {\n" +
                "          \"url\": \"https://media.imoji.io/3c4/3c4e6e04-6176-45ac-af85-4d546d4fa1f1/animated-1200.webp?44f0f282e51a8da7b28e351724945862s9264e3422dad482c98811d830e2810b6\",\n" +
                "          \"width\": 1200,\n" +
                "          \"height\": 1200,\n" +
                "          \"fileSize\": 360432\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"docDownloadCount\": 0,\n" +
                "  \"totalDownloadCount\": 0,\n" +
                "  \"quality\": 0,\n" +
                "  \"imojiQuery\": \"heart\",\n" +
                "  \"updateTs\": 1466711016787\n" +
                "}\n";
        Gson gson = new Gson();
        ImojiStickerDoc imojiStickerDoc = gson.fromJson(doc, ImojiStickerDoc.class);
        return imojiStickerDoc;

    }

}  