package com.cctv.ewservice.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author neusoft
 * @Date Created in 2018/7/16
 */
@Configuration
public class ElasticsearchConfig {
    @Value("${spring.elasticsearch.cluster-name}")
    private String cluster_name;

    @Value("${spring.elasticsearch.node-host1}")
    private String node_host1;

    @Value("${spring.elasticsearch.node-host2}")
    private String node_host2;

    @Value("${spring.elasticsearch.node-host3}")
    private String node_host3;

    @Value("${spring.elasticsearch.tcp-port}")
    private int tcp_port;

    @Bean(name = "transportClient")
    public TransportClient getEsClient() {
        TransportClient client = null;
//        long l1 = System.currentTimeMillis();
        if (client == null) {
            synchronized (TransportClient.class) {
                Settings settings = Settings.builder().put("cluster.name", cluster_name).build();
                try {
                    client = new PreBuiltTransportClient(settings)
                            .addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(node_host1), tcp_port),
                                    new InetSocketTransportAddress(InetAddress.getByName(node_host2), tcp_port),
                                    new InetSocketTransportAddress(InetAddress.getByName(node_host3), tcp_port));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l2 = System.currentTimeMillis();
        String sd1 = sdf.format(new Date(l2 - l1));   // 时间戳转换成时间
        System.out.println("链接" + (l2 - l1));*/
        return client;
    }
}
