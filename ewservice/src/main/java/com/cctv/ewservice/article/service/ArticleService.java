package com.cctv.ewservice.article.service;

import com.cctv.ewservice.article.dao.WeiBoArticle;
import com.cctv.ewservice.article.dao.WeiXinArticle;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wangcai
 * @Date Created in 2018/9/19
 */
@Service
public class ArticleService {
    @Autowired
    WeiXinArticle weiXinArticle;

    @Autowired
    WeiBoArticle weiBoArticle;

    /**
     * 判断能否转换为整数
     * @param str
     * @return
     */
    public Boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 获取两个日期之间的所有日期
     * @param startTime
     * @param endTime
     * @return
     */
    public  List<String> getBetweenDate(String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime()<=endDate.getTime()){
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate=calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println(list);
        return list;
    }

    public List<Map<String,String>> SortList(List<Map<String, String>> list) {
        Collections.sort(list, new Comparator<Map<String, String>>() {
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                Integer read1 = 0;
                Integer read2 = 0;
                if (isInteger(o1.get("read"))) {
                    read1 = Integer.valueOf(o1.get("read"));
                }
                if (isInteger(o2.get("read"))) {
                    read2 = Integer.valueOf(o2.get("read"));
                }
                return read1.compareTo(read2);
            }
        });
        Collections.reverse(list);
        return list;
    }

    /**
     * 获取满足条件的微信id列表
     * @param type
     * @param level
     * @param channel
     * @return
     */
    public List<Map<String,String>> getWeiXinIDList(int type, int level, int channel) {
        Map<String, String> map = new HashMap<String, String>();
        String queryType = null;
        String querylevel = null;
        String querychannel = null;
        switch (type)
        {
            case 0:
                queryType = "0";
                break;
            case 3:
                queryType = "央视";
                break;
            case 4:
                queryType = "央广";
                break;
            case 5:
                queryType = "国广";
                break;
            case 6:
                queryType = "人民";
                break;
            case 7:
                queryType = "新华";
                break;
            case 8:
                queryType = "其他";
                break;
            case 1:
                queryType = "1";
                break;
            case 2:
                queryType = "2";
                break;
            default:
                queryType = "0";
        }
        switch (level)
        {
            case 0:
                querylevel = "0";
                break;
            case 1:
                querylevel = "台级";
                break;
            case 2:
                querylevel = "频道级";
                break;
            case 3:
                querylevel = "栏目级";
                break;
            case 4:
                querylevel = "央视网";
                break;
            case 5:
                querylevel = "央视其他";
                break;
            case 6:
                querylevel = "人民日报";
                break;
            case 7:
                querylevel = "新华社";
                break;
            case 8:
                querylevel = "其他";
                break;
            default:
                querylevel = "0";
        }
        switch (channel)
        {
            case 0:
                querychannel = "央视网";
                break;
            case 1:
                querychannel = "CCTV-NEWS外语";
                break;
            case 2:
                querychannel = "CCTV-1综合";
                break;
            case 3:
                querychannel = "CCTV-2财经";
                break;
            case 4:
                querychannel = "CCTV-3综艺";
                break;
            case 5:
                querychannel = "CCTV-4中文国际";
                break;
            case 6:
                querychannel = "CCTV-5体育";
                break;
            case 7:
                querychannel = "CCTV-5+体育赛事";
                break;
            case 8:
                querychannel = "7";
                break;
            case 9:
                querychannel = "CCTV-8电视剧";
                break;
            case 10:
                querychannel = "CCTV-9纪录";
                break;
            case 11:
                querychannel = "CCTV-10科教";
                break;
            case 12:
                querychannel = "CCTV-11戏曲";
                break;
            case 13:
                querychannel = "CCTV-12社会与法";
                break;
            case 14:
                querychannel = "CCTV-13新闻";
                break;
            case 15:
                querychannel = "CCTV-14少儿";
                break;
            case 16:
                querychannel = "CCTV-15音乐";
                break;
            case 17:
                querychannel = "17";
                break;
            case 18:
                querychannel = "中央人民广播电台";
                break;
            case 19:
                querychannel = "中国国际广播电台";
                break;
            case 20:
                querychannel = "央视总编室";
                break;
            case 21:
                querychannel = "21";
                break;
            case 22:
                querychannel = "22";
                break;
            case 23:
                querychannel = "23";
                break;
            case 24:
                querychannel = "24";
                break;
            default:
                querychannel = "17";
        }
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = weiXinArticle.getWeiXinIdList(queryType, querylevel, querychannel);
//        System.out.println(list);
        return list;
    }

    public String getWeiXinLatestUpdate() {
        String date = weiXinArticle.getLatestUpdateDate();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", date);
        return jsonObject.toString();
    }

    /**
     * 获取所有微信文章详细信息列表
     * @param type
     * @param level
     * @param channel
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public List<Map<String,String>> getWeiXinArticleList(int type, int level, int channel, String startdate, String enddate, String keyword) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = getWeiXinIDList(type, level, channel);
        List<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
        for (Map<String, String> map :list) {
            Map<String, String>  result = new HashMap<String, String>();
            String weixinid = map.get("id");
            List<Map<String, String>> id_result_list = new ArrayList<Map<String, String>>();
            if (weiXinArticle.isIdExits(weixinid, startdate, enddate)) {
                id_result_list = weiXinArticle.getWeiXinArticleMsgid(weixinid, startdate, enddate, keyword, map);
                result_list.addAll(id_result_list);
            } else {
                id_result_list = weiXinArticle.getQingBoArticleInfo(weixinid, startdate, enddate, keyword, map);
                result_list.addAll(id_result_list);
            }
        }
        List<String> dateList = new ArrayList<String>();
        dateList = getBetweenDate(startdate, enddate);
//        System.out.println(dateList);
        List<Map<String,String>> sortDateReadList = new ArrayList<Map<String,String>>();
        for (int i = 0; i < dateList.size(); i++) {
            List<Map<String,String>> dayList = new ArrayList<Map<String,String>>();
            for (Map<String, String> map : result_list) {
                if (dateList.get(i).equals(map.get("pub_date"))) {
                    dayList.add(map);
                }
            }
            dayList = SortList(dayList);
            sortDateReadList.addAll(dayList);
        }
        return sortDateReadList;
    }


    /**
     * 获取微信文章页面数据
     * @param type
     * @param level
     * @param channel
     * @param startdate
     * @param enddate
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    public String getWeiXinArticlePageData(int type, int level, int channel, String startdate, String enddate, String keyword, int page, int size) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = getWeiXinArticleList(type, level, channel, startdate, enddate, keyword);
        int total = list.size();
        int start = (page - 1) * size;

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        for (int i = start; i< size + start; ++i) {
            if (i < total) {
                result.add(list.get(i));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("count", result.size());
        jsonObject.put("tableData", result);
        return jsonObject.toString();
    }


    /**
     * 获取所有满足条件的微博账号
     * @param type
     * @param level
     * @param channel
     * @return
     */
    public List<Map<String,String>> getWeiBoIDList(int type, int level, int channel) {
        Map<String, String> map = new HashMap<String, String>();
        String queryType = null;
        String querylevel = null;
        String querychannel = null;
        switch (type)
        {
            case 0:
                queryType = "0";
                break;
            case 3:
                queryType = "央视";
                break;
            case 4:
                queryType = "央广";
                break;
            case 5:
                queryType = "国广";
                break;
            case 6:
                queryType = "人民";
                break;
            case 7:
                queryType = "新华";
                break;
            case 8:
                queryType = "8";
                break;
            case 1:
                queryType = "1";
                break;
            case 2:
                queryType = "2";
                break;
            default:
                queryType = "0";
        }
        switch (level)
        {
            case 0:
                querylevel = "0";
                break;
            case 1:
                querylevel = "台级";
                break;
            case 2:
                querylevel = "频道级";
                break;
            case 3:
                querylevel = "栏目级";
                break;
            case 4:
                querylevel = "央视网";
                break;
            case 5:
                querylevel = "其他";
                break;
            case 6:
                querylevel = "人民日报";
                break;
            case 7:
                querylevel = "新华社";
                break;
            case 8:
                querylevel = "8";
                break;
            default:
                querylevel = "0";
        }
        switch (channel)
        {
            case 0:
                querychannel = "央视网";
                break;
            case 1:
                querychannel = "外语频道";
                break;
            case 2:
                querychannel = "CCTV-1综合";
                break;
            case 3:
                querychannel = "CCTV-2财经";
                break;
            case 4:
                querychannel = "CCTV-3综艺";
                break;
            case 5:
                querychannel = "CCTV-4中文国际";
                break;
            case 6:
                querychannel = "CCTV-5体育";
                break;
            case 7:
                querychannel = "CCTV-5+体育赛事";
                break;
            case 8:
                querychannel = "CCTV-7军事农业";
                break;
            case 9:
                querychannel = "CCTV-8电视剧";
                break;
            case 10:
                querychannel = "CCTV-9纪录";
                break;
            case 11:
                querychannel = "CCTV-10科教";
                break;
            case 12:
                querychannel = "CCTV-11戏曲";
                break;
            case 13:
                querychannel = "CCTV-12社会与法";
                break;
            case 14:
                querychannel = "CCTV-13新闻";
                break;
            case 15:
                querychannel = "CCTV-14少儿";
                break;
            case 16:
                querychannel = "CCTV-15音乐";
                break;
            case 17:
                querychannel = "17";
                break;
            case 18:
                querychannel = "中央人民广播电台";
                break;
            case 19:
                querychannel = "中国国际广播电台";
                break;
            case 20:
                querychannel = "总编室";
                break;
            case 21:
                querychannel = "21";
                break;
            case 22:
                querychannel = "22";
                break;
            case 23:
                querychannel = "23";
                break;
            case 24:
                querychannel = "24";
                break;
            case 25:
                querychannel = "CCTV-6电影";
                break;
            default:
                querychannel = "17";
        }
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = weiBoArticle.getWeiBoIdList(queryType, querylevel, querychannel);
//        System.out.println(list);
        return list;
    }

    /**
     * 获取所有微博账号全部文章列表
     * @param type
     * @param level
     * @param channel
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public List<Map<String,String>> getWeiBoArticleList(int type, int level, int channel, String startdate, String enddate, String keyword) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = getWeiBoIDList(type, level, channel);
        List<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
        for (Map<String, String> map :list) {
            Map<String, String>  result = new HashMap<String, String>();
            String weriboid = map.get("id");
            List<Map<String, String>> id_result_list = new ArrayList<Map<String, String>>();
            if (weiBoArticle.isIdExits(weriboid, startdate, enddate)) {
                id_result_list = weiBoArticle.getWeiBoArticleMid(weriboid, startdate, enddate, keyword, map);
                result_list.addAll(id_result_list);
            } else {
                id_result_list = weiBoArticle.getQingBoWeiBoArticleInfo(weriboid, startdate, enddate, keyword, map);
                result_list.addAll(id_result_list);
            }
        }
        List<String> dateList = new ArrayList<String>();
        dateList = getBetweenDate(startdate, enddate);
        List<Map<String,String>> sortDateReadList = new ArrayList<Map<String,String>>();
        for (int i = 0; i < dateList.size(); i++) {
            List<Map<String,String>> dayList = new ArrayList<Map<String,String>>();
            for (Map<String, String> map : result_list) {
                if (dateList.get(i).equals(map.get("date"))) {
                    dayList.add(map);
                }
            }
            dayList = SortList(dayList);
            sortDateReadList.addAll(dayList);
        }
        return sortDateReadList;
    }


    public String getWeiBoAccountPageData(int type, int level, int channel, String startdate, String enddate, String keyword, int page, int size) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = getWeiBoArticleList(type, level, channel, startdate, enddate, keyword);
        int total = list.size();
        int start = (page - 1) * size;

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        for (int i = start; i< size + start; ++i) {
            if (i < total) {
                result.add(list.get(i));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("count", result.size());
        jsonObject.put("tableData", result);
        return jsonObject.toString();
    }
}
