package com.cctv.excel;

import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Created by wangcai on 2018/06/26
 */
public class EsClientTools {
    private static final String CLUSTER_NAME = "dap-search";
//    private static final String NODE_HOST1 = "10.1.0.130";
    private static final String NODE_HOST1 = "10.117.33.4";
//    private static final String NODE_HOST2 = "10.1.0.120";
    private static final String NODE_HOST2 = "10.117.33.5";
//    private static final String NODE_HOST3 = "10.1.0.123";
    private static final String NODE_HOST3 = "10.117.33.6";
    private static final int TCP_PORT = 9300;
    private static TransportClient client;

    /**
     * 建立连接
     * @return
     */
    public static TransportClient getEsClient() {
        if (client == null) {
            synchronized (TransportClient.class) {
                Settings settings = Settings.builder().put("cluster.name", CLUSTER_NAME).build();
                try {
                    client = new PreBuiltTransportClient(settings)
                            .addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(NODE_HOST1), TCP_PORT),
                                    new InetSocketTransportAddress(InetAddress.getByName(NODE_HOST2), TCP_PORT),
                                    new InetSocketTransportAddress(InetAddress.getByName(NODE_HOST3), TCP_PORT));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l1 = System.currentTimeMillis();
        String sd1 = sdf.format(new Date(l1));   // 时间戳转换成时间
//        System.out.println("连接" + sd1);
        return client;
    }

    /**
     * 获取索引管理的IndicesAdminClient
     * @return
     */
    public static IndicesAdminClient getAdminClient() {
        return getEsClient().admin().indices();
    }

    /**
     * 关闭连接
     */
    public static void closeClient() {
        if (null != client) {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
