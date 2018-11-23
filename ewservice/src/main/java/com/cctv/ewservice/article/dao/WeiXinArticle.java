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
 * @Date Created in 2018/9/19
 */
@Repository
public class WeiXinArticle {
    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    @PostConstruct
    public void init() {
        client = this.transportClient;
    }

    /**
     * 获取对应的账号列表
     * @param type
     * @param level
     * @param channel
     * @return
     */
    public List<Map<String,String>> getWeiXinIdList(String type, String level, String channel) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();

        if (!"0".equals(type)) {
            if ("1".equals(type)) {
                QueryBuilder termquery1 = QueryBuilders.termsQuery("type", "央视", "央广", "国广");
                boolquery.must(termquery1);
                if (!"0".equals(level)) {
                    QueryBuilder termquery2 = QueryBuilders.termQuery("level", level);
                    boolquery.must(termquery2);
                    if (!"17".equals(channel)) {
                        if ("7".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-7军事", "CCTV-7农业");
                            boolquery.must(termquery3);
                        } else if ("21".equals(channel)){
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心");
                            boolquery.must(termquery3);
                        } else if ("22".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                            boolquery.must(termquery3);
                        } else if ("23".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                            boolquery.must(termquery3);
                        } else if ("24".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                    "人民日报","人民网","央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心", "央视总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                    "CCTV-NEWS外语", "央视网");
                            boolquery.mustNot(termquery3);
                        } else {
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                            boolquery.must(termquery3);
                        }
                    }
                } else {
                    if (!"17".equals(channel)) {
                        if ("7".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-7军事", "CCTV-7农业");
                            boolquery.must(termquery3);
                        } else if ("21".equals(channel)){
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心");
                            boolquery.must(termquery3);
                        } else if ("22".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                            boolquery.must(termquery3);
                        } else if ("23".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                            boolquery.must(termquery3);
                        } else if ("24".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                    "人民日报","人民网","央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心", "央视总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                    "CCTV-NEWS外语", "央视网");
                            boolquery.mustNot(termquery3);
                        } else {
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                            boolquery.must(termquery3);
                        }
                    }
                }
            } else if ("2".equals(type)) {
                QueryBuilder termquery1 = QueryBuilders.termsQuery("type", "人民", "新华", "其他");
                boolquery.must(termquery1);
                if (!"0".equals(level)) {
                    QueryBuilder termquery2 = QueryBuilders.termQuery("level", level);
                    boolquery.must(termquery2);
                    if (!"17".equals(channel)) {
                        if ("7".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-7军事", "CCTV-7农业");
                            boolquery.must(termquery3);
                        } else if ("21".equals(channel)){
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心");
                            boolquery.must(termquery3);
                        } else if ("22".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                            boolquery.must(termquery3);
                        } else if ("23".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                            boolquery.must(termquery3);
                        } else if ("24".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                    "人民日报","人民网","央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心", "央视总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                    "CCTV-NEWS外语", "央视网");
                            boolquery.mustNot(termquery3);
                        } else {
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                            boolquery.must(termquery3);
                        }
                    }
                } else {
                    if (!"17".equals(channel)) {
                        if ("7".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-7军事", "CCTV-7农业");
                            boolquery.must(termquery3);
                        } else if ("21".equals(channel)){
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心");
                            boolquery.must(termquery3);
                        } else if ("22".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                            boolquery.must(termquery3);
                        } else if ("23".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                            boolquery.must(termquery3);
                        } else if ("24".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                    "人民日报","人民网","央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心", "央视总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                    "CCTV-NEWS外语", "央视网");
                            boolquery.mustNot(termquery3);
                        } else {
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                            boolquery.must(termquery3);
                        }
                    }
                }
            } else {
                QueryBuilder termquery1 = QueryBuilders.termsQuery("type", type);
                boolquery.must(termquery1);
                if (!"0".equals(level)) {
                    QueryBuilder termquery2 = QueryBuilders.termQuery("level", level);
                    boolquery.must(termquery2);
                    if (!"17".equals(channel)) {
                        if ("7".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-7军事", "CCTV-7农业");
                            boolquery.must(termquery3);
                        } else if ("21".equals(channel)){
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心");
                            boolquery.must(termquery3);
                        } else if ("22".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                            boolquery.must(termquery3);
                        } else if ("23".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                            boolquery.must(termquery3);
                        } else if ("24".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                    "人民日报","人民网","央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心", "央视总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                    "CCTV-NEWS外语", "央视网");
                            boolquery.mustNot(termquery3);
                        } else {
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                            boolquery.must(termquery3);
                        }
                    }
                } else {
                    if (!"17".equals(channel)) {
                        if ("7".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-7军事", "CCTV-7农业");
                            boolquery.must(termquery3);
                        } else if ("21".equals(channel)){
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心");
                            boolquery.must(termquery3);
                        } else if ("22".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                            boolquery.must(termquery3);
                        } else if ("23".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                            boolquery.must(termquery3);
                        } else if ("24".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                    "人民日报","人民网","央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心", "央视总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                    "CCTV-NEWS外语", "央视网");
                            boolquery.mustNot(termquery3);
                        } else {
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                            boolquery.must(termquery3);
                        }
                    }
                }
            }
        } else {
            if (!"0".equals(level)) {
                QueryBuilder termquery2 = QueryBuilders.termQuery("level", level);
                boolquery.must(termquery2);
                if (!"17".equals(channel)) {
                    if ("7".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-7军事", "CCTV-7农业");
                        boolquery.must(termquery3);
                    } else if ("21".equals(channel)){
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心");
                        boolquery.must(termquery3);
                    } else if ("22".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                        boolquery.must(termquery3);
                    } else if ("23".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                        boolquery.must(termquery3);
                    } else if ("24".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                "人民日报","人民网","央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心", "央视总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                "CCTV-NEWS外语", "央视网");
                        boolquery.mustNot(termquery3);
                    } else {
                        QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                        boolquery.must(termquery3);
                    }
                }
            } else {
                if (!"17".equals(channel)) {
                    if ("7".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-7军事", "CCTV-7农业");
                        boolquery.must(termquery3);
                    } else if ("21".equals(channel)){
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心");
                        boolquery.must(termquery3);
                    } else if ("22".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                        boolquery.must(termquery3);
                    } else if ("23".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                        boolquery.must(termquery3);
                    } else if ("24".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                "人民日报","人民网","央视技术制作中心", "央视其他频道", "央视机关党委", "央视技术管理中心", "央视总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                "CCTV-NEWS外语", "央视网");
                        boolquery.mustNot(termquery3);
                    } else {
                        QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                        boolquery.must(termquery3);
                    }
                }
            }
        }
        /*if (!"0".equals(level)) {
            QueryBuilder termquery2 = QueryBuilders.termQuery("level", level);
            boolquery.must(termquery2);
        }
        if (!"17".equals(channel)) {
//            System.out.println(channel);
            if ("7".equals(channel)) {
                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "CCTV-7军事", "CCTV-7农业");
                boolquery.must(termquery3);
            } else {
                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                boolquery.must(termquery3);
            }
        }*/

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
            idmap.put("level", String.valueOf(hitmap.get("level")));
            idmap.put("channel", String.valueOf(hitmap.get("channel")));
            list.add(idmap);
        }
        return list;
    }


    public String getLatestUpdateDate() {
        SearchResponse response = client
                .prepareSearch("weixin_article_platform*")
                .setTypes("type")
                .addSort("pub_date", SortOrder.DESC)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();

        SearchHits hits = response.getHits();
        String date = null;
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            date = String.valueOf(hitmap.get("pub_date"));
        }
        return date;
    }


    /**
     * 查询大数据中心的文章表中是否含有某个微信号的文章
     * @param weixinid
     * @param startdate
     * @param enddate
     * @return
     */
    public Boolean isIdExits(String weixinid, String startdate, String enddate) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("weixin_id", weixinid);

        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("pub_date").from(startdate).to(enddate);
        boolquery.must(termquery1)
                .must(rangequery);
        SearchResponse response = client
                .prepareSearch("weixin_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10)
                .get();
        SearchHits hits = response.getHits();
        long count = hits.getTotalHits();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取每条文章的详细信息
     * @param weixinid
     * @param startdate
     * @param enddate
     * @return
     */
    public List<Map<String, String>> getWeiXinArticleMsgid(String weixinid, String startdate, String enddate, String keyword, Map<String, String> idmap) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("weixin_id", weixinid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "3");
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("pub_date").from(startdate).to(enddate);
        boolquery.must(termquery1)
                .must(termquery2)
                .must(rangequery);

        TermsAggregationBuilder msgidAgg = AggregationBuilders.terms("msgid").field("msgid").size(10000);
        SearchResponse response = null;
        if (keyword != null) {
            QueryBuilder queryBuilder2 = QueryBuilders.matchPhraseQuery("title", keyword);
            boolquery.must(queryBuilder2);
            response = client
                    .prepareSearch("weixin_article_accu_platform*")
                    .setTypes("type")
//                    .setMinScore((float) 10)
                    .setQuery(boolquery)
                    .addAggregation(msgidAgg)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        } else {
            response = client
                    .prepareSearch("weixin_article_accu_platform*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .addAggregation(msgidAgg)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        }

        SearchHits hits = response.getHits();
        Terms term = response.getAggregations().get("msgid");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Terms.Bucket entry : term.getBuckets()) {
            String msgid = entry.getKeyAsString();
            Map<String, String> map = new HashMap<String, String>();
            map = getArticleInfoByMsgid(msgid);
            map.putAll(getQingBoWeiXinArticleLike(msgid));
            map.putAll(idmap);
            list.add(map);
        }
        return list;
    }

    /**
     * 获取大数据文章累计表中的当前misgid所对应的文章数据
     * @param msgid
     * @return
     */
    public Map<String, String> getArticleInfoByMsgid(String msgid) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("msgid", msgid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "3");
//        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("pub_date").from(startdate).to(enddate);
        boolquery.must(termquery1)
                .must(termquery2);
//                .must(rangequery);

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
            String title = String.valueOf(hitmap.get("title"));
            String read = String.valueOf(hitmap.get("int_page_read_count"));
            String share = String.valueOf(hitmap.get("share_count"));
            String add = String.valueOf(hitmap.get("add_to_fav_count"));
            String pub_date = String.valueOf(hitmap.get("pub_date"));
            map.put("title", title);
            map.put("read", read);
            map.put("share", share);
            map.put("add", add);
            map.put("pub_date", pub_date);
        }
        return map;
    }

    /**
     * 获取微信文章的清博指标
     * @param msgid
     * @return
     */
    public Map<String, String> getQingBoWeiXinArticleLike(String msgid) {
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
        Map<String, String> map = new HashMap<String, String>();
        if (hits.totalHits > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> hitmap = new HashMap<String, Object>();
                hitmap = hit.getSourceAsMap();
                if (String.valueOf(hitmap.get("url")) != "null") {
                    map.put("url", String.valueOf(hitmap.get("url")));
                } else {
                    map.put("url", "-");
                }
                if (String.valueOf(hitmap.get("likenum_newest")) != "null") {
                    map.put("like", String.valueOf(hitmap.get("likenum_newest")));
                } else {
                    map.put("like", "-");
                }
                if (Integer.valueOf(String.valueOf(hitmap.get("top"))) != 1) {
                    map.put("istop", "否");
                } else {
                    map.put("istop", "是");
                }
            }
        } else {
            map.put("url", "-");
            map.put("like", "-");
            map.put("istop", "-");
        }

        return map;
    }


    /**
     * 获取清博微信账号的文章详细信息
     * @param weixinid
     * @param startdate
     * @param enddate
     * @param keyword
     * @param idmap
     * @return
     */
    public List<Map<String, String>> getQingBoArticleInfo(String weixinid, String startdate, String enddate, String keyword, Map<String, String> idmap) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("wx_name", weixinid);

        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("date").from(startdate).to(enddate);
        boolquery.must(termquery1)
                .must(rangequery);

        SearchResponse response = null;
        if (keyword != null) {
            QueryBuilder queryBuilder2 = QueryBuilders.matchPhraseQuery("title", keyword);
            boolquery.must(queryBuilder2);
            response = client
                    .prepareSearch("weixin_article_qingbo*")
                    .setTypes("type")
//                    .setMinScore((float) 10)
                    .setQuery(boolquery)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        } else {
            response = client
                    .prepareSearch("weixin_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        }

        SearchHits hits = response.getHits();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            Map<String, String> map = new HashMap<String, String>();
            String title = String.valueOf(hitmap.get("title"));
            String url = String.valueOf(hitmap.get("url"));
            String read = String.valueOf(hitmap.get("readnum_newest"));
            String like = String.valueOf(hitmap.get("likenum_newest"));
            String time = String.valueOf(hitmap.get("posttime"));
            if (Integer.valueOf(String.valueOf(hitmap.get("top"))) != 1) {
                map.put("istop", "否");
            } else {
                map.put("istop", "是");
            }
            map.put("title", title);
            map.put("url", url);
            map.put("read", read);
            map.put("share", "-");
            map.put("add", "-");
            map.put("pub_date", time.substring(0, time.indexOf(" ")));
            map.put("like", like);
            map.putAll(idmap);
            list.add(map);
        }
        return list;
    }
}
