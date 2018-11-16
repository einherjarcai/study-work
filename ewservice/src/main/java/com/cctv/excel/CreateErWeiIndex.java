package com.cctv.excel;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * @author wangcai
 * @Date Created in 2018/9/18
 */
public class CreateErWeiIndex {
    /**
     * 新建索引
     * @param indexname
     * @param shards
     * @param replicas
     * @return
     */
    public Boolean createIndex(String indexname, int shards, int replicas) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("analysis")
                        .startObject("analyzer")
                        .startObject("ik")
                        .field("tokenizer", "ik_max_word")
                        .endObject()
                        .endObject()
                        .endObject()
                        .startObject("index")
                        .field("number_of_shards", shards)
                        .field("number_of_replicas", replicas)
                        .endObject()
                    .endObject();
            CreateIndexResponse indexResponse = EsClientTools.getAdminClient().prepareCreate(indexname.toLowerCase()).setSettings(builder).get();
//            System.out.println(indexResponse.isAcknowledged());
            flag = indexResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除索引
     * @param indexname
     * @return
     */
    public Boolean delateIndex(String indexname) {
        Boolean flag = false;
        DeleteIndexResponse deleteResponse = EsClientTools.getAdminClient()
                .prepareDelete(indexname.toLowerCase())
                .execute()
                .actionGet();
        flag = deleteResponse.isAcknowledged();
        System.out.println(flag);
        return flag;
    }


    /**
     * 清博微博账号对应关系
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setQingBoWeiBoAccount(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("category")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("subcategory")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("channel")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("is_touch")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("day")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("uid")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("nickname")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("verified")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("verified_reason")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("follows_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("flowers_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("status_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("avatar")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("comments_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("statuses_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("statuses_count_up")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("reposts_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("reposts_count_up")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("comments_count_up")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("origin_statuses_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("origin_statuses_count_up")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("origin_reposts_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("origin_reposts_count_up")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("origin_comments_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("origin_comments_count_up")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("attitudes_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("attitudes_count_up")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("bci")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("bci_up")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 清博微博文章对应关系
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setQingBoWeiBoArticle(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("category")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("subcategory")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("channel")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("is_touch")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("url")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("content")
                            .startObject()
                            .field("type", "text")
                            .field("analyzer", "ik_max_word")
                            .field("search_analyzer", "ik_max_word")
                            .endObject()
                        .field("create_time")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("uid")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("nickname")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("mid")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("follows_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("flowers_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("status_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("comments_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("reposts_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("attitudes_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("is_origin")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("source")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("origin_nickname")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("origin_uid")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("origin_source")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 清博微信账号对应关系
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setQingBoWeiXinAccount(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("category")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("subcategory")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("channel")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("is_touch")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("nickname_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("wx_logo")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("wx_nickname")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("wx_qrcode")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("wx_note")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("wx_vip")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("wx_vip_note")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("wx_biz")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("wx_name")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("url_times")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("url_times_likenum")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("url_times_readnum")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("url_num")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("url_num_10w")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("readnum_all")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("readnum_av")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("likenum_all")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("likenum_av")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("readnum_max")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("likenum_max")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("wci")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("rowno")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("day")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 清博微信文章对应关系
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setQingBoWeiXinArticle(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("category")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("subcategory")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("channel")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("is_touch")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("nickname_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("wx_name")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("name")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("title")
                            .startObject()
                            .field("type", "text")
                            .field("analyzer", "ik_max_word")
                            .field("search_analyzer", "ik_max_word")
                            .endObject()
                        .field("url")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("msgid")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("posttime")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("content")
                            .startObject()
                            .field("type", "text")
                            .field("analyzer", "ik_max_word")
                            .field("search_analyzer", "ik_max_word")
                            .endObject()
                        .field("content_text")
                            .startObject()
                            .field("type", "text")
                            .field("analyzer", "ik_max_word")
                            .field("search_analyzer", "ik_max_word")
                            .endObject()
                        .field("readnum_pm")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("likenum_pm")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("readnum_week")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("likenum_week")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("top")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("readnum_newest")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("likenum_newest")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("status")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("sourceurl")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("author")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("copyright")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("sn")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 大数据中心微博账号对应关系
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setDataPlatWeiBoAccount(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("uid")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("uname")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("utype")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("followers_accumulation")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("statuses_accumulation")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 大数据中心微博文章对应关系
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setDataPlatWeiBoArticle(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("uid")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("uname")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("utype")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("mid")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("ori_mid")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("weibo_url")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("weibo_text")
                            .startObject()
                            .field("type", "text")
                            .field("analyzer", "ik_max_word")
                            .field("search_analyzer", "ik_max_word")
                            .endObject()
                        .field("created_time")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("is_original_weibo")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("reposts_accumulation")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("comments_accumulation")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("attitudes_accumulation")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("reads_accumulation")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("video_play_accumulation")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("live_play_accumulation")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("toutiao_num_accumulation")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 大数据微信文章关系(当天)
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setDataPlatWeiXinArticleDay(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("msgid")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("pub_date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("account_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("account_name")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("title")
                            .startObject()
                            .field("type", "text")
                            .field("analyzer", "ik_max_word")
                            .field("search_analyzer", "ik_max_word")
                            .endObject()
                        .field("int_page_read_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("ori_page_read_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("ori_page_read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("share_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("share_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("add_to_fav_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("add_to_fav_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("weixin_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("raw_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 大数据微信文章关系(累计)
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setDataPlatWeiXinArticleAccu(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("msgid")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("pub_date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("account_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("account_name")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("title")
                            .startObject()
                            .field("type", "text")
                            .field("analyzer", "ik_max_word")
                            .field("search_analyzer", "ik_max_word")
                            .endObject()
                        .field("target_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_read_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("ori_page_read_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("ori_page_read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("share_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("share_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("add_to_fav_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("add_to_fav_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_from_session_read_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_from_session_read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_from_hist_msg_read_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_from_hist_msg_read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_from_feed_read_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_from_feed_read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_from_friends_read_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_from_friends_read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_from_other_read_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_from_other_read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("feed_share_from_session_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("feed_share_from_session_cnt")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("feed_share_from_feed_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("feed_share_from_feed_cnt")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("feed_share_from_other_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("feed_share_from_other_cnt")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("weixin_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("raw_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 大数据微信账号关系（总体）
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setDataPlatWeiXinAccountTotal(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("account_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("account_name")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("user_source")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("new_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("cumulate_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("cancel_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("weixin_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("raw_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 大数据微信账号关系（阅读）
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setDataPlatWeiXinAccountRead(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("account_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("account_name")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("user_source")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("share_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("share_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("add_to_fav_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("add_to_fav_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("ori_page_read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("ori_page_read_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_read_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("int_page_read_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("weixin_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("raw_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 大数据微信账号关系（分享）
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setDataPlatWeiXinAccountShare(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("account_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("account_name")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("user_source")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("share_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("share_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("share_scene")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("weixin_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("raw_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 大数据微信账号关系（消息）
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setDataPlatWeiXinAccountUpStreamMsg(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("account_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("account_name")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("user_source")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("msg_type")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("msg_count")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("msg_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("weixin_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("raw_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 大数据微信账号关系（消息分布）
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setDataPlatWeiXinAccountUpStreamMsgDist(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("data_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("date")
                            .startObject()
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("digital_of_level")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("account_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("account_name")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("user_source")
                            .startObject()
                            .field("type", "integer")
                            .endObject()
                        .field("count_interval")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("msg_user")
                            .startObject()
                            .field("type", "long")
                            .endObject()
                        .field("weixin_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("raw_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 微博账号列表
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setWeiBoAccountList(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("UID")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("nickname")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("channel")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("type")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 微信账号列表
     * @param indexname
     * @param type
     * @return
     */
    public Boolean setWeiXinAccountList(String indexname, String type) {
        Boolean flag = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    .field("properties")
                    .startObject()
                        .field("wx_id")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("nickname")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("channel")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("type")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("level")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                        .field("biz")
                            .startObject()
                            .field("type", "keyword")
                            .endObject()
                    .endObject()
                    .endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexname).source(builder).type(type);
            PutMappingResponse response = EsClientTools.getAdminClient().putMapping(mappingRequest).actionGet();
            flag = response.isAcknowledged();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    public static void main(String[] args) {
        CreateErWeiIndex ct = new CreateErWeiIndex();
        Boolean indexFlag = ct.createIndex("weibo_account_list", 5, 1);
//        Boolean indexFlag = ct.createIndex("weixin_account_list", 5, 1);
        System.out.println("索引创建是否成功: " + indexFlag);
        Boolean mappingFlag = ct.setWeiBoAccountList("weibo_account_list", "type");
//        Boolean mappingFlag = ct.setWeiXinAccountList("weixin_account_list", "type");
        System.out.println("映射创建是否成功: " + mappingFlag);
    }
}
