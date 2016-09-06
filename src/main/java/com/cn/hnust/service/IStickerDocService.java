package com.cn.hnust.service;

import com.cn.hnust.pojo.imoji.ImojiStickerDoc;
import java.io.File;

public interface IStickerDocService {
    public ImojiStickerDoc getDoc(String url, File imageLocalFile, String keyWord, String md5);
}
