package com.cn.hnust.dao;

import com.cn.hnust.pojo.ThirdPartyStickerInfo;

import java.util.List;

public interface IThirdPartyStickerInfoDao {
    List<ThirdPartyStickerInfo> selectAllInfos();
}