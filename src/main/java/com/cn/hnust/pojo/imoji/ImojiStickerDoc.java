package com.cn.hnust.pojo.imoji;

import com.cn.hnust.pojo.EsObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 *
 * Created by huangkang on 5/24/16.
 */

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class ImojiStickerDoc extends EsObject {
//    private String id;
    private List<String> tags;
    private String licenseStyle;
    private ImojiImageCells images;
    private long docDownloadCount;
    private long totalDownloadCount;
    private double quality;
    private double click;
    private long updateTs;
    private String imojiQuery;
}
