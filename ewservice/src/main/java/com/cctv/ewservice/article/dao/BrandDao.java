package com.cctv.ewservice.article.dao;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangcai
 * @Date Created in 2018/10/22
 */
@Repository
public class BrandDao {
    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    @PostConstruct
    public void init() {
        client = this.transportClient;
    }


    public List<Map<String,String>> getWeiXinIdList(String type, String channel) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();

        if ("1".equals(type)) {
            QueryBuilder termquery1 = QueryBuilders.termsQuery("type", "央视", "央广", "国广");
            boolquery.must(termquery1);

        } else {
            QueryBuilder termquery1 = QueryBuilders.termQuery("type", type);
            boolquery.must(termquery1);
        }

        if (!"17".equals(channel)) {
            if ("7".equals(channel)) {
                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-7军事", "CCTV-7农业");
                boolquery.must(termquery3);
            } else if ("20".equals(channel)) {
                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                        "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-7军事", "CCTV-7农业", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                        "CCTV-NEWS外语", "央视网");
                boolquery.must(termquery3);
            } else {
                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                boolquery.must(termquery3);
            }
        } else {
            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-7军事", "CCTV-7农业", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                    "CCTV-NEWS外语", "央视网");
            boolquery.must(termquery3);
        }

        SearchResponse response = client
                .prepareSearch("weixin_account_list")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();
        SearchHits hits = response.getHits();
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            Map<String, String> idmap = new HashMap<String, String>();
            idmap.put("name", String.valueOf(hitmap.get("nickname")));
            idmap.put("id", String.valueOf(hitmap.get("wx_id")));
            idmap.put("type", String.valueOf(hitmap.get("type")));
            idmap.put("channel", String.valueOf(hitmap.get("channel")));
            list.add(idmap);
        }
        return list;
    }


    public List<Map<String,String>> getWeiBoIdList(String type, String channel) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();

        if ("1".equals(type)) {
            QueryBuilder termquery1 = QueryBuilders.termsQuery("type", "央视", "央广", "国广");
            boolquery.must(termquery1);

        } else {
            QueryBuilder termquery1 = QueryBuilders.termQuery("type", type);
            boolquery.must(termquery1);
        }

        if (!"17".equals(channel)) {
            if ("20".equals(channel)) {
                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                        "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-7军事农业", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                        "外语频道", "央视网");
                boolquery.must(termquery3);
            } else {
                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                boolquery.must(termquery3);
            }
        } else {
            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-7军事农业", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                    "外语频道", "央视网");
            boolquery.must(termquery3);
        }

        SearchResponse response = client
                .prepareSearch("weibo_account_list")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();
        SearchHits hits = response.getHits();
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            Map<String, String> idmap = new HashMap<String, String>();
            idmap.put("name", String.valueOf(hitmap.get("nickname")));
            idmap.put("id", String.valueOf(hitmap.get("UID")));
            idmap.put("type", String.valueOf(hitmap.get("type")));
            idmap.put("channel", String.valueOf(hitmap.get("channel")));
            list.add(idmap);
        }
        return list;
    }

    public List<Map<String, String>> getWeiXinAllMsgidInfo(String weixinid, String startdate, String enddate, String keyword, Map<String, String> idmap) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("weixin_id", weixinid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "3");
        QueryBuilder termquery3 = QueryBuilders.matchPhraseQuery("title", keyword);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("pub_date").from(startdate).to(enddate);
        boolquery.must(termquery1)
                .must(termquery2)
                .must(termquery3)
                .must(rangequery);
        TermsAggregationBuilder msgidAgg = AggregationBuilders.terms("msgid").field("msgid").size(10000);

        SearchResponse response = client
                .prepareSearch("weixin_article_accu_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addSort("pub_date", SortOrder.ASC)
                .addAggregation(msgidAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();
        Terms term = response.getAggregations().get("msgid");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Terms.Bucket entry : term.getBuckets()) {
            String msgid = entry.getKeyAsString();
            Map<String, String> map = new HashMap<String, String>();
            map = getWeiXinArticleInfoByMsgid(msgid);
            map.put("brand", keyword);
            map.putAll(idmap);
            list.add(map);
        }
//        System.out.println(list);
        return list;
    }

    public Map<String, String> getWeiXinArticleInfoByMsgid(String msgid) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("msgid", msgid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "3");
        boolquery.must(termquery1)
                .must(termquery2);

        SearchResponse response = client
                .prepareSearch("weixin_article_accu_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addSort("date", SortOrder.DESC)
                .addSort("digital_of_level", SortOrder.DESC)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();
        SearchHits hits = response.getHits();
        Map<String, String> map = new HashMap<String, String>();

        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            String read = String.valueOf(hitmap.get("int_page_read_count"));
            String share = String.valueOf(hitmap.get("share_count"));
            String add = String.valueOf(hitmap.get("add_to_fav_count"));
            Map<String, String> qingboMap = new HashMap<String, String>();
            qingboMap = getQingBoWeiXinArticleLike(msgid);
            int like = Integer.valueOf(qingboMap.get("like"));
            int hd = like + Integer.valueOf(share) + Integer.valueOf(add);
            map.put("source", "微信");
            map.put("read", read);
            map.put("title", String.valueOf(hitmap.get("title")));
            map.put("hd", String.valueOf(hd));
            map.put("url", qingboMap.get("url"));
            map.put("videoNum", "-");
            map.put("pub_date", String.valueOf(hitmap.get("pub_date")));
            map.put("time", "-");
        }
        return map;
    }


    public Map<String, String> getQingBoWeiXinArticleLike(String msgid) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("msgid", msgid);
        boolquery.must(termquery1);
        SearchResponse response = client
                .prepareSearch("weixin_article_qingbo*")
                .setTypes("type")
                .setQuery(boolquery)
                .addSort("likenum_pm", SortOrder.DESC)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();
        SearchHits hits = response.getHits();
        Map<String, String> map = new HashMap<String, String>();
        if (hits.totalHits > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> hitmap = new HashMap<String, Object>();
                hitmap = hit.getSourceAsMap();
                /*if (isInteger(String.valueOf(hitmap.get("likenum_pm")))) {
                    like = Integer.valueOf(String.valueOf(hitmap.get("likenum_pm")));
                }*/
                if (String.valueOf(hitmap.get("likenum_pm")) != "null") {
                    map.put("like", String.valueOf(hitmap.get("likenum_pm")));
                } else {
                    map.put("like", "0");
                }
                if (String.valueOf(hitmap.get("url")) != "null") {
                    map.put("url", String.valueOf(hitmap.get("url")));
                } else {
                    map.put("url", "-");
                }
            }
        } else {
            map.put("like", "0");
            map.put("url", "-");
        }
        return map;
    }


    public List<Map<String, String>> getWeiBoArticleMid(String weiboid, String startdate, String enddate, String keyword, Map<String, String> idmap) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("uid", weiboid);
        QueryBuilder termquery2 = QueryBuilders.matchPhraseQuery("weibo_text", keyword);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("created_time").from(startdate + " 00:00:00").to(enddate + " 23:59:59");
        boolquery.must(termquery1)
                .must(termquery2)
                .must(rangequery);

        TermsAggregationBuilder midAgg = AggregationBuilders.terms("mid").field("mid").size(1000);

        SearchResponse response = client
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addSort("created_time", SortOrder.ASC)
                .addAggregation(midAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        Terms term = response.getAggregations().get("mid");
        for (Terms.Bucket entry : term.getBuckets()) {
            String mid = entry.getKeyAsString();
//            System.out.println(mid);
            Map<String, String> map = new HashMap<String, String>();
            map = getWeiBoLatestInfo(mid);
            map.put("brand", keyword);
            map.putAll(idmap);
            list.add(map);
        }
        return list;
    }

    public Map<String, String> getWeiBoLatestInfo(String mid) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("mid", mid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "2");
        boolquery.must(termquery1)
                .must(termquery2);

        SearchResponse response = client
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addSort("date", SortOrder.DESC)
                .addSort("digital_of_level", SortOrder.DESC)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();

        SearchHits hits = response.getHits();
        Map<String, String> map = new HashMap<String, String>();
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            String repost = String.valueOf(hitmap.get("reposts_accumulation"));
            String comment = String.valueOf(hitmap.get("comments_accumulation"));
            String like = String.valueOf(hitmap.get("attitudes_accumulation"));
            String read = String.valueOf(hitmap.get("reads_accumulation"));
            String live = String.valueOf(hitmap.get("live_play_accumulation"));
            String video = String.valueOf(hitmap.get("video_play_accumulation"));
            String date = String.valueOf(hitmap.get("created_time"));

            int videoNum = Integer.valueOf(live) + Integer.valueOf(video);
            int hd = Integer.valueOf(repost) + Integer.valueOf(comment) + Integer.valueOf(like);

            map.put("source", "微博");
            map.put("read", read);
            map.put("videoNum", String.valueOf(videoNum));
            map.put("hd", String.valueOf(hd));
            map.put("pub_date", date.substring(0, date.indexOf(" ")));
            map.put("time", date.substring(date.indexOf(" ") + 1, date.length()));
            map.put("date", date);
            map.put("url", String.valueOf(hitmap.get("weibo_url")));
            map.put("title", String.valueOf(hitmap.get("weibo_text")));
        }
        return map;
    }

}
