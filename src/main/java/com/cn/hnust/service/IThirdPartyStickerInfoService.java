package com.cn.hnust.service;

import com.cn.hnust.pojo.ThirdPartyStickerInfo;

import java.util.List;

public interface IThirdPartyStickerInfoService {
    public List<ThirdPartyStickerInfo> getAllInfos();
    public void processTask();
    public void localTask();
}
