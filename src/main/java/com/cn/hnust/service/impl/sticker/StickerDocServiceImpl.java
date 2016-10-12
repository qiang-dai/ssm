package com.cn.hnust.service.impl.sticker;

import com.cn.hnust.pojo.imoji.ImojiStickerDoc;
import com.cn.hnust.service.IStickerDocService;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DaiQiang on 16/8/26.
 */
public class StickerDocServiceImpl implements IStickerDocService{
    public ImojiStickerDoc getDoc(String url, File imageLocalFile, String keyWord, String md5) {
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
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"320\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"512\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"1200\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }" +
                        "            }, " +
                        "            \"webp\": {" +
                        "                \"150\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"320\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"512\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"1200\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }" +
                        "            }" +
                        "        }, " +
                        "        \"bordered\": {" +
                        "            \"webp\": {" +
                        "                \"150\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"320\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"512\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"1200\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }" +
                        "            }, " +
                        "            \"png\": {" +
                        "                \"150\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"320\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"512\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"1200\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }" +
                        "            }" +
                        "        }, " +
                        "        \"unbordered\": {" +
                        "            \"webp\": {" +
                        "                \"150\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"240\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"320\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"512\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"960\": {" +
                        "                    \"url\": \"IMAGE_URL\"" +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"1200\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }" +
                        "            }, " +
                        "            \"png\": {" +
                        "                \"150\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"240\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"320\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"512\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"960\": {" +
                        "                    \"url\": \"IMAGE_URL\"" +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
                        "                }, " +
                        "                \"1200\": {" +
                        "                    \"url\": \"IMAGE_URL\", " +
                        "                    \"width\": IMAGE_WIDTH, " +
                        "                    \"fileSize\": IMAGE_FILESIZE, " +
                        "                    \"height\": IMAGE_HEIGHT" +
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
            BufferedImage image = ImageIO.read(imageLocalFile);

            //IUploadService uploadService = new UploadServiceImpl();
            //url = uploadService.upload(imageLocalFile.toString());

            String doc = docTemplate;
            doc = doc.replaceAll("IMAGE_FILESIZE", String.valueOf(imageLocalFile.length()));
            doc = doc.replaceAll("IMAGE_WIDTH", String.valueOf(image.getWidth()));
            doc = doc.replaceAll("IMAGE_HEIGHT", String.valueOf(image.getHeight()));
            doc = doc.replaceAll("IMAGE_URL", url);
            Gson gson = new Gson();

            ImojiStickerDoc imojiStickerDoc = gson.fromJson(doc, ImojiStickerDoc.class);
            List<String> tags = new ArrayList<>();
            tags.add(keyWord);
            imojiStickerDoc.setTags(tags);
            imojiStickerDoc.setId("crawl_sticker" + md5);
            imojiStickerDoc.setImojiQuery(keyWord);

            System.out.println("keyWord= " + keyWord);

            return imojiStickerDoc;
        }catch (Exception e) {
            return null;
        }
    }
}
