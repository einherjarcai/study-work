/*
package com.cctv.ewservice.account.dao;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @author wangcai
 * @Date Created in 2018/9/18
 *//*

public class AccountDao {
    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    @PostConstruct
    public void init() {
        client = this.transportClient;
    }

    public void getFollow(Map<String, String> map) {
        QueryBuilder termquery1 = QueryBuilders.termQuery("type", map.get("type"));
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", map.get("level"));
        if (map.get("type") != "0") {
            termquery1 = QueryBuilders.termQuery("type", map.get("type"));
        }
        if (map.get("level") != "0") {
            termquery2 = QueryBuilders.termQuery("level", map.get("level"));
        }
        QueryBuilder termquery3 = QueryBuilders.termQuery("date", map.get("date"));
        QueryBuilder termquery4 = QueryBuilders.termQuery("channel", map.get("channel"));
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        if (map.get("type") != "0") {
            boolquery.must(termquery1);
        }
        if (map.get("level") != "0") {
            boolquery.must(termquery2);
        }
        boolquery.must(termquery3)
                .must(termquery4);
        SumAggregationBuilder sumAgg = AggregationBuilders.sum("follow").field("user");
        TermsAggregationBuilder termAgg = AggregationBuilders.terms("account").field("id").size(1000000);

        SearchResponse response = client
                .prepareSearch("")
                .setTypes("type")
                .setQuery(boolquery)
                .addAggregation(termAgg.subAggregation(sumAgg))
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000000)
                .get();
        Terms term = response.getAggregations().get("account");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Terms.Bucket entry : term.getBuckets()) {
            Map<String, String> weixin = new HashMap<String, String>();
            String weiXinName = entry.getKeyAsString();
            Sum sum = entry.getAggregations().get("follow");
            weixin.put("name", weiXinName);
            weixin.put("follow", String.valueOf(sum));
            list.add(weixin);
        }
    }


    public void getArticleCount() {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
    }
}
*/
