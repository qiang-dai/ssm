package com.cn.hnust.pojo.imoji;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 *
 * Created by huangkang on 5/24/16.
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImojiStaticImageCells {
    private Map<Integer, ImojiImageCell> png;
    private Map<Integer, ImojiImageCell> webp;
}
