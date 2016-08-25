package org.zsl.testmybatis;

import com.cn.hnust.pojo.ThirdPartyStickerInfo;
import com.cn.hnust.pojo.imoji.ImojiStickerDoc;
import com.cn.hnust.service.IEsService;
import com.cn.hnust.service.IThirdPartyStickerInfoService;
import com.cn.hnust.service.IUploadService;
import com.cn.hnust.service.IUserService;
import com.cn.hnust.service.impl.EsServiceImpl;
import com.cn.hnust.service.impl.UploadServiceImpl;
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
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
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
    @Test
    public void test2(){
        List<ThirdPartyStickerInfo> thirdPartyStickerInfoList = thirdPartyStickerInfoService.getAllInfos();
        for (ThirdPartyStickerInfo thirdPartyStickerInfo : thirdPartyStickerInfoList) {
            if (thirdPartyStickerInfo.getImgWidth() < 300 || thirdPartyStickerInfo.getImgWidth() > 500) {
                continue;
            }
            //下载
            File imageFile = download(thirdPartyStickerInfo.getImgUrl());
            if (imageFile == null || imageFile.length() == 0) {
                continue;
            }

            IEsService esService = new EsServiceImpl();
            ImojiStickerDoc doc = getDoc(
                imageFile,
                thirdPartyStickerInfo.getKeyWord());
            esService.Add("sticker_index", "sticker_type", doc);
        }
    }

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

    public static String getMd5ByFile(String fileName) {
        String value = null;
        try {
            File file = new File(fileName);
            FileInputStream in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(32);
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return value;
    }

    public File download(String url) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        File dstFile = null;
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
                File file = new File("craw_sticker/" + getFileName(url));
                file.mkdirs();

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

                    //rename
                    String md5_name = getMd5ByFile(file.getAbsolutePath());
                    md5_name = "craw_sticker/"+md5_name;

                    File newFile = new File(md5_name);
                    file.renameTo(newFile);

                    return newFile;
                }
            }
            httpclient.close();
            response.close();
        } catch (Exception e) {
            System.out.println(e);
        }finally{
        }

        return dstFile;
    }

    private ImojiStickerDoc getDoc(File imageFile, String keyWord) {
        String docTemplate =
                "{" +
                "    \"updateTs\": 1466711016787, " +
                "    \"tags\": [" +
                "    ], " +
                "    \"imojiQuery\": \"heart\", " +
                "    \"licenseStyle\": \"nonCommercial\", " +
                "    \"docDownloadCount\": 0, " +
                "    \"images\": {" +
                "        \"animated\": {" +
                "            \"gif\": {" +
                "                \"150\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"320\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"512\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"1200\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }" +
                "            }, " +
                "            \"webp\": {" +
                "                \"150\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"320\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"512\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"1200\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }" +
                "            }" +
                "        }, " +
                "        \"bordered\": {" +
                "            \"webp\": {" +
                "                \"150\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"320\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"512\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"1200\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }" +
                "            }, " +
                "            \"png\": {" +
                "                \"150\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"320\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"512\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"1200\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }" +
                "            }" +
                "        }, " +
                "        \"unbordered\": {" +
                "            \"webp\": {" +
                "                \"150\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"240\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"320\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"512\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"960\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\"" +
                "                }, " +
                "                \"1200\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }" +
                "            }, " +
                "            \"png\": {" +
                "                \"150\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"240\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"320\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"512\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }, " +
                "                \"960\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\"" +
                "                }, " +
                "                \"1200\": {" +
                "                    \"url\": \"xxxxxxxxxxxxxx\", " +
                "                    \"width\": 1001, " +
                "                    \"fileSize\": 3003, " +
                "                    \"height\": 2002" +
                "                }" +
                "            }" +
                "        }" +
                "    }, " +
                "    \"totalDownloadCount\": 0, " +
                "    \"quality\": 0, " +
                "    \"id\": \"3c4e6e04-6176-45ac-af85-4d546d4fa1f1\"" +
                "}";

        //获取信息

        try {
            BufferedImage image = ImageIO.read(imageFile);

            IUploadService uploadService = new UploadServiceImpl();
            String url = uploadService.upload(imageFile.toString());

            String doc = docTemplate;
            doc = doc.replaceAll("1001", String.valueOf(imageFile.length()));
            doc = doc.replaceAll("2002", String.valueOf(image.getWidth()));
            doc = doc.replaceAll("3003", String.valueOf(image.getHeight()));
            doc = doc.replaceAll("xxxxxxxxxxxxxx", url);
            Gson gson = new Gson();

            ImojiStickerDoc imojiStickerDoc = gson.fromJson(doc, ImojiStickerDoc.class);
            List<String> tags = new ArrayList<>();
            tags.add(keyWord);
            imojiStickerDoc.setTags(tags);
            imojiStickerDoc.setId("craw_sticker" + imageFile.getName());
            imojiStickerDoc.setImojiQuery(keyWord);

            return imojiStickerDoc;
        }catch (IOException e) {
            return null;
        }
    }

}  