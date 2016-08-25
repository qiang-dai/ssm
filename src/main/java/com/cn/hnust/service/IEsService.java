package com.cn.hnust.service;

import com.cn.hnust.pojo.EsObject;
import org.elasticsearch.client.Client;

public interface IEsService {
    public void Add(String index, String docType, EsObject data);
    public Client getEsClient();
}
