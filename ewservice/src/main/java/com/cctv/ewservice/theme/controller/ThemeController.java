package com.cctv.ewservice.theme.controller;

import com.cctv.ewservice.theme.service.ThemeService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangcai
 * @Date Created in 2018/9/22
 */
@RestController
@RequestMapping("cctv/theme")
public class ThemeController {
    @Autowired
    ThemeService themeService;

    @RequestMapping(value = "/weixin", method = RequestMethod.GET)
    public String getWeixinTheme(@RequestParam(value = "theme", required = true) String theme,
                                   @RequestParam(value = "depart", required = true) String department,
                                   @RequestParam(value = "startdate", required = true) String startdate,
                                   @RequestParam(value = "enddate", required = true) String enddate,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "size", required = false) Integer size,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   HttpServletResponse response) {
        if (keyword == null || keyword.isEmpty()) {
            keyword = null;
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        return themeService.getWeiXinThemePageData(theme, department, startdate, enddate, keyword, page, size);
    }

    @RequestMapping(value = "/weibo", method = RequestMethod.GET)
    public String getWeiBoTheme(@RequestParam(value = "theme", required = true) String theme,
                                   @RequestParam(value = "depart", required = true) String department,
                                   @RequestParam(value = "startdate", required = true) String startdate,
                                   @RequestParam(value = "enddate", required = true) String enddate,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "size", required = false) Integer size,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   HttpServletResponse response) {
        if (keyword == null || keyword.isEmpty()) {
            keyword = null;
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        return themeService.getWeiBoThemePageData(theme, department, startdate, enddate, keyword, page, size);
    }


    @RequestMapping(value = "/weixin/download", method = RequestMethod.GET)
    public void getWeiXinThemeDownLoad(@RequestParam(value = "theme", required = true) String theme,
                                      @RequestParam(value = "depart", required = true) String department,
                                      @RequestParam(value = "startdate", required = true) String startdate,
                                      @RequestParam(value = "enddate", required = true) String enddate,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      HttpServletResponse response) throws IOException {
        if (keyword == null || keyword.isEmpty()) {
            keyword = null;
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = themeService.getWeiXinThemeDownLoadData(theme, department, startdate, enddate, keyword);
//        System.out.println(list);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("微博");
        int rowNum = 1;
        String[] headers = { "序号", "账号名", "账号归属", "账号级别", "所属频道", "发稿日期", "是否头条", "URL", "标题", "阅读量", "转发量", "收藏量", "点赞量"};
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            map = list.get(i);
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(String.valueOf(i+1));
            row1.createCell(1).setCellValue(map.get("name"));
            row1.createCell(2).setCellValue(map.get("type"));
            row1.createCell(3).setCellValue(map.get("level"));
            row1.createCell(4).setCellValue(map.get("channel"));
            row1.createCell(5).setCellValue(map.get("pub_date"));
            row1.createCell(6).setCellValue(map.get("istop"));
            row1.createCell(7).setCellValue(map.get("url"));
            row1.createCell(8).setCellValue(map.get("title"));
            row1.createCell(9).setCellValue(map.get("read"));
            row1.createCell(10).setCellValue(map.get("share"));
            row1.createCell(11).setCellValue(map.get("add"));
            row1.createCell(12).setCellValue(map.get("like"));
            rowNum++;
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-disposition", "attachment;filename=kpi.xls" );
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }



    @RequestMapping(value = "/weibo/download", method = RequestMethod.GET)
    public void getWeiBoThemeDownLoad(@RequestParam(value = "theme", required = true) String theme,
                                      @RequestParam(value = "depart", required = true) String department,
                                      @RequestParam(value = "startdate", required = true) String startdate,
                                      @RequestParam(value = "enddate", required = true) String enddate,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      HttpServletResponse response) throws IOException {
        if (keyword == null || keyword.isEmpty()) {
            keyword = null;
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = themeService.getWeiBoThemeDownLoadData(theme, department, startdate, enddate, keyword);
//        System.out.println(list);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("微博");
        int rowNum = 1;
        String[] headers = { "序号", "账号名", "账号归属", "账号级别", "所属频道", "发稿日期", "是否原创", "URL", "内容", "阅读量", "转发量", "评论量", "点赞量"};
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            map = list.get(i);
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(String.valueOf(i+1));
            row1.createCell(1).setCellValue(map.get("name"));
            row1.createCell(2).setCellValue(map.get("type"));
            row1.createCell(3).setCellValue(map.get("level"));
            row1.createCell(4).setCellValue(map.get("channel"));
            row1.createCell(5).setCellValue(map.get("date"));
            row1.createCell(6).setCellValue(map.get("isori"));
            row1.createCell(7).setCellValue(map.get("url"));
            row1.createCell(8).setCellValue(map.get("weibo_text"));
            row1.createCell(9).setCellValue(map.get("read"));
            row1.createCell(10).setCellValue(map.get("repost"));
            row1.createCell(11).setCellValue(map.get("comment"));
            row1.createCell(12).setCellValue(map.get("like"));
            rowNum++;
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-disposition", "attachment;filename=kpi.xls" );
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

}
