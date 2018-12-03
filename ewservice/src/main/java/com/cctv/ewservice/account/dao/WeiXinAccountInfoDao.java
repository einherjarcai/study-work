package com.cctv.ewservice.account.dao;

import com.cctv.ewservice.common.CommonDateFunction;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangcai
 * @Date Created in 2018/9/18
 */
@Repository
public class WeiXinAccountInfoDao {
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

    /**
     * 获取大数据账号数据最新更新时间
     * @return
     */
    public String getLatestUpdateDate() {
        SearchResponse response = client
                .prepareSearch("weixin_account_total_dataplat*")
                .setTypes("type")
                .addSort("date", SortOrder.DESC)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();

        SearchHits hits = response.getHits();
        String date = null;
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            date = String.valueOf(hitmap.get("date"));
        }
        return date;
    }


    public String getQingBoLatestUpdateDate() {
        SearchResponse response = client
                .prepareSearch("weixin_account_qingbo*")
                .setTypes("type")
                .addSort("date", SortOrder.DESC)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();

        SearchHits hits = response.getHits();
        String date = null;
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            date = String.valueOf(hitmap.get("date"));
        }
        return date;
    }
    /**
     * 获取订阅数
     * @param weixinid
     * @param date
     * @return
     */
    public List<String> getFlowerById(String weixinid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("weixin_id", weixinid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("date", date);
        QueryBuilder termquery3 = QueryBuilders.termQuery("level", "3");
        boolquery.must(termquery1)
                .must(termquery2)
                .must(termquery3);

        SumAggregationBuilder sumAgg = AggregationBuilders.sum("follow").field("cumulate_user");
        SearchResponse response = client
                .prepareSearch("weixin_account_total_dataplat*")
                .setTypes("type")
                .setQuery(boolquery)
                .addAggregation(sumAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();

        Sum sumValue = response.getAggregations().get("follow");
        String sum = sumValue.getValueAsString();
        long count = response.getHits().getTotalHits();
//        System.out.println(sum);
        if (sum.indexOf("E") != -1) {
            BigDecimal bd = new BigDecimal(sum);
            sum = bd.toPlainString() + ".0";
        }
        if (sum.equals("0.0")) {
            String before_day = CommonDateFunction.convertCircleToBFStartDate(0, date);
            BoolQueryBuilder yesquery = QueryBuilders.boolQuery();
            QueryBuilder termquery4 = QueryBuilders.termQuery("date", before_day);
            yesquery.must(termquery1)
                    .must(termquery3)
                    .must(termquery4);
            SumAggregationBuilder yessumAgg = AggregationBuilders.sum("follow").field("cumulate_user");
            SearchResponse yesresponse = client
                    .prepareSearch("weixin_account_total_dataplat*")
                    .setTypes("type")
                    .setQuery(yesquery)
                    .addAggregation(yessumAgg)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(1000)
                    .get();

            Sum yessumValue = yesresponse.getAggregations().get("follow");
            sum = yessumValue.getValueAsString();
            if (sum.indexOf("E") != -1) {
                BigDecimal bd = new BigDecimal(sum);
                sum = bd.toPlainString() + ".0";
            }
            count = yesresponse.getHits().getTotalHits();
        }
        String flag = "0";
        if (count > 0 ){
            flag = "1";
        }
        List<String> list = new ArrayList<String>();
        list.add(sum);
        list.add(flag);
        return list;
    }

    /**
     * 获取文章数
     * @param weixinid
     * @param date
     * @return
     */
    public int getArticleCount(String weixinid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("weixin_id", weixinid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("pub_date", date);
        QueryBuilder termquery4 = QueryBuilders.termQuery("level", "3");
        boolquery.must(termquery1)
                .must(termquery2)
                .must(termquery4);
        TermsAggregationBuilder msgidAgg = AggregationBuilders.terms("msgid").field("msgid").size(10000);

        SearchResponse response = client
                .prepareSearch("weixin_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addAggregation(msgidAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();
        Terms term = response.getAggregations().get("msgid");
        int count = term.getBuckets().size();

        return count;
    }


    /**
     * 获取微信账号阅读数
     * @param weixinid
     * @param date
     * @return
     */
    public int getWeiXinAccountRead(String weixinid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("weixin_id", weixinid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("date", date);
        QueryBuilder termquery3 = QueryBuilders.termQuery("level", "3");
        boolquery.must(termquery1)
                .must(termquery2)
                .must(termquery3);

        SumAggregationBuilder sumAgg = AggregationBuilders.sum("read").field("int_page_read_count");

        SearchResponse response = client
                .prepareSearch("weixin_account_read_dataplat*")
                .setTypes("type")
                .setQuery(boolquery)
                .addAggregation(sumAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();
        Sum sumValue = response.getAggregations().get("read");
        String sum = sumValue.getValueAsString();
        if (sum == null || "null".equals(sum)) {
            sum = "0";
        } else {
            sum = sum.substring(0, sum.indexOf("."));
        }
        return Integer.valueOf(sum);
    }


    /**
     * 获取每篇文章统计的最新阅读数
     * 文章可能会被统计多次
     * 阅读数通过 账号阅读关系表 计算，此函数暂时不用
     * @param msgid
     * @return
     */
    /*public int getWeiXinArticleRead(String msgid) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("msgid", msgid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "3");
        boolquery.must(termquery1)
                .must(termquery2);

        SearchResponse response = client
                .prepareSearch("weixin_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addSort("date", SortOrder.DESC)
                .addSort("digital_of_level", SortOrder.DESC)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();
        SearchHits hits = response.getHits();
        int read = 0;
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            String count = String.valueOf(hitmap.get("int_page_read_count"));
            if (count == null || "null".equals(count)) {
                count = "0";
            }
            read = Integer.valueOf(count);
        }
        return read;
    }*/


    /**
     * 微信分享次数
     * @param weixinid
     * @param date
     * @return
     */
    public String getShareCount(String weixinid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("weixin_id", weixinid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("date", date);
        QueryBuilder termquery3 = QueryBuilders.termQuery("level", "3");
        boolquery.must(termquery1)
                .must(termquery2)
                .must(termquery3);

        SumAggregationBuilder ShareSumAgg = AggregationBuilders.sum("share").field("share_count");
        SearchResponse response = client
                .prepareSearch("weixin_account_share_dataplat*")
                .setTypes("type")
                .setQuery(boolquery)
                .addAggregation(ShareSumAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();
        Sum sumValue = response.getAggregations().get("share");
        String sum = sumValue.getValueAsString();
        return sum;
    }

    /**
     * 清博微信文章点赞数
     * @param weixinid
     * @param date
     * @return
     */
    public String getLikeCount(String weixinid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("wx_name", weixinid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("date", date);
        boolquery.must(termquery1)
                .must(termquery2);

//        SumAggregationBuilder likeSumAgg = AggregationBuilders.sum("like").field("likenum_pm");
        SearchResponse response = client
                .prepareSearch("weixin_account_qingbo*")
                .setTypes("type")
                .setQuery(boolquery)
//                .addAggregation(likeSumAgg)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();
//        Sum sumValue = response.getAggregations().get("like");
//        String sum = sumValue.getValueAsString();
        String sum = null;
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            sum = String.valueOf(hitmap.get("likenum_all"));
        }
        return sum;
    }

    /**
     * 大数据中心没有，查询清博相关账号信息
     * @param weixinid
     * @param date
     */
    public Map<String,String> getQingBoData(String weixinid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("wx_name", weixinid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("date", date);
        boolquery.must(termquery1)
                .must(termquery2);

        SearchResponse response = client
                .prepareSearch("weixin_account_qingbo*")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();
        SearchHits hits = response.getHits();
        Map<String, String> idmap = new HashMap<String, String>();
        if (hits.getTotalHits() > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> hitmap = new HashMap<String, Object>();
                hitmap = hit.getSourceAsMap();
                idmap.put("flowers", "-");
                String article_count = String.valueOf(hitmap.get("url_num"));
                idmap.put("article_count", article_count);
                String read = String.valueOf(hitmap.get("readnum_all"));
                idmap.put("read", read);
                String like = String.valueOf(hitmap.get("likenum_all"));
                int hd = Integer.valueOf(like);
                idmap.put("hd", String.valueOf(hd));
            }
        } else {
            idmap.put("flowers", "-");
            idmap.put("article_count", "0");
            idmap.put("read", "0");
            idmap.put("hd", "0");
        }

        return idmap;
    }
}
