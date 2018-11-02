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
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPlatFormQingBoMatch {

    public static void getWeiXinArticleMsgid(String startdate, String enddate) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("date").from(startdate).to(enddate);
        boolquery.must(rangequery);
//                .must(termquery2)
//                .must(rangequery);

        TermsAggregationBuilder msgidAgg = AggregationBuilders.terms("msgid").field("msgid").size(10000);
        SearchResponse response = null;
        response = EsClientTools.getEsClient()
                .prepareSearch("weixin_article_accu_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addAggregation(msgidAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();

        SearchHits hits = response.getHits();
        Terms term = response.getAggregations().get("msgid");
        List<String> list = new ArrayList<String>();
        System.out.println(term.getBuckets().size());
        for (Terms.Bucket entry : term.getBuckets()) {
            String msgid = entry.getKeyAsString();
            list.add(msgid);
        }
        getQingBoWeiXinArticleLike(list);
    }

    public static void getQingBoWeiXinArticleLike(List<String> msgid) {
        int i = 0;
        for (String s: msgid) {
            BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
            QueryBuilder termquery1 = QueryBuilders.termQuery("msgid", s);
            boolquery.must(termquery1);
            SearchResponse response = EsClientTools.getEsClient()
                    .prepareSearch("weixin_article_qingbo*")
                    .setTypes("type")
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
        getWeiXinArticleMsgid("2018-09-20", "2018-10-11");
    }
}
