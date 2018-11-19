package com.cctv.ewservice.article.service;

import com.cctv.ewservice.article.dao.BrandAccuSecDao;
import com.cctv.ewservice.article.dao.BrandDao;
import com.cctv.ewservice.article.dao.BrandDepartDayDao;
import com.cctv.ewservice.article.dao.BrandSecDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.type.ArrayType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BrandService {
    @Autowired
    BrandDao brandDao;
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    BrandSecDao brandSecDao;
    @Autowired
    BrandAccuSecDao brandAccuSecDao;
    @Autowired
    BrandDepartDayDao brandDepartDayDao;

    /**
     * 微信账号及属性
     * @param type
     * @param channel
     * @return
     */
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
            case 20:
                querychannel = "20";
                break;
            default:
                querychannel = "17";
        }
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = brandDao.getWeiXinIdList(queryType, querychannel);
        return list;
    }

    /**
     * 微博账号及属性
     * @param type
     * @param channel
     * @return
     */
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
            case 20:
                querychannel = "20";
                break;
            default:
                querychannel = "17";
        }
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = brandDao.getWeiBoIdList(queryType, querychannel);
        return list;
    }

    /**
     * 品牌列表
     * @param key
     * @return
     */
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
                keywordList.add("时政快讯");
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
            case 12:
                keywordList.add("时政快讯");
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
                keywordList.add("时政快讯");
        }
        return keywordList;
    }

    /**
     * 是否能转化为整数
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
     * 按照阅读量排序
     * @param list
     * @return
     */
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


    /**
     * 微博所有数据按照发布时间排序
     * @param list
     * @return
     */
    public List<Map<String,String>> TimeSortList(List<Map<String, String>> list) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Collections.sort(list, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                Date date1;
                Date date2;
                try {
                    date1 = format.parse(o1.get("date"));
                    date2 = format.parse(o2.get("date"));
                } catch (ParseException e) {
                    return 0;
                }
                if (date1.before(date2)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        Collections.reverse(list);
        return list;
    }

    /**
     * 微信最终所有结果按照日期分组，并且将每日数据按照阅读量排序
     * @param list
     * @param dateList
     * @return
     */
    public List<Map<String, String>> DateSortList(List<Map<String, String>> list, List<String> dateList) {
        List<Map<String,String>> sortDateReadList = new ArrayList<Map<String,String>>();
        for (int i = 0; i < dateList.size(); i++) {
            List<Map<String,String>> dayList = new ArrayList<Map<String,String>>();
            for (Map<String, String> map : list) {
                if (dateList.get(i).equals(map.get("pub_date"))) {
                    dayList.add(map);
                }
            }
            // 按照阅读量排序
            dayList = ReadSortList(dayList);
            sortDateReadList.addAll(dayList);
        }
        return sortDateReadList;
    }

    /**
     * 获取两个日期之间的所有日期列表
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
        return list;
    }

    /**
     * 品牌分天分条所有数据
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

        List<String> wxList = new ArrayList<String>();
        wxList = fileUploadService.getWeiXinIDList(type, channel);
        List<String> wbList = new ArrayList<String>();
        wbList = fileUploadService.getWeiBoIDList(type, channel);

        List<String> wxQingBoList = new ArrayList<String>();
        List<String> wbQingBoList = new ArrayList<String>();
        for (String id : wxList) {
            if (!brandSecDao.isWxIdExit(id, startdate, enddate)) {
                wxQingBoList.add(id);
            }
        }
        for (String id : wbList) {
            if (!brandSecDao.isWbIdExits(id, startdate, enddate)) {
                wbQingBoList.add(id);
            }
        }
//        System.out.println("品牌后: " + System.currentTimeMillis());
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        for (int i = 0; i < keywordList.size(); ++i) {
            List<Map<String, String>> wx_result = new ArrayList<Map<String, String>>();
            List<Map<String, String>> wb_result = new ArrayList<Map<String, String>>();

            List<Map<String, String>> wx_bigdata_list = new ArrayList<Map<String, String>>();
            wx_bigdata_list = brandSecDao.getWeiXinAllMsgidInfo(wxList, startdate, enddate, keywordList.get(i), wxidList);
            wx_result.addAll(wx_bigdata_list);
            if (wxQingBoList.size() > 0) {
                List<Map<String, String>> wx_qb_list = new ArrayList<Map<String, String>>();
                wx_qb_list = brandSecDao.getWeiXinQingBoArticleInfo(wxQingBoList, startdate, enddate, keywordList.get(i), wxidList);
                wx_result.addAll(wx_qb_list);
            }
            wx_result = DateSortList(wx_result, dateList);

            List<Map<String, String>> wb_bigdata_list = new ArrayList<Map<String, String>>();
            wb_bigdata_list = brandSecDao.getWeiBoArticleMid(wbList, startdate, enddate, keywordList.get(i), wbidList);
            wb_result.addAll(wb_bigdata_list);

            if (wbQingBoList.size() > 0) {
                List<Map<String, String>> wb_qb_list = new ArrayList<Map<String, String>>();
                wb_qb_list = brandSecDao.getQingBoWeiBoArticleInfo(wbQingBoList, startdate, enddate, keywordList.get(i), wxidList);
                wb_result.addAll(wb_qb_list);
            }
            wb_result = TimeSortList(wb_result);

            list.addAll(wx_result);
            list.addAll(wb_result);
        }
        return list;
    }

    /**
     * 品牌累计所有数据
     * @param type
     * @param channel
     * @param key
     * @param startdate
     * @param enddate
     * @return
     */
    public List<Map<String, String>> getBrandAccuSecData(int type, int channel, int key, String startdate, String enddate) {
        List<String> wbidList = new ArrayList<String>();
        wbidList = fileUploadService.getWeiBoIDList(type, channel);
        List<String> wxidList = new ArrayList<String>();
        wxidList = fileUploadService.getWeiXinIDList(type, channel);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        List<String> keywordList = new ArrayList<String>();
        keywordList = getKeyword(key);

        List<String> dateList = new ArrayList<String>();
        dateList = getBetweenDate(startdate, enddate);

        List<String> wxQingBoList = new ArrayList<String>();
        List<String> wbQingBoList = new ArrayList<String>();
        for (String id : wxidList) {
            if (!brandSecDao.isWxIdExit(id, startdate, enddate)) {
                wxQingBoList.add(id);
            }
        }
        for (String id : wbidList) {
            if (!brandSecDao.isWbIdExits(id, startdate, enddate)) {
                wbQingBoList.add(id);
            }
        }

        for (int i = 0; i < keywordList.size(); ++i) {
            for (int j = 0; j < dateList.size(); ++j) {
                Map<String, String> result = new HashMap<String, String>();
                int weixinCount = 0;
                int weixinRead = 0;
                int weixinShare = 0;
                int weixinHd = 0;
                int weiboCount = 0;
                int weiboRead = 0;
                int weiboShare = 0;
                int weiboHd = 0;
                int weiboVideo = 0;

                List<Integer> wx_bigdata = new ArrayList<Integer>();
                wx_bigdata = brandDepartDayDao.getWeiXinAccuAllMsgidInfo(wxidList, dateList.get(j), dateList.get(j), keywordList.get(i));
                if (wx_bigdata.size() == 4) {
                    weixinCount += wx_bigdata.get(0);
                    weixinRead += wx_bigdata.get(1);
                    weixinShare += wx_bigdata.get(2);
                    weixinHd += wx_bigdata.get(3);
                }
                if (wxQingBoList.size() > 0) {
                    List<Integer> wx_qb = new ArrayList<Integer>();
                    wx_qb = brandDepartDayDao.getWeiXinQingBoArticleInfo(wxQingBoList, dateList.get(j), dateList.get(j), keywordList.get(i));
                    if (wx_qb.size() == 4) {
                        weixinCount += wx_qb.get(0);
                        weixinRead += wx_qb.get(1);
                        weixinShare += wx_qb.get(2);
                        weixinHd += wx_qb.get(3);
                    }
                }

                List<Integer> wb_bigdata = new ArrayList<Integer>();
                wb_bigdata = brandDepartDayDao.getWeiBoAccuAllMsgidInfo(wbidList, dateList.get(j), dateList.get(j), keywordList.get(i));
                if (wb_bigdata.size() == 5) {
                    weiboCount += wb_bigdata.get(0);
                    weiboRead += wb_bigdata.get(1);
                    weiboShare += wb_bigdata.get(2);
                    weiboHd += wb_bigdata.get(3);
                    weiboVideo += wb_bigdata.get(4);
                }
                if (wbQingBoList.size() > 0) {
                    List<Integer> wb_qb = new ArrayList<Integer>();
                    wb_qb = brandDepartDayDao.getQingBoWeiBoArticleInfo(wbQingBoList, dateList.get(j), dateList.get(j), keywordList.get(i));
                    if (wb_qb.size() == 5) {
                        weiboCount += wb_qb.get(0);
                        weiboRead += wb_qb.get(1);
                        weiboShare += wb_qb.get(2);
                        weiboHd += wb_qb.get(3);
                        weiboVideo += wb_qb.get(4);
                    }
                }

                result.put("date", dateList.get(j));
                result.put("brand", keywordList.get(i));
                result.put("wx_count", String.valueOf(weixinCount));
                result.put("wx_read", String.valueOf(weixinRead));
                result.put("wx_share", String.valueOf(weixinShare));
                result.put("wx_hd", String.valueOf(weixinHd));
                result.put("wb_count", String.valueOf(weiboCount));
                result.put("wb_read", String.valueOf(weiboRead));
                result.put("wb_share", String.valueOf(weiboShare));
                result.put("wb_hd", String.valueOf(weiboHd));
                result.put("wb_video", String.valueOf(weiboVideo));
                list.add(result);
            }
            brandDepartDayDao.setMsgMidListToNull();
        }
        return list;
    }

    /**
     * 品牌分天页面数据
     * @param type
     * @param channel
     * @param key
     * @param startdate
     * @param enddate
     * @param page
     * @param size
     * @return
     */
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

    /**
     * 品牌累计页面数据
     * @param type
     * @param channel
     * @param key
     * @param startdate
     * @param enddate
     * @param page
     * @param size
     * @return
     */
    public String getBrandAccuPageData(int type, int channel, int key, String startdate, String enddate, int page, int size) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = getBrandAccuSecData(type, channel, key, startdate, enddate);

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
