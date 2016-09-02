package com.cn.hnust.service.impl.sticker;

import com.cn.hnust.dao.ThirdPartyStickerInfoDao;
import com.cn.hnust.pojo.ThirdPartyStickerInfo;
import com.cn.hnust.pojo.imoji.ImojiStickerDoc;
import com.cn.hnust.service.IEsService;
import com.cn.hnust.service.IStickerDocService;
import com.cn.hnust.service.IThirdPartyStickerInfoService;
import com.cn.hnust.service.impl.EsServiceImpl;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.List;

@Service("thirdPartyStickerInfoService")
public class ThirdPartyStickerServiceImpl implements IThirdPartyStickerInfoService {
    @Resource
    private ThirdPartyStickerInfoDao thirdPartyStickerInfoDao;
    //@Override
    public List<ThirdPartyStickerInfo> getAllInfos() {
        // TODO Auto-generated method stub
        return this.thirdPartyStickerInfoDao.selectAllInfos();
    }

    public void processTask() {
        List<ThirdPartyStickerInfo> thirdPartyStickerInfoList = getAllInfos();
        for (ThirdPartyStickerInfo thirdPartyStickerInfo : thirdPartyStickerInfoList) {
            if (thirdPartyStickerInfo.getImgWidth() < 300 || thirdPartyStickerInfo.getImgWidth() > 500) {
                continue;
            }
            //下载
            File imageLocalFile = download(thirdPartyStickerInfo.getImgUrl());
            if (imageLocalFile == null || imageLocalFile.length() == 0) {
                continue;
            }

            IStickerDocService stickerDocService = new StickerDocServiceImpl();
            ImojiStickerDoc doc = stickerDocService.getDoc(
                    thirdPartyStickerInfo.getImgUrl(),
                    imageLocalFile,
                    thirdPartyStickerInfo.getKeyWord());
            IEsService esService = new EsServiceImpl();
            esService.Add("sticker_index_1", "sticker_type", doc);
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



}
