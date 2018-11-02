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
public class WeiBoArticle {
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
     * 查询大数据中心的文章表中是否含有某个微博号的文章
     * @param weiboid
     * @param startdate
     * @param enddate
     * @return
     */
    public Boolean isIdExits(String weiboid, String startdate, String enddate ) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("uid", weiboid);

        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("created_time").from(startdate + " 00:00:00").to(enddate + " 23:59:59");
        boolquery.must(termquery1)
                .must(rangequery);
        SearchResponse response = client
                .prepareSearch("weibo_article_platform*")
                .setTypes("type")
                .setQuery(boolquery)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(1000)
                .get();
        SearchHits hits = response.getHits();
        long count = hits.getTotalHits();
//        System.out.println("count: " + count);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取某个微博账号对应的文章列表
     * @param weiboid
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public List<Map<String, String>> getWeiBoArticleMid(String weiboid, String startdate, String enddate, String keyword, Map<String, String> idmap) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("uid", weiboid);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("created_time").from(startdate + " 00:00:00").to(enddate + " 23:59:59");
        boolquery.must(termquery1)
                .must(rangequery);

        TermsAggregationBuilder midAgg = AggregationBuilders.terms("mid").field("mid").size(1000);

        SearchResponse response = null;

        if (keyword != null) {
            QueryBuilder queryBuilder2 = QueryBuilders.matchPhraseQuery("weibo_text", keyword);
            boolquery.must(queryBuilder2);
            response = client
                    .prepareSearch("weibo_article_platform*")
                    .setTypes("type")
//                    .setMinScore((float) 10)
                    .setQuery(boolquery)
                    .addAggregation(midAgg)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        } else {
            response = client
                    .prepareSearch("weibo_article_platform*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .addAggregation(midAgg)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        }

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        Terms term = response.getAggregations().get("mid");
        for (Terms.Bucket entry : term.getBuckets()) {
            String mid = entry.getKeyAsString();
//            System.out.println(mid);
            Map<String, String> map = new HashMap<String, String>();
            map = getWeiBoLatestInfo(mid);
            map.putAll(idmap);
            list.add(map);
        }
        return list;
    }


    /**
     * 获取每篇文章的最新数据
     * @param mid
     * @return
     */
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
            map.put("url", String.valueOf(hitmap.get("weibo_url")));
            if (!"None".equals(String.valueOf(hitmap.get("video_url")))) {
                map.put("video_url", String.valueOf(hitmap.get("video_url")));
            } else {
                map.put("video_url", " ");
            }
            map.put("weibo_text", String.valueOf(hitmap.get("weibo_text")));
//            System.out.println(String.valueOf(hitmap.get("is_original_weibo")));
            if ("true".equals(String.valueOf(hitmap.get("is_original_weibo")))) {
//                System.out.println("2222222222222");
                map.put("isori", "是");
            } else if ("false".equals(String.valueOf(hitmap.get("is_original_weibo")))) {
                map.put("isori", "否");
            }
            map.put("repost", String.valueOf(hitmap.get("reposts_accumulation")));
            map.put("comment", String.valueOf(hitmap.get("comments_accumulation")));
            map.put("like", String.valueOf(hitmap.get("attitudes_accumulation")));
            map.put("read", String.valueOf(hitmap.get("reads_accumulation")));
            String date = String.valueOf(hitmap.get("created_time"));
            int hour = Integer.valueOf(String.valueOf(hitmap.get("digital_of_level"))) + 1;
            String update_date = String.valueOf(hitmap.get("date"));
            if (hour < 10) {
                map.put("latest", update_date.substring(5, update_date.length()) + " 0" + hour + ":00");
            } else {
                map.put("latest", update_date.substring(5, update_date.length()) + " " + hour + ":00");
            }
            map.put("time", date.substring(date.indexOf(" ") + 1, date.length()));
            map.put("date", date.substring(0, date.indexOf(" ")));
            map.put("live", String.valueOf(hitmap.get("live_play_accumulation")));
            map.put("video", String.valueOf(hitmap.get("video_play_accumulation")));
        }
        return map;
    }


    /**
     * 获取清博微博文章数据
     * @param weiboid
     * @param startdate
     * @param enddate
     * @param keyword
     * @param idmap
     * @return
     */
    public List<Map<String, String>> getQingBoWeiBoArticleInfo(String weiboid, String startdate, String enddate, String keyword, Map<String, String> idmap) {
        BoolQueryBuilder boolquery = QueryBuilders.boolQuery();
        QueryBuilder termquery1 = QueryBuilders.termQuery("uid", weiboid);
//        System.out.println(weiboid);
        RangeQueryBuilder rangequery = QueryBuilders.rangeQuery("create_time").from(startdate + " 00:00:00").to(enddate + " 23:59:59");
        boolquery.must(termquery1)
                .must(rangequery);

        SearchResponse response = null;
        if (keyword != null) {
            QueryBuilder queryBuilder2 = QueryBuilders.matchPhraseQuery("content", keyword);
            boolquery.must(queryBuilder2);
            response = client
                    .prepareSearch("weibo_article_qingbo*")
                    .setTypes("type")
//                    .setMinScore((float) 10)
                    .setQuery(boolquery)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        } else {
            response = client
                    .prepareSearch("weibo_article_qingbo*")
                    .setTypes("type")
                    .setQuery(boolquery)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setSize(10000)
                    .get();
        }

        SearchHits hits = response.getHits();
//        System.out.println(hits.getTotalHits());
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        for (SearchHit hit : hits) {
            Map<String, Object> hitmap = new HashMap<String, Object>();
            hitmap = hit.getSourceAsMap();
            Map<String, String> map = new HashMap<String, String>();
            String text = String.valueOf(hitmap.get("content"));
            String url = String.valueOf(hitmap.get("url"));
            String read = String.valueOf(hitmap.get("read_count"));
            String like = String.valueOf(hitmap.get("attitudes_count"));
            String comment = String.valueOf(hitmap.get("comments_count"));
            String repost = String.valueOf(hitmap.get("reposts_count"));
            String date = String.valueOf(hitmap.get("create_time"));

            if (String.valueOf(hitmap.get("is_origin")) == "1") {
                map.put("isori", "是");
            } else {
                map.put("isori", "否");
            }
            map.put("weibo_text", text);
            map.put("url", url);
            map.put("read", read);
            map.put("comment", comment);
            map.put("repost", repost);
            map.put("like", like);
            map.put("latest", "-");
            map.put("time", date.substring(date.indexOf(" ") + 1, date.length()));
            map.put("date", date.substring(0, date.indexOf(" ")));
            map.put("live", "-");
            map.put("video", "-");
//            System.out.println(map);
            map.putAll(idmap);
            list.add(map);
        }
        return list;
    }
}
