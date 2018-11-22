package com.cctv.ewservice.article.dao;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author        wangcai
 * @program       ewservice
 * @description   批量查询累计数据
 * @create        2018-11-20 13:41
 */
@Repository
public class BatchQueryAccuDao {
    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    @PostConstruct
    public void init() {
        client = this.transportClient;
    }

    public Boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<String> getWeiXinIdList(String type, String channel) {
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
            } else if (!"20".equals(channel)) {
                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                boolquery.must(termquery3);
            }
        }
        SearchResponse response = client
                .prepareSearch("weixin_account_list")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();
        SearchHits hits = response.getHits();
        List<String> list = new ArrayList<String>();
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            list.add(String.valueOf(hitmap.get("wx_id")));
        }
        return list;
    }

    public List<String> getWeiBoIdList(String type, String channel) {
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

            } else {
                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                boolquery.must(termquery3);
            }
        }

        SearchResponse response = client
                .prepareSearch("weibo_account_list")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();
        SearchHits hits = response.getHits();
        List<String> list = new ArrayList<String>();
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            list.add(String.valueOf(hitmap.get("UID")));
        }
        return list;
    }

    /**
     * 返回大数据微信账号的所有数据
     * @param weixinid
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public List<Integer> getWeiXinAccuAllMsgidInfo(List<String> weixinid, String startdate, String enddate, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termsQuery("weixin_id", weixinid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "3");

        QueryBuilder termquery3 = QueryBuilders.matchQuery("title", keyword);
        ((MatchQueryBuilder) termquery3).minimumShouldMatch("100%");
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
                .setMinScore(10)
                .addSort("pub_date", SortOrder.ASC)
                .addAggregation(msgidAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();
        Terms term = response.getAggregations().get("msgid");
        List<Integer> list = new ArrayList<Integer>();
        int count = term.getBuckets().size();
        list.add(count);
        int read = 0;
        int hd = 0;
        for (Terms.Bucket entry : term.getBuckets()) {
            String msgid = entry.getKeyAsString();
            List<Integer> indexlist = new ArrayList<Integer>();
            indexlist = getWeiXinAccuArticleInfoByMsgid(msgid, startdate, enddate);
            read += indexlist.get(0);
            hd += indexlist.get(1);
        }
        list.add(read);
        list.add(hd);
        return list;
    }

    /**
     * 返回单个文章的微信指标
     * 查询周期内
     * @param msgid
     * @param startdate
     * @param enddate
     * @return
     */
    public List<Integer> getWeiXinAccuArticleInfoByMsgid(String msgid, String startdate, String enddate) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("msgid", msgid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "3");
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("date").from(startdate).to(enddate);

        boolquery.must(termquery1)
                .must(termquery2)
                .must(rangequery);

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
        List<Integer> list = new ArrayList<Integer>();
        int readCount = 0;
        int hd = 0;
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            String read = String.valueOf(hitmap.get("int_page_read_count"));
            String share = String.valueOf(hitmap.get("share_count"));
            String add = String.valueOf(hitmap.get("add_to_fav_count"));

            readCount = Integer.valueOf(read);
            int shareCount = Integer.valueOf(share);
            int addCount = Integer.valueOf(add);
            int like = getQingBoWeiXinArticleLike(msgid);
            hd = like + shareCount + addCount;
        }
        list.add(readCount);
        list.add(hd);
        return list;
    }

    /**
     * 返回清博对应的微信文章的点赞数
     * @param msgid
     * @return
     */
    public int getQingBoWeiXinArticleLike(String msgid) {
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
        int like = 0;
        if (hits.totalHits > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> hitmap = new HashMap<String, Object>();
                hitmap = hit.getSourceAsMap();
                String likenum = String.valueOf(hitmap.get("likenum_pm"));
                if (isInteger(likenum)) {
                    like = Integer.valueOf(likenum);
                }
            }
        }
        return like;
    }


    /**
     * 清博账号对应的微信指标
     * @param weixinid
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public List<Integer> getWeiXinQingBoArticleInfo(List<String> weixinid, String startdate, String enddate, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termsQuery("wx_name", weixinid);

        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("date").from(startdate).to(enddate);
        boolquery.must(termquery1)
                .must(rangequery);

        String str = keyword;
        String pattern = "\\(.*?\\)|（.*?）";
        str = str.replaceAll(pattern, " ");
        String regEx = "[`~☆★!@#$%^&*()+=|{}':;,\\[\\]》·.<>/?~！@#￥%……（）——+|{}【】‘；：”“’。，、？丨]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        str = m.replaceAll(" ").trim().replace(" ", " ").replace("\\", " ");

        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("title", str);
        ((MatchQueryBuilder) queryBuilder2).minimumShouldMatch("100%");
        boolquery.must(queryBuilder2);
        SearchResponse response = client
                .prepareSearch("weixin_article_qingbo*")
                .setTypes("type")
                .setQuery(boolquery)
                .setMinScore(10)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();

        SearchHits hits = response.getHits();
        List<Integer> list = new ArrayList<Integer>();
        int readCount = 0;
        int hd = 0;
        int count = (int)hits.getTotalHits();
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            String read = String.valueOf(hitmap.get("readnum_pm"));
            String like = String.valueOf(hitmap.get("likenum_pm"));
            readCount += Integer.valueOf(read);
            hd += Integer.valueOf(like);
        }
        list.add(count);
        list.add(readCount);
        list.add(hd);
        return list;
    }


    /**
     * 大数据所有微博账号对应的文章指标
     * @param weiboid
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public List<Integer> getWeiBoAccuAllMsgidInfo(List<String> weiboid, String startdate, String enddate, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termsQuery("uid", weiboid);

//        QueryBuilder termquery2 = QueryBuilders.matchPhraseQuery("weibo_text", keyword);
        QueryBuilder termquery2 = QueryBuilders.matchQuery("weibo_text", keyword);
        ((MatchQueryBuilder) termquery2).minimumShouldMatch("100%");
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("created_time").from(startdate + " 00:00:00").to(enddate + " 23:59:59");
        boolquery.must(termquery1)
                .must(termquery2)
                .must(rangequery);

        TermsAggregationBuilder midAgg = AggregationBuilders.terms("mid").field("mid").size(10000);

        SearchResponse response = client
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .setMinScore(10)
                .addSort("created_time", SortOrder.ASC)
                .addAggregation(midAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();
        Terms term = response.getAggregations().get("mid");

        List<Integer> list = new ArrayList<Integer>();
        int count = term.getBuckets().size();
        list.add(count);
        int read = 0;
        int hd = 0;
        int video = 0;
        for (Terms.Bucket entry : term.getBuckets()) {
            String msgid = entry.getKeyAsString();
            List<Integer> indexlist = new ArrayList<Integer>();
            indexlist = getWeiBoLatestInfo(msgid, startdate, enddate);
            read += indexlist.get(0);
            hd += indexlist.get(1);
            video += indexlist.get(2);
        }
        list.add(read);
        list.add(hd);
        list.add(video);
        return list;
    }

    /**
     * 返回单个文章的微博指标
     * @param mid
     * @param startdate
     * @param enddate
     * @return
     */
    public List<Integer> getWeiBoLatestInfo(String mid, String startdate, String enddate) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("mid", mid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "2");
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("date").from(startdate).to(enddate);
        boolquery.must(termquery1)
                .must(termquery2)
                .must(rangequery);

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
        List<Integer> list = new ArrayList<Integer>();
        int readCount = 0;
        int videoNum = 0;
        int hd = 0;
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            String repost = String.valueOf(hitmap.get("reposts_accumulation"));
            String comment = String.valueOf(hitmap.get("comments_accumulation"));
            String like = String.valueOf(hitmap.get("attitudes_accumulation"));
            String read = String.valueOf(hitmap.get("reads_accumulation"));
            int live = Integer.valueOf(String.valueOf(hitmap.get("live_play_accumulation")));
            int video = Integer.valueOf(String.valueOf(hitmap.get("video_play_accumulation")));
            String create_time = String.valueOf(hitmap.get("created_time"));

            if (live > 0 || video > 0) {
                String videoUrl = String.valueOf(hitmap.get("video_url"));
                if (!"None".equals(videoUrl)) {
                    String first_time = getVideoArticleDate(videoUrl);
                    if (first_time != null) {
                        if (!create_time.equals(first_time)) {
                            if (live > 0) {
                                live = 0;
                            } else if (video > 0) {
                                video = 0;
                            }
                        }
                    }
                }
            }
            readCount = Integer.valueOf(read);
            videoNum = live + video;
            hd = Integer.valueOf(repost) + Integer.valueOf(comment) + Integer.valueOf(like);
        }
        list.add(readCount);
        list.add(hd);
        list.add(videoNum);
        return list;
    }


    /**
     * 返回清博的微博指标
     * @param weiboid
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public List<Integer> getQingBoWeiBoArticleInfo(List<String> weiboid, String startdate, String enddate, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("uid", weiboid);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("create_time").from(startdate + " 00:00:00").to(enddate + " 23:59:59");
        boolquery.must(termquery1)
                .must(rangequery);

        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("content", keyword);
        ((MatchQueryBuilder) queryBuilder2).minimumShouldMatch("100%");
        boolquery.must(queryBuilder2);
        SearchResponse response = client
                .prepareSearch("weibo_article_qingbo*")
                .setTypes("type")
                .setQuery(boolquery)
                .setMinScore(10)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();

        SearchHits hits = response.getHits();
        List<Integer> list = new ArrayList<Integer>();
        int readCount = 0;
        int hd = 0;
        int count = (int)hits.getTotalHits();
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            String read = String.valueOf(hitmap.get("read_count"));
            String like = String.valueOf(hitmap.get("attitudes_count"));
            String comment = String.valueOf(hitmap.get("comments_count"));
            String repost = String.valueOf(hitmap.get("reposts_count"));

            hd += Integer.valueOf(repost) + Integer.valueOf(comment) + Integer.valueOf(like);
            readCount += Integer.valueOf(read);
        }
        list.add(count);
        list.add(readCount);
        list.add(hd);
        list.add(0);
        return list;
    }

    /**
     * 返回 video_url 对应的第一篇微博文章的创建时间
     * @param url
     * @return
     */
    public String getVideoArticleDate(String url) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("video_url", url);
        boolquery.must(termquery1);
        SearchResponse response = client
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addSort("created_time", SortOrder.ASC)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();
        SearchHits hits = response.getHits();
        String create_date = null;
        if (hits.getTotalHits() > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> hitmap = new HashMap<String, Object>();
                hitmap = hit.getSourceAsMap();
                create_date = String.valueOf(hitmap.get("created_time"));
            }
        }
        return create_date;
    }
}
