package com.cn.hnust.service.impl;

import com.cn.hnust.pojo.EsObject;
import com.cn.hnust.service.IEsService;
import com.google.gson.Gson;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service("esService")
public class EsServiceImpl implements IEsService {
    private Gson gson = new Gson();

    public void Add(String index, String docType, EsObject doc){
        Client client = getEsClient();
        IndexResponse indexResponse = client.prepareIndex(index, docType, doc.getId()).setSource(gson.toJson(doc)).get();
        System.out.println(indexResponse);
    }

    public Client getEsClient() {
        try {
//            Client client = TransportClient.builder()
//                    .build()
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            // 172.31.28.109    dev         xinmei-backend-test-cluster0
            //172.31.42.37      online      xinmei-backend-search-cluster0
//            Settings settings = Settings.settingsBuilder().put("cluster.name", "xinmei-backend-test-cluster0")
//            Settings settings = Settings.settingsBuilder().put("cluster.name", "elasticsearch")
            Settings settings = Settings.settingsBuilder().put("cluster.name", "xinmei-backend-search-cluster0")
                    .build();

            Client client = TransportClient.builder()
                    .settings(settings)
                    .build()
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("52.32.227.45"), 9300));
            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.31.28.109"), 9300));

            return client;
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
