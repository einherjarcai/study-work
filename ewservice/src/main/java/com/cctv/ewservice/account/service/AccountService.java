package com.cctv.ewservice.account.service;

import com.cctv.ewservice.account.dao.WeiBoAccountInfoDao;
import com.cctv.ewservice.account.dao.WeiXinAccountInfoDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wangcai
 * @Date Created in 2018/9/18
 */
@Service
public class AccountService {
    @Autowired
    WeiXinAccountInfoDao weiXinAccountInfoDao;

    @Autowired
    WeiBoAccountInfoDao weiBoAccountInfoDao;

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
     * 获取满足要求的微信id列表
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
//        System.out.println("type: " + type);
//        System.out.println("level: " + level);
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
        list = weiXinAccountInfoDao.getWeiXinIdList(queryType, querylevel, querychannel);
//        System.out.println(list);
        return list;
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

    /**
     * 按结果 List<Map<String,String>> 按照阅读数排序
     * @param list
     * @return
     */
    public List<Map<String,String>> SortList(List<Map<String, String>> list) {
//        System.out.println("排序前：" + list);
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
//        System.out.println("排序后：" + list);
        Collections.reverse(list);
        return list;
    }


    public String getWeiXinLatestUpdate() {
        String date = weiXinAccountInfoDao.getLatestUpdateDate();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", date);
        return jsonObject.toString();
    }

    /**
     * 获取累计数据
     * @param idList
     * @param dataList
     * @param stardate
     * @param enddate
     * @return
     */
    public List<Map<String,String>> getWeiXinAccuData(List<Map<String, String>> idList, List<Map<String, String>> dataList, String stardate, String enddate) {
        List<Map<String, String>> accu_result_list = new ArrayList<Map<String, String>>();
        for (Map<String, String> id : idList) {
            int articleCount = 0;
            int readCount = 0;
            int hdCount = 0;
            String flowers = "-";
            Map<String, String> accuMap = new HashMap<String, String>();
            for (Map<String, String> map : dataList) {
                if (id.get("name").equals(map.get("name"))) {
                    if (isInteger(map.get("article_count"))) {
                        articleCount += Integer.valueOf(map.get("article_count"));
                    } else {
                        articleCount += 0;
                    }
                    if (isInteger(map.get("read"))) {
                        readCount += Integer.valueOf(map.get("read"));
                    } else {
                        readCount += 0;
                    }
                    if (isInteger(map.get("hd"))) {
                        hdCount += Integer.valueOf(map.get("hd"));
                    } else {
                        hdCount += 0;
                    }
                    accuMap.putAll(map);
                    if (!"-".equals(map.get("flowers"))) {
                        flowers = map.get("flowers");
                    }
                }
            }
            accuMap.put("flowers", flowers);
            accuMap.put("article_count", String.valueOf(articleCount));
            accuMap.put("read", String.valueOf(readCount));
            accuMap.put("hd", String.valueOf(hdCount));
            accuMap.put("date", stardate + "~" + enddate);
            accu_result_list.add(accuMap);
        }
        accu_result_list = SortList(accu_result_list);
        return accu_result_list;
    }

    /**
     * 获取微信账号所有数据
     * @param type
     * @param level
     * @param channel
     * @param startdate
     * @param enddate
     * @return
     */
    public List<Map<String,String>> getWeiXinAccountList(int type, int level, int channel, String startdate, String enddate, int accu) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = getWeiXinIDList(type, level, channel);
        List<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
        List<String> datelist = new ArrayList<String>();
        datelist = getBetweenDate(startdate, enddate);
        for (int i = 0; i < datelist.size(); i++){
            List<Map<String,String>> id_result_list = new ArrayList<Map<String,String>>();
            for (Map<String, String> map :list) {
                Map<String, String>  result = new HashMap<String, String>();
                List<String> flowerlist = new ArrayList<String>();
                flowerlist = weiXinAccountInfoDao.getFlowerById(map.get("id"), datelist.get(i));
                if ((flowerlist.get(1)).equals("1")) {
                    String flowers = flowerlist.get(0).substring(0, flowerlist.get(0).indexOf("."));
                    int article_count = weiXinAccountInfoDao.getArticleCount(map.get("id"), datelist.get(i));
                    int read = weiXinAccountInfoDao.getWeiXinAccountRead(map.get("id"), datelist.get(i));
                    String share = weiXinAccountInfoDao.getShareCount(map.get("id"), datelist.get(i));
                    share = share.substring(0, share.indexOf("."));
                    if (share.equals("0.0")) {
                        share = String.valueOf(0);
                    }
                    String like = weiXinAccountInfoDao.getLikeCount(map.get("id"), datelist.get(i));
                    if (like == null) {
                        like = String.valueOf(0);
                    }
                    int hd = Integer.valueOf(share) + Integer.valueOf(like);
                    result.put("date", datelist.get(i));
                    result.put("flowers", flowers);
                    result.put("article_count", String.valueOf(article_count));
                    result.put("read", String.valueOf(read));
                    result.put("hd", String.valueOf(hd));
                    result.putAll(map);
                    id_result_list.add(result);
                } else {
                    result = weiXinAccountInfoDao.getQingBoData(map.get("id"), datelist.get(i));
                    result.put("date", datelist.get(i));
                    result.putAll(map);
                    id_result_list.add(result);
                }
            }
            if (accu == 0) {
                id_result_list = SortList(id_result_list);
            }
            result_list.addAll(id_result_list);
        }
        if (accu == 1) {
            result_list = getWeiXinAccuData(list, result_list, startdate, enddate);
        }
        return result_list;
    }


    /**
     * 获取微信页面数据
     * @param type
     * @param level
     * @param channel
     * @param startdate
     * @param enddate
     * @param page
     * @param size
     * @return
     */
    public String getWeiXinAccountPageData(int type, int level, int channel, String startdate, String enddate, int page, int size, int accu) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = getWeiXinAccountList(type, level, channel, startdate, enddate, accu);
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
     * 获取所有满足要求的微博账号列表
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
        list = weiBoAccountInfoDao.getWeiBoIdList(queryType, querylevel, querychannel);
//        System.out.println(list);
        return list;
    }




    /**
     * 获取所有微博账号详细信息列表
     * @param type
     * @param level
     * @param channel
     * @param startdate
     * @param enddate
     * @return
     */
    public List<Map<String,String>> getWeiBoAccountList(int type, int level, int channel, String startdate, String enddate, int accu) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = getWeiBoIDList(type, level, channel);
        List<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
        List<String> datelist = new ArrayList<String>();
        datelist = getBetweenDate(startdate, enddate);
        for (int i = 0; i < datelist.size(); ++i) {
            List<Map<String,String>> id_result_list = new ArrayList<Map<String,String>>();
            for (Map<String, String> map :list) {
                Map<String, String>  result = new HashMap<String, String>();
                String flowers = weiBoAccountInfoDao.getWeiboFlowerByID(map.get("id"), datelist.get(i));
                if (flowers != null) {
                    List<String> aclist = weiBoAccountInfoDao.getWeiBoArticleInfo(map.get("id"), datelist.get(i));
                    result.put("date", datelist.get(i));
                    result.put("flowers", flowers);
                    result.put("article_count", aclist.get(0));
                    result.put("read", aclist.get(1));
                    result.put("live", aclist.get(2));
                    result.put("video", aclist.get(3));
                    result.put("hd", aclist.get(4));
                    result.putAll(map);
                    id_result_list.add(result);
                } else {
                    result = weiBoAccountInfoDao.getQingBoWeiBoInfo(map.get("id"), datelist.get(i));
                    result.put("date", datelist.get(i));
                    result.putAll(map);
                    id_result_list.add(result);
                }
            }
            if (accu == 0) {
                id_result_list = SortList(id_result_list);
            }
            result_list.addAll(id_result_list);
        }
        if (accu == 1) {
            result_list = getWeiBoAccuData(list, result_list, startdate, enddate);
        }
        return result_list;
    }


    public List<Map<String,String>> getWeiBoAccuData(List<Map<String, String>> idList, List<Map<String, String>> dataList, String stardate, String enddate) {
        List<Map<String, String>> accu_result_list = new ArrayList<Map<String, String>>();
        for (Map<String, String> id : idList) {
            int articleCount = 0;
            int readCount = 0;
            int liveCount = 0;
            int videoCount = 0;
            int hdCount = 0;
            String flowers = "-";
            Map<String, String> accuMap = new HashMap<String, String>();
            for (Map<String, String> map : dataList) {
                if (id.get("name").equals(map.get("name"))) {
                    if (isInteger(map.get("article_count"))) {
                        articleCount += Integer.valueOf(map.get("article_count"));
                    } else {
                        articleCount += 0;
                    }
                    if (isInteger(map.get("read"))) {
                        readCount += Integer.valueOf(map.get("read"));
                    } else {
                        readCount += 0;
                    }
                    if (isInteger(map.get("hd"))) {
                        hdCount += Integer.valueOf(map.get("hd"));
                    } else {
                        hdCount += 0;
                    }
                    if (isInteger(map.get("live"))) {
                        liveCount += Integer.valueOf(map.get("live"));
                    } else {
                        liveCount += 0;
                    }
                    if (isInteger(map.get("video"))) {
                        videoCount += Integer.valueOf(map.get("video"));
                    } else {
                        videoCount += 0;
                    }
                    accuMap.putAll(map);
                    if (!"-".equals(map.get("flowers"))) {
                        flowers = map.get("flowers");
                    }
                }
            }
            accuMap.put("flowers", flowers);
            accuMap.put("article_count", String.valueOf(articleCount));
            accuMap.put("read", String.valueOf(readCount));
            accuMap.put("hd", String.valueOf(hdCount));
            accuMap.put("live", String.valueOf(liveCount));
            accuMap.put("video", String.valueOf(videoCount));
            accuMap.put("date", stardate + "~" + enddate);
            accu_result_list.add(accuMap);
        }
        accu_result_list = SortList(accu_result_list);
        return accu_result_list;
    }

    /**
     * 获取微博页面数据
     * @param type
     * @param level
     * @param channel
     * @param startdate
     * @param enddate
     * @param page
     * @param size
     * @return
     */
    public String getWeiBoAccountPageData(int type, int level, int channel, String startdate, String enddate, int page, int size, int accu) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = getWeiBoAccountList(type, level, channel, startdate, enddate, accu);
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
