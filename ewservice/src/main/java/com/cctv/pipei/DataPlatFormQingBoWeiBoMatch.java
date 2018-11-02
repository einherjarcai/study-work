package com.cctv.pipei;

import com.cctv.excel.EsClientTools;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPlatFormQingBoWeiBoMatch {
    public static void getWeiBoArticleMid(String startdate, String enddate) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("created_time").from(startdate + " 00:00:00").to(enddate + " 23:59:59");
//        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("date").from(startdate).to(enddate);
        boolquery.must(rangequery);
        TermsAggregationBuilder midAgg = AggregationBuilders.terms("mid").field("mid").size(1000000);
        SearchResponse response = null;
        response = EsClientTools.getEsClient()
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addAggregation(midAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();
        System.out.println(response.getHits().getTotalHits());
        List<String> list = new ArrayList<String>();

        Terms term = response.getAggregations().get("mid");
        System.out.println( term.getBuckets().size());
        for (Terms.Bucket entry : term.getBuckets()) {
            String mid = entry.getKeyAsString();
            list.add(mid);
        }
        getQingBoWeiBoArticleInfo(list);
    }

    public static void getQingBoWeiBoArticleInfo(List<String> mid) {
        int i = 0;
        for (String s : mid) {
            BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
            QueryBuilder termquery1 = QueryBuilders.termQuery("mid", s);
            boolquery.must(termquery1);
            SearchResponse response = null;
            response = EsClientTools.getEsClient()
                    .prepareSearch("weibo_article_qingbo*")
                    .setTypes("type")
//                    .setMinScore((float) 10)
                    .setQuery(boolquery)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(1)
                    .get();

            SearchHits hits = response.getHits();
            if (hits.totalHits > 0) {
                i += 1;
            }
        }
        System.out.println(i);
    }

    public static void main(String[] args) {
        getWeiBoArticleMid("2018-09-20", "2018-10-11");
    }
}
