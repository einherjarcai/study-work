package com.cctv.ewservice.article.dao;

import com.cctv.ewservice.common.CommonDateFunction;
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
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
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
 * @author wangcai
 * @program ewservice
 * @description
 * @create 2018-11-16 11:49
 */
@Repository
public class BrandDepartDayDao {
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

    private List<String> msgList = new ArrayList<String>();

    public void setMsgMidListToNull() {
        msgList.clear();
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
        int read = 0;
        int share = 0;
        int hd = 0;
        if (msgList.size() > 0) {
            List<Integer> yes_list = new ArrayList<Integer>();
            yes_list = getNowMsgData(startdate);
            read = yes_list.get(0);
            share = yes_list.get(1);
            hd = yes_list.get(2);
        }

        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termsQuery("weixin_id", weixinid);
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
        List<Integer> list = new ArrayList<Integer>();
        int count = term.getBuckets().size();

        for (Terms.Bucket entry : term.getBuckets()) {
            String msgid = entry.getKeyAsString();
            msgList.add(msgid);
            List<Integer> indexlist = new ArrayList<Integer>();
            indexlist = getWeiXinAccuArticleInfoByMsgid(msgid, startdate, enddate);
            read += indexlist.get(0);
            share += indexlist.get(1);
            hd += indexlist.get(2);
        }
        list.add(count);
        list.add(read);
        list.add(share);
        list.add(hd);
        return list;
    }

    public void getSevenDayMsgid(List<String> weixinid, String startdate, String enddate, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termsQuery("weixin_id", weixinid);
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

        for (Terms.Bucket entry : term.getBuckets()) {
            String msgid = entry.getKeyAsString();
            msgList.add(msgid);
        }

    }

    public List<Integer> getNowMsgData(String date) {
        int readCount = 0;
        int shareCount = 0;
        int addCount = 0;
        List<String> msg = new ArrayList<String>();
        for (String s : msgList) {
            BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
            QueryBuilder termquery1 = QueryBuilders.termQuery("msgid", s);
            QueryBuilder termquery2 = QueryBuilders.termQuery("date", date);

            boolquery.must(termquery1)
                    .must(termquery2);

            SearchResponse response = client
                    .prepareSearch("weixin_article_accu_platform*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(1)
                    .get();
            SearchHits hits = response.getHits();
            if (hits.getTotalHits() > 0) {
                msg.add(s);
                for (SearchHit hit : hits) {
                    Map<String, Object> hitmap = new HashMap<String, Object>();
                    hitmap = hit.getSourceAsMap();
                    String read = String.valueOf(hitmap.get("int_page_read_count"));
                    String share = String.valueOf(hitmap.get("share_count"));
                    String add = String.valueOf(hitmap.get("add_to_fav_count"));
                    int read_count = Integer.valueOf(read);
                    int share_count = Integer.valueOf(share);
                    int add_count = Integer.valueOf(add);
                    if (read_count > 0) {
                        String before_day = CommonDateFunction.convertCircleToBFStartDate(0, date);
                        List<Integer> list = new ArrayList<Integer>();
                        list = getYesMsgData(before_day, s);
                        read_count -= list.get(0);
                        share_count -= list.get(1);
                        add_count -= list.get(2);
                    }
                    if (read_count < 0) {
                        read_count = 0;
                    }
                    if (share_count < 0) {
                        share_count = 0;
                    }
                    if (add_count < 0) {
                        add_count = 0;
                    }
                    readCount += read_count;
                    shareCount += share_count;
                    addCount += add_count;
                }
            }
        }
//        msgList = msg;
        List<Integer> resultlist = new ArrayList<Integer>();
        resultlist.add(readCount);
        resultlist.add(shareCount);
        resultlist.add(addCount + shareCount);
        return resultlist;
    }

    public List<Integer> getYesMsgData(String date, String msgid) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("msgid", msgid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("date", date);

        boolquery.must(termquery1)
                .must(termquery2);

        SearchResponse response = client
                .prepareSearch("weixin_article_accu_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();
        SearchHits hits = response.getHits();
        int readCount = 0;
        int shareCount = 0;
        int addCount = 0;
        List<Integer> list = new ArrayList<Integer>();
        if (hits.getTotalHits() > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> hitmap = new HashMap<String, Object>();
                hitmap = hit.getSourceAsMap();
                String read = String.valueOf(hitmap.get("int_page_read_count"));
                String share = String.valueOf(hitmap.get("share_count"));
                String add = String.valueOf(hitmap.get("add_to_fav_count"));
                readCount = Integer.valueOf(read);
                shareCount = Integer.valueOf(share);
                addCount = Integer.valueOf(add);
            }
        }
        list.add(readCount);
        list.add(shareCount);
        list.add(addCount);
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
        int shareCount = 0;
        int hd = 0;
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            String read = String.valueOf(hitmap.get("int_page_read_count"));
            String share = String.valueOf(hitmap.get("share_count"));
            String add = String.valueOf(hitmap.get("add_to_fav_count"));

            readCount = Integer.valueOf(read);
            shareCount = Integer.valueOf(share);
            int addCount = Integer.valueOf(add);
            int like = getQingBoWeiXinArticleLike(msgid);
            hd = like + shareCount + addCount;
        }
        list.add(readCount);
        list.add(shareCount);
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
                .addSort("likenum_newest", SortOrder.DESC)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();
        SearchHits hits = response.getHits();
        int like = 0;
        if (hits.totalHits > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> hitmap = new HashMap<String, Object>();
                hitmap = hit.getSourceAsMap();
                String likenum = String.valueOf(hitmap.get("likenum_newest"));
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

        QueryBuilder queryBuilder2 = QueryBuilders.matchPhraseQuery("title", str);
        boolquery.must(queryBuilder2);
        SearchResponse response = client
                .prepareSearch("weixin_article_qingbo*")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();

        SearchHits hits = response.getHits();
        List<Integer> list = new ArrayList<Integer>();
        int readCount = 0;
        int shareCount = 0;
        int hd = 0;
        int count = (int)hits.getTotalHits();
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            String read = String.valueOf(hitmap.get("readnum_newest"));
            String like = String.valueOf(hitmap.get("likenum_newest"));
            readCount += Integer.valueOf(read);
            hd += Integer.valueOf(like);
        }
        list.add(count);
        list.add(readCount);
        list.add(shareCount);
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
    /*public List<Integer> getWeiBoAccuAllMsgidInfo(List<String> weiboid, String startdate, String enddate, String keyword) {
        int read = 0;
        int share = 0;
        int hd = 0;
        int video = 0;
        if (midList.size() > 0) {
            List<Integer> yes_list = new ArrayList<Integer>();
            yes_list = getNowMidData(startdate);
            read = yes_list.get(0);
            share = yes_list.get(1);
            hd = yes_list.get(2);
            video = yes_list.get(3);
        }

        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termsQuery("uid", weiboid);

        QueryBuilder termquery2 = QueryBuilders.matchPhraseQuery("weibo_text", keyword);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("created_time").from(startdate + " 00:00:00").to(enddate + " 23:59:59");
        boolquery.must(termquery1)
                .must(termquery2)
                .must(rangequery);

        TermsAggregationBuilder midAgg = AggregationBuilders.terms("mid").field("mid").size(10000);

        SearchResponse response = client
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addSort("created_time", SortOrder.ASC)
                .addAggregation(midAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();
        Terms term = response.getAggregations().get("mid");

        List<Integer> list = new ArrayList<Integer>();
        int count = term.getBuckets().size();

        for (Terms.Bucket entry : term.getBuckets()) {
            String msgid = entry.getKeyAsString();
            midList.add(msgid);
            List<Integer> indexlist = new ArrayList<Integer>();
            indexlist = getWeiBoLatestInfo(msgid, startdate, enddate);
            read += indexlist.get(0);
            share += indexlist.get(1);
            hd += indexlist.get(2);
            video += indexlist.get(3);
        }
        list.add(count);
        list.add(read);
        list.add(share);
        list.add(hd);
        list.add(video);
        return list;
    }*/


    /**
     * 大数据所有微博账号对应的文章指标
     * @param weiboid
     * @param date
     * @param keyword
     * @return
     */
    public List<Integer> getWeiBoBrandArticleInfo(List<String> weiboid, String date, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termsQuery("uid", weiboid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "2");
        QueryBuilder termquery3 = QueryBuilders.termQuery("date", date);
        QueryBuilder termquery4 = QueryBuilders.matchPhraseQuery("weibo_text", keyword);
        String ten_before_day = CommonDateFunction.convertCircleToBFStartDate(10, date);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("created_time").from(ten_before_day + " 00:00:00").to(date + " 23:59:59");
        boolquery.must(termquery1)
                .must(termquery2)
                .must(termquery3)
                .must(termquery4)
                .must(rangequery);

        TermsAggregationBuilder midAgg = AggregationBuilders.terms("mid").field("mid").size(1000);

        SearchResponse response = client
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addAggregation(midAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();

        int read = 0;
        int share = 0;
        int hd = 0;
        int video = 0;

        Terms term = response.getAggregations().get("mid");

        for (Terms.Bucket entry : term.getBuckets()) {
            String mid = entry.getKeyAsString();
            List<Integer> list = new ArrayList<Integer>();
            list = getWeiBoArticleLatestInfo(mid, date);
            read += list.get(0);
            share += list.get(1);
            hd += list.get(2);
            video += list.get(3);
        }
        List<Integer> index = new ArrayList<Integer>();
        int count = getWeiBoArticleCount(weiboid, date, keyword);
        index.add(count);
        index.add(read);
        index.add(share);
        index.add(hd);
        index.add(video);
        return index;
    }

    /**
     * 当天的品牌微博文章数
     * @param weiboid
     * @param date
     * @param keyword
     * @return
     */
    public int getWeiBoArticleCount(List<String> weiboid, String date, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termsQuery("uid", weiboid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "2");
        QueryBuilder termquery3 = QueryBuilders.matchPhraseQuery("weibo_text", keyword);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("created_time").from(date + " 00:00:00").to(date + " 23:59:59");
        boolquery.must(termquery1)
                .must(termquery2)
                .must(termquery3)
                .must(rangequery);

        TermsAggregationBuilder midAgg = AggregationBuilders.terms("mid").field("mid").size(1000);

        SearchResponse response = client
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addAggregation(midAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();

        Terms term = response.getAggregations().get("mid");
        int count = term.getBuckets().size();
        return count;
    }


    /**
     * 获取周期时间统计的微博品牌文章数据
     * @param mid
     * @return
     */
    public List<Integer> getWeiBoArticleLatestInfo(String mid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("mid", mid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "2");
        QueryBuilder termquery3 = QueryBuilders.termQuery("date", date);
        boolquery.must(termquery1)
                .must(termquery2)
                .must(termquery3);

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
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            String create_time = String.valueOf(hitmap.get("created_time"));
            String pub_date = String.valueOf(hitmap.get("created_time"));
            pub_date = pub_date.substring(0, pub_date.indexOf(" "));
            int read = Integer.valueOf(String.valueOf(hitmap.get("reads_accumulation")));
            int like = Integer.valueOf(String.valueOf(hitmap.get("attitudes_accumulation")));
            int comment = Integer.valueOf(String.valueOf(hitmap.get("comments_accumulation")));
            int repost = Integer.valueOf(String.valueOf(hitmap.get("reposts_accumulation")));
            int live = Integer.valueOf(String.valueOf(hitmap.get("live_play_accumulation")));
            int video = Integer.valueOf(String.valueOf(hitmap.get("video_play_accumulation")));
            String videoUrl = String.valueOf(hitmap.get("video_url"));
            /*if ("None".equals(videoUrl)) {
                live = 0;
                video = 0;
            }*/
            if (live > 0 || video > 0) {
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
            int videoNum = live + video;
            int all = like + comment + repost;
            if (!pub_date.equals(date)) {
                if (read > 0) {
                    List<Integer> yes_list = new ArrayList<Integer>();
                    yes_list = getYesDataInfo(mid, date);
                    if (yes_list.size() > 0) {
                        int yes_read = yes_list.get(0);
                        int yes_share = yes_list.get(1);
                        int yes_all = yes_list.get(2);
                        int yes_videoNum = yes_list.get(3);
                        read = read - yes_read;
                        repost = repost - yes_share;
                        if (videoNum > 0) {
                            videoNum = videoNum - yes_videoNum;
                        }
                        all = all - yes_all;
                    }
                }
            }
            if (read < 0) {
                read = 0;
            }
            if (repost < 0) {
                repost = 0;
            }
            if (videoNum < 0) {
                videoNum = 0;
            }
            if (all < 0) {
                all = 0;
            }
            list.add(read);
            list.add(repost);
            list.add(all);
            list.add(videoNum);
        }
        return list;
    }


    /**
     * 获取不是当天发布微博品牌文章的上一次统计的阅读数
     * @param mid
     * @param date
     * @return
     */
    public List<Integer> getYesDataInfo(String mid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("mid", mid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "2");
//        QueryBuilder termquery3 = QueryBuilders.termQuery("date", date);
        String ten_before_day = CommonDateFunction.convertCircleToBFStartDate(10, date);
        String before_day = CommonDateFunction.convertCircleToBFStartDate(0, date);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("date").from(ten_before_day).to(before_day);
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
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            int read = Integer.valueOf(String.valueOf(hitmap.get("reads_accumulation")));
            int like = Integer.valueOf(String.valueOf(hitmap.get("attitudes_accumulation")));
            int comment = Integer.valueOf(String.valueOf(hitmap.get("comments_accumulation")));
            int repost = Integer.valueOf(String.valueOf(hitmap.get("reposts_accumulation")));
            int live = Integer.valueOf(String.valueOf(hitmap.get("live_play_accumulation")));
            int video = Integer.valueOf(String.valueOf(hitmap.get("video_play_accumulation")));
            int all = like + comment + repost;
            int videoNum = video + live;
            list.add(read);
            list.add(repost);
            list.add(all);
            list.add(videoNum);
        }
        return list;
    }


    /**
     * 查询开始日期前10天内所有满足条件的微博mid
     * @param weiboid
     * @param startdate
     * @param enddate
     * @param keyword
     */
    /*public void getTenDayAllMid(List<String> weiboid, String startdate, String enddate, String keyword) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termsQuery("uid", weiboid);

        QueryBuilder termquery2 = QueryBuilders.matchPhraseQuery("weibo_text", keyword);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("created_time").from(startdate + " 00:00:00").to(enddate + " 23:59:59");
        boolquery.must(termquery1)
                .must(termquery2)
                .must(rangequery);

        TermsAggregationBuilder midAgg = AggregationBuilders.terms("mid").field("mid").size(10000);

        SearchResponse response = client
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addSort("created_time", SortOrder.ASC)
                .addAggregation(midAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();
        Terms term = response.getAggregations().get("mid");

        for (Terms.Bucket entry : term.getBuckets()) {
            String msgid = entry.getKeyAsString();
            midList.add(msgid);
        }
    }


    public List<Integer> getNowMidData(String date) {
        int readCount = 0;
        int shareCount = 0;
        int hd = 0;
        int videoNum = 0;
        List<String> mid = new ArrayList<String>();
        for (String s : midList) {
            BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
            QueryBuilder termquery1 = QueryBuilders.termQuery("mid", s);
            QueryBuilder termquery2 = QueryBuilders.termQuery("level", "2");
            QueryBuilder termquery3 = QueryBuilders.termQuery("date", date);

            boolquery.must(termquery1)
                    .must(termquery2)
                    .must(termquery3);

            SearchResponse response = client
                    .prepareSearch("weibo_article_platform*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .addSort("digital_of_level", SortOrder.DESC)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(1)
                    .get();
            SearchHits hits = response.getHits();
            if (hits.getTotalHits() > 0) {
                mid.add(s);
                for (SearchHit hit : hits) {
                    Map<String, Object> hitmap = new HashMap<String, Object>();
                    hitmap = hit.getSourceAsMap();
                    String repost = String.valueOf(hitmap.get("reposts_accumulation"));
                    String comment = String.valueOf(hitmap.get("comments_accumulation"));
                    String like = String.valueOf(hitmap.get("attitudes_accumulation"));
                    String read = String.valueOf(hitmap.get("reads_accumulation"));
                    int live = Integer.valueOf(String.valueOf(hitmap.get("live_play_accumulation")));
                    int video = Integer.valueOf(String.valueOf(hitmap.get("video_play_accumulation")));

                    int read_count = Integer.valueOf(read);
                    int share_ount = Integer.valueOf(repost);
                    int hd_count = Integer.valueOf(repost) + Integer.valueOf(comment) + Integer.valueOf(like);

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
                    int video_num = (live + video);

                    if (read_count > 0) {
                        List<Integer> list = new ArrayList<Integer>();
                        list = getYesMidData(date, s);
                        read_count -= list.get(0);
                        share_ount -= list.get(1);
                        hd_count -= list.get(2);
                        video_num -= list.get(3);
                    }
                    if (read_count < 0) {
                        read_count = 0;
                    }
                    if (share_ount < 0) {
                        share_ount = 0;
                    }
                    if (hd_count < 0) {
                        hd_count = 0;
                    }
                    if (video_num < 0) {
                        video_num = 0;
                    }
                    readCount += read_count;
                    shareCount += share_ount;
                    hd += hd_count;
                    videoNum += video_num;
                }
            }
        }
//        midList = mid;
        List<Integer> resultlist = new ArrayList<Integer>();
        resultlist.add(readCount);
        resultlist.add(shareCount);
        resultlist.add(hd);
        resultlist.add(videoNum);
        return resultlist;
    }

    public List<Integer> getYesMidData(String date, String msgid) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("mid", msgid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "2");
//        QueryBuilder termquery3 = QueryBuilders.termQuery("date", date);
        String ten_before_day = CommonDateFunction.convertCircleToBFStartDate(10, date);
        String before_day = CommonDateFunction.convertCircleToBFStartDate(0, date);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("date").from(ten_before_day).to(before_day);

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
        int readCount = 0;
        int shareCount = 0;
        int hd = 0;
        int videoNum = 0;
        List<Integer> list = new ArrayList<Integer>();
        if (hits.getTotalHits() > 0) {
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
                shareCount = Integer.valueOf(repost);
                hd = Integer.valueOf(repost) + Integer.valueOf(comment) + Integer.valueOf(like);
                videoNum = live + video;
            }
        }
        list.add(readCount);
        list.add(shareCount);
        list.add(hd);
        list.add(videoNum);
        return list;
    }*/



    /**
     * 返回单个文章的微博指标
     * @param mid
     * @param startdate
     * @param enddate
     * @return
     */
    /*public List<Integer> getWeiBoLatestInfo(String mid, String startdate, String enddate) {
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
        int shareCount = 0;
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
            shareCount = Integer.valueOf(repost);
            videoNum = live + video;
            hd = Integer.valueOf(repost) + Integer.valueOf(comment) + Integer.valueOf(like);
        }
        list.add(readCount);
        list.add(shareCount);
        list.add(hd);
        list.add(videoNum);
        return list;
    }*/


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

        QueryBuilder queryBuilder2 = QueryBuilders.matchPhraseQuery("content", keyword);
        boolquery.must(queryBuilder2);
        SearchResponse response = client
                .prepareSearch("weibo_article_qingbo*")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();

        SearchHits hits = response.getHits();
        List<Integer> list = new ArrayList<Integer>();
        int readCount = 0;
        int shareCount = 0;
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
            shareCount += Integer.valueOf(repost);
        }
        list.add(count);
        list.add(readCount);
        list.add(shareCount);
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
