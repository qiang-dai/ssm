package com.cn.hnust.service.impl;

import com.cn.hnust.service.IDownloadService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service("downloadService")
public class DownloadServiceImpl implements IDownloadService {

    public String download(URL url){
        String filename = "";
        int size= tryGetFileSize(url);
        if (size <= 0) {
            return filename;
        }
        return "";
    }

    private int tryGetFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return -1;
        } finally {
            conn.disconnect();
        }
    }
}
