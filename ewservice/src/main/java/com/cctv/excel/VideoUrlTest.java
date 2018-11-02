package com.cctv.excel;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import java.util.HashMap;
import java.util.Map;

public class VideoUrlTest {
    public static void getWeiboFlowerByID(String weiboid) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("video_url", weiboid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("date", "2018-10-11");
        boolquery.must(termquery1)
        .must(termquery2);
        SearchResponse response = EsClientTools.getEsClient()
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(100)
                .get();

        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            System.out.println(hitmap);
        }
    }

    public static void main(String[] args) {
        getWeiboFlowerByID("None");
    }
}
