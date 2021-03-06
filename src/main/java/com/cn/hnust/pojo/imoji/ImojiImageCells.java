package com.cn.hnust.pojo.imoji;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 *
 * Created by huangkang on 5/24/16.
 */

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImojiImageCells {
    private ImojiStaticImageCells bordered;
    private ImojiStaticImageCells unbordered;
    private ImojiDynamicImageCells animated;
}
