package com.cctv.ewservice.theme.service;

import com.cctv.ewservice.theme.dao.ThemeDepartDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ThemeDepartSevice {
    @Autowired
    ThemeDepartDao themeDepartDao;

    public Boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getThemePageData(String theme, String department, String startdate, String enddate, String keyword, int page, int size) {
        int total = themeDepartDao.getThemeTotal(theme, department, startdate, enddate, keyword);
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = themeDepartDao.getThemeData(theme, department, startdate, enddate, keyword, page, size);
        int count = list.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("count", count);
        jsonObject.put("tableData", list);
        return jsonObject.toString();
    }


    public List<Map<String, String>> getThemeDownLoadData(String theme, String department, String startdate, String enddate, String keyword) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = themeDepartDao.getThemeDownLoadData(theme, department, startdate, enddate, keyword);
        return list;
    }
}
