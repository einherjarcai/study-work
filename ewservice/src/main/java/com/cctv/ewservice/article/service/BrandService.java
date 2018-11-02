package com.cctv.ewservice.article.service;

import com.cctv.ewservice.article.dao.BrandDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.type.ArrayType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BrandService {
    @Autowired
    BrandDao brandDao;

    public List<Map<String,String>> getWeiXinIDList(int type, int channel) {
        Map<String, String> map = new HashMap<String, String>();
        String queryType = null;
        String querychannel = null;
        switch (type)
        {
            case 3:
                queryType = "央视";
                break;
            case 4:
                queryType = "央广";
                break;
            case 5:
                queryType = "国广";
                break;
            case 1:
                queryType = "1";
                break;
            default:
                queryType = "1";
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
            default:
                querychannel = "17";
        }
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = brandDao.getWeiXinIdList(queryType, querychannel);
//        System.out.println(list);
        return list;
    }

    public List<Map<String,String>> getWeiBoIDList(int type, int channel) {
        Map<String, String> map = new HashMap<String, String>();
        String queryType = null;
        String querychannel = null;
        switch (type)
        {
            case 3:
                queryType = "央视";
                break;
            case 4:
                queryType = "央广";
                break;
            case 5:
                queryType = "国广";
                break;
            case 1:
                queryType = "1";
                break;
            default:
                queryType = "1";
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
            default:
                querychannel = "17";
        }
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = brandDao.getWeiBoIdList(queryType, querychannel);
        return list;
    }

    public List<String> getKeyword(int key) {
        List<String> keywordList = new ArrayList<String>();
        switch (key)
        {
            case 0:
                keywordList.add("央视快评");
                keywordList.add("国际锐评");
                keywordList.add("独家V观");
                keywordList.add("习声回响");
                keywordList.add("早啊新闻来了");
                keywordList.add("港媒转发央视快评");
                keywordList.add("境外媒体广泛转发");
                keywordList.add("境外媒体关注");
                keywordList.add("央视财经评论");
                keywordList.add("陆家嘴观察");
                keywordList.add("公司行业深观察");
                break;
            case 1:
                keywordList.add("央视快评");
                break;
            case 2:
                keywordList.add("国际锐评");
                break;
            case 3:
                keywordList.add("独家V观");
                break;
            case 4:
                keywordList.add("习声回响");
                break;
            case 5:
                keywordList.add("早啊新闻来了");
                break;
            case 6:
                keywordList.add("港媒转发央视快评");
                break;
            case 7:
                keywordList.add("境外媒体广泛转发");
                break;
            case 8:
                keywordList.add("境外媒体关注");
                break;
            case 9:
                keywordList.add("央视财经评论");
                break;
            case 10:
                keywordList.add("陆家嘴观察");
                break;
            case 11:
                keywordList.add("公司行业深观察");
                break;
            default:
                keywordList.add("央视快评");
                keywordList.add("国际锐评");
                keywordList.add("独家V观");
                keywordList.add("习声回响");
                keywordList.add("早啊新闻来了");
                keywordList.add("港媒转发央视快评");
                keywordList.add("境外媒体广泛转发");
                keywordList.add("境外媒体关注");
                keywordList.add("央视财经评论");
                keywordList.add("陆家嘴观察");
                keywordList.add("公司行业深观察");
        }
        return keywordList;
    }

    public Boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<Map<String,String>> ReadSortList(List<Map<String, String>> list) {
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

    public List<Map<String, String>> DateSortList(List<Map<String, String>> list, List<String> dateList) {
        List<Map<String,String>> sortDateReadList = new ArrayList<Map<String,String>>();
        for (int i = 0; i < dateList.size(); i++) {
            List<Map<String,String>> dayList = new ArrayList<Map<String,String>>();
            for (Map<String, String> map : list) {
                if (dateList.get(i).equals(map.get("pub_date"))) {
                    dayList.add(map);
                }
            }
            dayList = ReadSortList(dayList);
            sortDateReadList.addAll(dayList);
        }
        return sortDateReadList;
    }


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
        return list;
    }

    /**
     * 获取品牌所有数据
     *
     * @param type
     * @param channel
     * @param key
     * @param startdate
     * @param enddate
     * @return
     */
    public List<Map<String, String>> getAllBrandData(int type, int channel, int key, String startdate, String enddate) {
        List<Map<String, String>> wbidList = new ArrayList<Map<String, String>>();
        wbidList = getWeiBoIDList(type, channel);
        List<Map<String, String>> wxidList = new ArrayList<Map<String, String>>();
        wxidList = getWeiXinIDList(type, channel);
        List<String> keywordList = new ArrayList<String>();
        keywordList = getKeyword(key);
        List<String> dateList = new ArrayList<String>();
        dateList = getBetweenDate(startdate, enddate);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        for (int i = 0; i < keywordList.size(); ++i) {
                List<Map<String, String>> wx_result = new ArrayList<Map<String, String>>();
                List<Map<String, String>> wb_result = new ArrayList<Map<String, String>>();
                for (Map<String, String> map : wxidList) {
                    List<Map<String, String>> wxid_result_list = new ArrayList<Map<String, String>>();
                    String weixinid = map.get("id");
                    wxid_result_list = brandDao.getWeiXinAllMsgidInfo(weixinid, startdate, enddate, keywordList.get(i), map);
                    wx_result.addAll(wxid_result_list);
                }
                wx_result = DateSortList(wx_result, dateList);
                for (Map<String, String> map : wbidList) {
                    List<Map<String, String>> wbid_result_list = new ArrayList<Map<String, String>>();
                    String weiboId = map.get("id");
                    wbid_result_list = brandDao.getWeiBoArticleMid(weiboId, startdate, enddate, keywordList.get(i), map);
                    wb_result.addAll(wbid_result_list);
                }
                list.addAll(wx_result);
                list.addAll(wb_result);
        }
        return list;
    }

    public String getBrandPageData(int type, int channel, int key, String startdate, String enddate, int page, int size) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = getAllBrandData(type, channel, key, startdate, enddate);

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
