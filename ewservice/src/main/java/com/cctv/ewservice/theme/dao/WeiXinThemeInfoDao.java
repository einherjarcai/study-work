package com.cctv.ewservice.theme.dao;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangcai
 * @Date Created in 2018/9/22
 */
@Repository
public class WeiXinThemeInfoDao {
    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    @PostConstruct
    public void init() {
        client = this.transportClient;
    }

    public Map<String, String> getWeiXinIdInfo(String weixinid) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("wx_id", weixinid);
        boolquery.must(termquery1);
        SearchResponse response = client
                .prepareSearch("weixin_account_list")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();
        SearchHits hits = response.getHits();
        Map<String, String> idmap = new HashMap<String, String>();

        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            idmap.put("name", String.valueOf(hitmap.get("nickname")));
            idmap.put("type", String.valueOf(hitmap.get("type")));
            idmap.put("level", String.valueOf(hitmap.get("level")));
            idmap.put("channel", String.valueOf(hitmap.get("channel")));
        }
        return idmap;
    }

    /**
     * 获取微信主题页面信息
     * @param theme
     * @param department
     * @param startdate
     * @param enddate
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    public List<Map<String,String>> getWeiXinThemeData(String theme, String department, String startdate, String enddate, String keyword, int page, int size) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("theme", theme);
        QueryBuilder termquery2 = QueryBuilders.termQuery("department", department);
        QueryBuilder termquery3 = QueryBuilders.termQuery("level", "3");
        QueryBuilder termquery4 = QueryBuilders.termQuery("type", "8");
        QueryBuilder termquery5 = QueryBuilders.termQuery("mold", "1");
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("news_postdate").from(startdate).to(enddate);
        boolquery.must(termquery1)
                .must(termquery2)
//                .must(termquery3)
                .must(termquery4)
                .must(termquery5)
                .must(rangequery);

        SearchResponse response = null;
        if (keyword != null) {
            QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("title", keyword);
            boolquery.must(queryBuilder2);
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .setMinScore((float) 10)
                    .addSort("news_postdate", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setFrom(size*(page-1)).setSize(size).setExplain(true)
                    .get();
        } else {
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .addSort("news_postdate", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setFrom(size*(page-1)).setSize(size).setExplain(true)
                    .get();
        }

        SearchHits hits = response.getHits();
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        if (hits.totalHits > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> sourceData = new HashMap<String, Object>();
                sourceData = hit.getSourceAsMap();
                Map<String, String> map = new HashMap<String, String>();
                map.put("theme", theme);
                map.put("department", department);
                map.put("date", String.valueOf(sourceData.get("news_postdate")));
                map.put("url", String.valueOf(sourceData.get("news_url")));
                map.put("title", String.valueOf(sourceData.get("news_title")));
                map.put("read", String.valueOf(sourceData.get("news_read_count")));
                map.put("repost", String.valueOf(sourceData.get("news_reposts_count")));
                map.put("like", String.valueOf(sourceData.get("news_like_count")));
                String wx_id = String.valueOf(sourceData.get("wx_name"));
                map.putAll(getWeiXinIdInfo(wx_id));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 获取微信主题总数
     * @param theme
     * @param department
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public int getWeiXinThemeTotal(String theme, String department, String startdate, String enddate, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("theme", theme);
        QueryBuilder termquery2 = QueryBuilders.termQuery("department", department);
        QueryBuilder termquery3 = QueryBuilders.termQuery("level", "3");
        QueryBuilder termquery4 = QueryBuilders.termQuery("type", "8");
        QueryBuilder termquery5 = QueryBuilders.termQuery("mold", "1");
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("news_postdate").from(startdate).to(enddate);
        boolquery.must(termquery1)
                .must(termquery2)
//                .must(termquery3)
                .must(termquery4)
                .must(termquery5)
                .must(rangequery);

        SearchResponse response = null;

        if (keyword != null) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", keyword);
            boolquery.must(queryBuilder);
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .setMinScore((float) 10)
                    .addSort("news_postdate", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        } else {
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .addSort("news_postdate", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        }

        SearchHits hits = response.getHits();
        int count = (int)hits.getTotalHits();
        return count;
    }

    /**
     * 微信主题下载信息
     * Elasticsearch 每次查询最多返回10000条数据，建议时间范围不能过大
     * @param theme
     * @param department
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public List<Map<String,String>> getWeiXinThemeDownLoadData(String theme, String department, String startdate, String enddate, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("theme", theme);
        QueryBuilder termquery2 = QueryBuilders.termQuery("department", department);
        QueryBuilder termquery3 = QueryBuilders.termQuery("level", "3");
        QueryBuilder termquery4 = QueryBuilders.termQuery("type", "8");
        QueryBuilder termquery5 = QueryBuilders.termQuery("mold", "1");
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("news_postdate").from(startdate).to(enddate);
        boolquery.must(termquery1)
                .must(termquery2)
//                .must(termquery3)
                .must(termquery4)
                .must(termquery5)
                .must(rangequery);

        SearchResponse response = null;

        if (keyword != null) {
            QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("title", keyword);
            boolquery.must(queryBuilder2);
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .setMinScore((float) 10)
                    .addSort("news_postdate", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        } else {
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .addSort("news_postdate", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        }


        SearchHits hits = response.getHits();
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        if (hits.totalHits > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> sourceData = new HashMap<String, Object>();
                sourceData = hit.getSourceAsMap();
                Map<String, String> map = new HashMap<String, String>();
                map.put("theme", theme);
                map.put("department", department);
                map.put("date", String.valueOf(sourceData.get("news_postdate")));
                map.put("url", String.valueOf(sourceData.get("news_url")));
                map.put("title", String.valueOf(sourceData.get("news_title")));
                map.put("read", String.valueOf(sourceData.get("news_read_count")));
                map.put("repost", String.valueOf(sourceData.get("news_reposts_count")));
                map.put("like", String.valueOf(sourceData.get("news_like_count")));
                String wx_id = String.valueOf(sourceData.get("wx_name"));
                map.putAll(getWeiXinIdInfo(wx_id));
                list.add(map);
            }
        }
        return list;
    }
}
