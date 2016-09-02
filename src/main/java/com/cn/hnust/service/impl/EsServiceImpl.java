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
            String clusterName = "xinmei-backend-search-cluster0";
            String ip = "172.31.42.37";
            int port = 9300;

            if (true) {
                clusterName = "xinmei-backend-test-cluster0";
                ip = "172.31.28.109";
                port = 9300;
            }

            Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName)
                    .build();

            Client client = TransportClient.builder()
                    .settings(settings)
                    .build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));

            return client;
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
