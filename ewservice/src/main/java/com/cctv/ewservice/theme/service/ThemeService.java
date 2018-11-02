package com.cctv.ewservice.theme.service;

import com.cctv.ewservice.theme.dao.WeiBoThemeInfoDao;
import com.cctv.ewservice.theme.dao.WeiXinThemeInfoDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangcai
 * @Date Created in 2018/9/22
 */
@Service
public class ThemeService {
    @Autowired
    WeiXinThemeInfoDao weiXinThemeInfoDao;

    @Autowired
    WeiBoThemeInfoDao weiBoThemeInfoDao;

    /**
     * 微信主题页面数据
     * @param theme
     * @param department
     * @param startdate
     * @param enddate
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    public String getWeiXinThemePageData(String theme, String department, String startdate, String enddate, String keyword, int page, int size) {
        int total = weiXinThemeInfoDao.getWeiXinThemeTotal(theme, department, startdate, enddate, keyword);
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = weiXinThemeInfoDao.getWeiXinThemeData(theme, department, startdate, enddate, keyword, page, size);
        int count = list.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("count", count);
        jsonObject.put("tableDate", list);
        return jsonObject.toString();
    }

    /**
     * 微博主题页面数据
     * @param theme
     * @param department
     * @param startdate
     * @param enddate
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    public String getWeiBoThemePageData(String theme, String department, String startdate, String enddate, String keyword, int page, int size) {
        int total = weiBoThemeInfoDao.getWeiBoThemeTotal(theme, department, startdate, enddate, keyword);
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = weiBoThemeInfoDao.getWeiBoThemeData(theme, department, startdate, enddate, keyword, page, size);
        int count = list.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("count", count);
        jsonObject.put("tableDate", list);
        return jsonObject.toString();
    }

    /**
     * 微信主题下载数据
     * @param theme
     * @param department
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public List<Map<String, String>> getWeiXinThemeDownLoadData(String theme, String department, String startdate, String enddate, String keyword) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = weiXinThemeInfoDao.getWeiXinThemeDownLoadData(theme, department, startdate, enddate, keyword);
        return list;
    }

    /**
     * 微博主题下载数据
     * @param theme
     * @param department
     * @param startdate
     * @param enddate
     * @param keyword
     * @return
     */
    public List<Map<String, String>> getWeiBoThemeDownLoadData(String theme, String department, String startdate, String enddate, String keyword) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = weiBoThemeInfoDao.getWeiBoThemeDownLoadData(theme, department, startdate, enddate, keyword);
        return list;
    }
}
