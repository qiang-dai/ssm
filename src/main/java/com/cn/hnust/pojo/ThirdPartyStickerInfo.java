package com.cn.hnust.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class ThirdPartyStickerInfo {
    private Integer id;

    private String imgUrl;

    private String channel;

    private String keyWord;

    private String imgTitle;

    private String imgDesc;

    private Integer imgWidth;

    private Integer imgHeight;

    private String srcUrl;

    private String rawJsonData;

    private String srcUrlMd5;

    private Date updateTime;

}