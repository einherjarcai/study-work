package com.cctv.ewservice.account.dao;

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
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.*;

/**
 * @author wangcai
 * @Date Created in 2018/9/18
 */
@Repository
public class WeiBoAccountInfoDao {
    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    @PostConstruct
    public void init() {
        client = this.transportClient;
    }

    /**
     * 获取符合条件的微博id列表
     * @param type
     * @param level
     * @param channel
     * @return
     */
    public List<Map<String,String>> getWeiBoIdList(String type, String level, String channel) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();

        if (!"0".equals(type)) {
            if ("1".equals(type)) {
                QueryBuilder termquery1 = QueryBuilders.termsQuery("type", "央视", "央广", "国广");
                boolquery.must(termquery1);
                if (!"0".equals(level)) {
                    QueryBuilder termquery2 = QueryBuilders.termQuery("level", level);
                    boolquery.must(termquery2);
                    if (!"17".equals(channel)) {
                        if ("21".equals(channel)){
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                            boolquery.must(termquery3);
                        } else if ("22".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                            boolquery.must(termquery3);
                        } else if ("23".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                            boolquery.must(termquery3);
                        } else if ("24".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                    "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                    "外语频道", "央视网");
                            boolquery.mustNot(termquery3);
                        } else {
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                            boolquery.must(termquery3);
                        }
                    }
                } else {
                    if (!"17".equals(channel)) {
                        if ("21".equals(channel)){
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                            boolquery.must(termquery3);
                        } else if ("22".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                            boolquery.must(termquery3);
                        } else if ("23".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                            boolquery.must(termquery3);
                        } else if ("24".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                    "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                    "外语频道", "央视网");
                            boolquery.mustNot(termquery3);
                        } else {
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                            boolquery.must(termquery3);
                        }
                    }
                }
            } else if ("2".equals(type)) {
                QueryBuilder termquery1 = QueryBuilders.termsQuery("type", "人民", "新华", "卫视栏目", "卫视频道");
                boolquery.must(termquery1);
                if (!"0".equals(level)) {
                    if ("8".equals(level)){
                        QueryBuilder termquery2 = QueryBuilders.termsQuery("level", "卫视栏目", "卫视频道");
                        boolquery.must(termquery2);
                        if (!"17".equals(channel)) {
                            if ("21".equals(channel)){
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                                boolquery.must(termquery3);
                            } else if ("22".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                                boolquery.must(termquery3);
                            } else if ("23".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                                boolquery.must(termquery3);
                            } else if ("24".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                        "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                        "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                        "外语频道", "央视网");
                                boolquery.mustNot(termquery3);
                            } else {
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                                boolquery.must(termquery3);
                            }
                        }
                    } else {
                        QueryBuilder termquery2 = QueryBuilders.termQuery("level", level);
                        boolquery.must(termquery2);
                        if (!"17".equals(channel)) {
                            if ("21".equals(channel)){
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                                boolquery.must(termquery3);
                            } else if ("22".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                                boolquery.must(termquery3);
                            } else if ("23".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                                boolquery.must(termquery3);
                            } else if ("24".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                        "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                        "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                        "外语频道", "央视网");
                                boolquery.mustNot(termquery3);
                            } else {
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                                boolquery.must(termquery3);
                            }
                        }
                    }
                } else {
                    if (!"17".equals(channel)) {
                        if ("21".equals(channel)){
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                            boolquery.must(termquery3);
                        } else if ("22".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                            boolquery.must(termquery3);
                        } else if ("23".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                            boolquery.must(termquery3);
                        } else if ("24".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                    "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                    "外语频道", "央视网");
                            boolquery.mustNot(termquery3);
                        } else {
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                            boolquery.must(termquery3);
                        }
                    }
                }
            } else if ("8".equals(type)) {
                QueryBuilder termquery1 = QueryBuilders.termsQuery("type",  "卫视栏目", "卫视频道");
                boolquery.must(termquery1);
                if (!"0".equals(level)) {
                    if ("8".equals(level)){
                        QueryBuilder termquery2 = QueryBuilders.termsQuery("level", "卫视栏目", "卫视频道");
                        boolquery.must(termquery2);
                        if (!"17".equals(channel)) {
                            if ("21".equals(channel)){
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                                boolquery.must(termquery3);
                            } else if ("22".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                                boolquery.must(termquery3);
                            } else if ("23".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                                boolquery.must(termquery3);
                            } else if ("24".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                        "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                        "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                        "外语频道", "央视网");
                                boolquery.mustNot(termquery3);
                            } else {
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                                boolquery.must(termquery3);
                            }
                        }
                    } else {
                        QueryBuilder termquery2 = QueryBuilders.termQuery("level", level);
                        boolquery.must(termquery2);
                        if (!"17".equals(channel)) {
                            if ("21".equals(channel)){
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                                boolquery.must(termquery3);
                            } else if ("22".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                                boolquery.must(termquery3);
                            } else if ("23".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                                boolquery.must(termquery3);
                            } else if ("24".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                        "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                        "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                        "外语频道", "央视网");
                                boolquery.mustNot(termquery3);
                            } else {
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                                boolquery.must(termquery3);
                            }
                        }
                    }
                } else {
                    if (!"17".equals(channel)) {
                        if ("21".equals(channel)){
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                            boolquery.must(termquery3);
                        } else if ("22".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                            boolquery.must(termquery3);
                        } else if ("23".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                            boolquery.must(termquery3);
                        } else if ("24".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                    "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                    "外语频道", "央视网");
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
                    if ("8".equals(level)){
                        QueryBuilder termquery2 = QueryBuilders.termsQuery("level", "卫视栏目", "卫视频道");
                        boolquery.must(termquery2);
                        if (!"17".equals(channel)) {
                            if ("21".equals(channel)){
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                                boolquery.must(termquery3);
                            } else if ("22".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                                boolquery.must(termquery3);
                            } else if ("23".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                                boolquery.must(termquery3);
                            } else if ("24".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                        "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                        "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                        "外语频道", "央视网");
                                boolquery.mustNot(termquery3);
                            } else {
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                                boolquery.must(termquery3);
                            }
                        }
                    } else {
                        QueryBuilder termquery2 = QueryBuilders.termQuery("level", level);
                        boolquery.must(termquery2);
                        if (!"17".equals(channel)) {
                            if ("21".equals(channel)){
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                                boolquery.must(termquery3);
                            } else if ("22".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                                boolquery.must(termquery3);
                            } else if ("23".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                                boolquery.must(termquery3);
                            } else if ("24".equals(channel)) {
                                QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                        "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                        "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                        "外语频道", "央视网");
                                boolquery.mustNot(termquery3);
                            } else {
                                QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                                boolquery.must(termquery3);
                            }
                        }
                    }

                } else {
                    if (!"17".equals(channel)) {
                        if ("21".equals(channel)){
                            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                            boolquery.must(termquery3);
                        } else if ("22".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                            boolquery.must(termquery3);
                        } else if ("23".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                            boolquery.must(termquery3);
                        } else if ("24".equals(channel)) {
                            QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                    "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                    "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                    "外语频道", "央视网");
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
                    if ("21".equals(channel)){
                        QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                        boolquery.must(termquery3);
                    } else if ("22".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                        boolquery.must(termquery3);
                    } else if ("23".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                        boolquery.must(termquery3);
                    } else if ("24".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                "外语频道", "央视网");
                        boolquery.mustNot(termquery3);
                    } else {
                        QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
                        boolquery.must(termquery3);
                    }
                }
            } else {
                if (!"17".equals(channel)) {
                    if ("21".equals(channel)){
                        QueryBuilder termquery3 = QueryBuilders.termQuery("channel", "其他频道");
                        boolquery.must(termquery3);
                    } else if ("22".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "人民日报", "人民网");
                        boolquery.must(termquery3);
                    } else if ("23".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网");
                        boolquery.must(termquery3);
                    } else if ("24".equals(channel)) {
                        QueryBuilder termquery3 = QueryBuilders.termsQuery("channel", "新华社", "新华网",
                                "人民日报","人民网","其他频道", "总编室", "中国国际广播电台", "中央人民广播电台", "CCTV-15音乐", "CCTV-14少儿","CCTV-13新闻", "CCTV-12社会与法",
                                "CCTV-11戏曲", "CCTV-10科教", "CCTV-9纪录", "CCTV-8电视剧", "CCTV-5+体育赛事", "CCTV-5体育", "CCTV-4中文国际", "CCTV-3综艺", "CCTV-2财经", "CCTV-1综合",
                                "外语频道", "央视网");
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

        if (!"18".equals(channel)) {
//            System.out.println(channel);
            QueryBuilder termquery3 = QueryBuilders.termQuery("channel", channel);
            boolquery.must(termquery3);
        }*/

        SearchResponse response = client
                .prepareSearch("weibo_account_list")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();
        SearchHits hits = response.getHits();
//        System.out.println(hits.getTotalHits());
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            Map<String, String> idmap = new HashMap<String, String>();
            idmap.put("name", String.valueOf(hitmap.get("nickname")));
            idmap.put("id", String.valueOf(hitmap.get("UID")));
            idmap.put("type", String.valueOf(hitmap.get("type")));
            idmap.put("level", String.valueOf(hitmap.get("level")));
            idmap.put("channel", String.valueOf(hitmap.get("channel")));
            list.add(idmap);
        }
//        System.out.println(list.toString());
        return list;
    }

    /**
     * 获取相应的微博账号粉丝数
     * @param weiboid
     * @param date
     * @return
     */
    public String getWeiboFlowerByID(String weiboid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("uid", weiboid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("date", date);
        boolquery.must(termquery1)
                .must(termquery2);

        SearchResponse response = client
                .prepareSearch("weibo_account_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .addSort("digital_of_level", SortOrder.DESC)
                .setSize(1)
                .get();

        SearchHits hits = response.getHits();
        String fllow_count = null;
        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
//            System.out.println(hitmap);
            fllow_count = String.valueOf(hitmap.get("followers_accumulation"));
        }
        return fllow_count;
    }


    /**
     * 获取当天所有微博文章的指标和
     * @param weiboid
     * @param date
     * @return
     */
    public List<String> getWeiBoArticleInfo(String weiboid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("uid", weiboid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "2");
        QueryBuilder termquery3 = QueryBuilders.termQuery("date", date);
        String ten_before_day = CommonDateFunction.convertCircleToBFStartDate(10, date);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("created_time").from(ten_before_day + " 00:00:00").to(date + " 23:59:59");
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

        int read = 0;
        int live = 0;
        int video = 0;
        int all = 0;

        Terms term = response.getAggregations().get("mid");

        for (Terms.Bucket entry : term.getBuckets()) {
            String mid = entry.getKeyAsString();
            List<Integer> list = new ArrayList<Integer>();
            list = getWeiBoArticleLatestInfo(mid, date);
            read += list.get(0);
            live += list.get(1);
            video += list.get(2);
            all += list.get(3);
        }
        List<String> index = new ArrayList<String>();
        int count = getWeiBoArticleCount(weiboid, date);
        index.add(String.valueOf(count));
        index.add(String.valueOf(read));
        index.add(String.valueOf(live));
        index.add(String.valueOf(video));
        index.add(String.valueOf(all));
        return index;
    }

    /**
     * 获取当天发布的微博数
     * @param weiboid
     * @param date
     * @return
     */
    public int getWeiBoArticleCount(String weiboid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("uid", weiboid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("level", "2");
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("created_time").from(date + " 00:00:00").to(date + " 23:59:59");
        boolquery.must(termquery1)
                .must(termquery2)
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
     * 获取周期时间统计的微博文章数据
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
            int all = like + comment + repost;
            if (!pub_date.equals(date)) {
                if (read > 0) {
                    List<Integer> yes_list = new ArrayList<Integer>();
                    yes_list = getYesDataInfo(mid, date);
                    if (yes_list.size() > 0) {
                        int yes_read = yes_list.get(0);
                        int yes_live = yes_list.get(1);
                        int yes_video = yes_list.get(2);
                        int yes_all = yes_list.get(3);
                        read = read - yes_read;
                        if (live > 0) {
                            live = live - yes_live;
                        }
                        if (video > 0) {
                            video = video - yes_video;
                        }
                        all = all - yes_all;
                    }
                }
            }
            if (read < 0) {
                read = 0;
            }
            if (live < 0) {
                live = 0;
            }
            if (video < 0) {
                video = 0;
            }
            if (all < 0) {
                all = 0;
            }
            list.add(read);
            list.add(live);
            list.add(video);
            list.add(all);
        }
        return list;
    }


    /**
     * 获取不是当天发布微博的前一天阅读数
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
            list.add(read);
            list.add(live);
            list.add(video);
            list.add(all);
        }
        return list;
    }


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

    /**
     * 获取微博指标数
     * 应该是当天最后时间点的更新数据，此处用最大数代替
     * 此函数暂时废弃
     * @param mid
     * @return
     */
    /*public List<Integer> getWeiBoArticleIndex(String mid) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("mid", mid);
        boolquery.must(termquery1);

        MaxAggregationBuilder readMax = AggregationBuilders.max("read").field("reads_accumulation");
        MaxAggregationBuilder likeMax = AggregationBuilders.max("like").field("attitudes_accumulation");
        MaxAggregationBuilder cmtMax = AggregationBuilders.max("cmt").field("comments_accumulation");
        MaxAggregationBuilder repostMax = AggregationBuilders.max("repost").field("reposts_accumulation");
        MaxAggregationBuilder livePlayMax = AggregationBuilders.max("live").field("live_play_accumulation");
        MaxAggregationBuilder videoMax = AggregationBuilders.max("video").field("video_play_accumulation");

        SearchResponse response = client
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .addAggregation(readMax)
                .addAggregation(likeMax)
                .addAggregation(cmtMax)
                .addAggregation(repostMax)
                .addAggregation(livePlayMax)
                .addAggregation(videoMax)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(10000)
                .get();
        Max readMaxValue = response.getAggregations().get("read");
        String readString = readMaxValue.getValueAsString();
        int read = Integer.valueOf(readString.substring(0, readString.indexOf(".")));
        Max liveMaxValue = response.getAggregations().get("live");
        String liveString = liveMaxValue.getValueAsString();
        int live = Integer.valueOf(liveString.substring(0, liveString.indexOf(".")));
        Max videoMaxValue = response.getAggregations().get("video");
        String videoString = videoMaxValue.getValueAsString();
        int video = Integer.valueOf(videoString.substring(0, videoString.indexOf(".")));

        Max likeMaxValue = response.getAggregations().get("like");
//        int like = Integer.valueOf(String.valueOf(likeMaxValue.getValue()));
        String likeString = likeMaxValue.getValueAsString();
        int like = Integer.valueOf(likeString.substring(0, likeString.indexOf(".")));
        Max cmtMaxValue = response.getAggregations().get("cmt");
        String cmtString = cmtMaxValue.getValueAsString();
        int cmt = Integer.valueOf(cmtString.substring(0, cmtString.indexOf(".")));
        Max repostMaxValue = response.getAggregations().get("repost");
        String repostString = repostMaxValue.getValueAsString();
        int repost = Integer.valueOf(repostString.substring(0, repostString.indexOf(".")));
        int all = like + cmt + repost;

        List<Integer> list = new ArrayList<Integer>();
        list.add(read);
        list.add(live);
        list.add(video);
        list.add(all);
        return list;
    }*/

    /**
     * 获取清博对应的微博指标信息
     * 无阅读数 视频数
     * @param weiboid
     * @param date
     * @return
     */
    public Map<String,String> getQingBoWeiBoInfo(String weiboid, String date) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("uid", weiboid);
        QueryBuilder termquery2 = QueryBuilders.termQuery("date", date);
        boolquery.must(termquery1)
                .must(termquery2);

        SearchResponse response = client
                .prepareSearch("weibo_account_qingbo*")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1)
                .get();

        SearchHits hits = response.getHits();
        Map<String, String>  result = new HashMap<String, String>();
        if (hits.getTotalHits() > 0) {
            for (SearchHit hit : hits) {
                Map<String, Object> hitmap = new HashMap<String, Object>();
                hitmap = hit.getSourceAsMap();
                String flowersCount = String.valueOf(hitmap.get("flowers_count"));
                String weiboCount = String.valueOf(hitmap.get("statuses_count"));
                String repostCount = String.valueOf(hitmap.get("reposts_count"));
                String likeCount = String.valueOf(hitmap.get("attitudes_count"));
                String cmtCount = String.valueOf(hitmap.get("comments_count"));
                int all = Integer.valueOf(repostCount) + Integer.valueOf(likeCount) + Integer.valueOf(cmtCount);
                result.put("flowers", flowersCount);
                result.put("article_count", weiboCount);
                result.put("read", "-");
                result.put("live", "-");
                result.put("video", "-");
                result.put("hd", String.valueOf(all));
            }
        } else {
            result.put("flowers", "-");
            result.put("article_count", "0");
            result.put("read", "-");
            result.put("live", "-");
            result.put("video", "-");
            result.put("hd", "0");
        }
        return result;
    }
}
