package com.cn.hnust.service.impl;

import com.cn.hnust.dao.IThirdPartyStickerInfoDao;
import com.cn.hnust.pojo.ThirdPartyStickerInfo;
import com.cn.hnust.service.IThirdPartyStickerInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("thirdPartyStickerInfoService")
public class ThirdPartyStickerServiceImpl implements IThirdPartyStickerInfoService {
    @Resource
    private IThirdPartyStickerInfoDao thirdPartyStickerInfoDao;
    //@Override
    public List<ThirdPartyStickerInfo> getAllInfos() {
        // TODO Auto-generated method stub
        return this.thirdPartyStickerInfoDao.selectAllInfos();
    }

}
