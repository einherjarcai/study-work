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

@Repository
public class ThemeDepartDao {
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
            idmap.put("type", String.valueOf(hitmap.get("type")));
            idmap.put("level", String.valueOf(hitmap.get("level")));
            idmap.put("channel", String.valueOf(hitmap.get("channel")));
        }
        return idmap;
    }


    public Map<String, String> getWeiBoIdInfo(String uid) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("UID", uid);
        boolquery.must(termquery1);
        SearchResponse response = client
                .prepareSearch("weibo_account_list")
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
    public List<Map<String,String>> getThemeData(String theme, String department, String startdate, String enddate, String keyword, int page, int size) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        /*if (theme != null) {
            QueryBuilder termquery1 = QueryBuilders.termQuery("theme", theme);
            boolquery.must(termquery1);
        }*/
        QueryBuilder termquery1 = QueryBuilders.termQuery("theme", theme);
        QueryBuilder termquery3 = QueryBuilders.termQuery("mold", "1");
        QueryBuilder termquery4 = QueryBuilders.termsQuery("type", "7", "8");
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("news_postdate").from(startdate).to(enddate);
        boolquery.must(termquery1)
                .must(termquery3)
                .must(termquery4)
                .must(rangequery);

        if (department != null) {
            QueryBuilder termquery2 = QueryBuilders.termQuery("department", department);
            boolquery.must(termquery2);
        }

        SearchResponse response = null;
        if (keyword != null) {
            QueryBuilder queryBuilder2 = QueryBuilders.matchPhraseQuery("title", keyword);
            boolquery.must(queryBuilder2);
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
//                    .setMinScore((float) 10)
                    .addSort("news_posttime", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setFrom(size*(page-1)).setSize(size).setExplain(true)
                    .get();
        } else {
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .addSort("news_posttime", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setFrom(size*(page-1)).setSize(size).setExplain(true)
                    .get();
        }

        SearchHits hits = response.getHits();
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
//        System.out.println("--------" + hits.totalHits);
        if (hits.totalHits > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> sourceData = new HashMap<String, Object>();
                sourceData = hit.getSourceAsMap();
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", String.valueOf(sourceData.get("app_name")));
                map.put("theme", theme);
                map.put("department", department);
                map.put("type", String.valueOf(sourceData.get("labelall")));
                map.put("channel", String.valueOf(sourceData.get("labelygg")));
                map.put("date", String.valueOf(sourceData.get("news_posttime")));
                map.put("url", String.valueOf(sourceData.get("news_url")));
                map.put("title", String.valueOf(sourceData.get("news_title")));
                String news_read_count = String.valueOf(sourceData.get("news_read_count"));
                news_read_count = news_read_count.substring(0, news_read_count.indexOf("."));
                String news_reposts_count = String.valueOf(sourceData.get("news_reposts_count"));
                news_reposts_count = news_reposts_count.substring(0, news_reposts_count.indexOf("."));
                String news_like_count = String.valueOf(sourceData.get("news_like_count"));
                news_like_count = news_like_count.substring(0, news_like_count.indexOf("."));
                String news_comment_count = String.valueOf(sourceData.get("news_comment_count"));
                news_comment_count = news_comment_count.substring(0, news_comment_count.indexOf("."));
                map.put("read", news_read_count);
                map.put("repost", news_reposts_count);
                map.put("like", news_like_count);
                map.put("comment", news_comment_count);
                /*String type = String.valueOf(sourceData.get("type"));
                if ("7".equals(type)) {
                    String uid = String.valueOf(sourceData.get("mblog_uid"));
                    map.putAll(getWeiBoIdInfo(uid));
                } else if ("8".equals(type)) {
                    String wx_id = String.valueOf(sourceData.get("wx_name"));
                    map.putAll(getWeiXinIdInfo(wx_id));
                }*/
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 获取主题总数
     * @param theme
     * @param department
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public int getThemeTotal(String theme, String department, String startdate, String enddate, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("theme", theme);
        QueryBuilder termquery3 = QueryBuilders.termQuery("level", "3");
        QueryBuilder termquery4 = QueryBuilders.termsQuery("type", "7", "8");
        QueryBuilder termquery5 = QueryBuilders.termQuery("mold", "1");
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("news_postdate").from(startdate).to(enddate);
        boolquery.must(termquery1)
//                .must(termquery3)
                .must(termquery4)
                .must(termquery5)
                .must(rangequery);
        if (department != null) {
            QueryBuilder termquery2 = QueryBuilders.termQuery("department", department);
            boolquery.must(termquery2);
        }

        SearchResponse response = null;

        if (keyword != null) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", keyword);
            boolquery.must(queryBuilder);
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
//                    .setMinScore((float) 10)
                    .addSort("news_posttime", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        } else {
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .addSort("news_posttime", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        }

        SearchHits hits = response.getHits();
        int count = (int)hits.getTotalHits();
        return count;
    }

    /**
     * 主题下载信息
     * Elasticsearch 每次查询最多返回10000条数据，建议时间范围不能过大
     * @param theme
     * @param department
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public List<Map<String,String>> getThemeDownLoadData(String theme, String department, String startdate, String enddate, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        /*if (theme != null) {
            QueryBuilder termquery1 = QueryBuilders.termQuery("theme", theme);
            boolquery.must(termquery1);
        }*/
        QueryBuilder termquery1 = QueryBuilders.termQuery("theme", theme);
        QueryBuilder termquery3 = QueryBuilders.termQuery("level", "3");
        QueryBuilder termquery4 = QueryBuilders.termsQuery("type", "7", "8");
        QueryBuilder termquery5 = QueryBuilders.termQuery("mold", "1");
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("news_postdate").from(startdate).to(enddate);
        boolquery.must(termquery1)
//                .must(termquery3)
                 .must(termquery4)
                .must(termquery5)
                .must(rangequery);
        if (department != null) {
            QueryBuilder termquery2 = QueryBuilders.termQuery("department", department);
            boolquery.must(termquery2);
        }

        SearchResponse response = null;

        if (keyword != null) {
            QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("title", keyword);
            boolquery.must(queryBuilder2);
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
//                    .setMinScore((float) 10)
                    .addSort("news_posttime", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        } else {
            response = client
                    .prepareSearch("match_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .addSort("news_posttime", SortOrder.DESC)
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
                map.put("name", String.valueOf(sourceData.get("app_name")));
                map.put("theme", theme);
                map.put("department", department);
                map.put("type", String.valueOf(sourceData.get("labelall")));
                map.put("channel", String.valueOf(sourceData.get("labelygg")));
                map.put("date", String.valueOf(sourceData.get("news_posttime")));
                map.put("url", String.valueOf(sourceData.get("news_url")));
                map.put("title", String.valueOf(sourceData.get("news_title")));
                String news_read_count = String.valueOf(sourceData.get("news_read_count"));
                news_read_count = news_read_count.substring(0, news_read_count.indexOf("."));
                String news_reposts_count = String.valueOf(sourceData.get("news_reposts_count"));
                news_reposts_count = news_reposts_count.substring(0, news_reposts_count.indexOf("."));
                String news_like_count = String.valueOf(sourceData.get("news_like_count"));
                news_like_count = news_like_count.substring(0, news_like_count.indexOf("."));
                String news_comment_count = String.valueOf(sourceData.get("news_comment_count"));
                news_comment_count = news_comment_count.substring(0, news_comment_count.indexOf("."));
                map.put("read", news_read_count);
                map.put("repost", news_reposts_count);
                map.put("like", news_like_count);
                map.put("comment", news_comment_count);
                /*String type = String.valueOf(sourceData.get("type"));
                if ("7".equals(type)) {
                    String uid = String.valueOf(sourceData.get("mblog_uid"));
                    map.putAll(getWeiBoIdInfo(uid));
                } else if ("8".equals(type)) {
                    String wx_id = String.valueOf(sourceData.get("wx_name"));
                    map.putAll(getWeiXinIdInfo(wx_id));
                }*/
                list.add(map);
            }
        }
        return list;
    }
}
