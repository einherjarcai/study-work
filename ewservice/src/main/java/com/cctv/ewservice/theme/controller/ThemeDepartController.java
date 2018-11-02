package com.cctv.ewservice.theme.controller;

import com.cctv.ewservice.theme.service.ThemeDepartSevice;
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

@RestController
public class ThemeDepartController {
    @Autowired
    ThemeDepartSevice themeDepartSevice;

    @RequestMapping(value = "/theme", method = RequestMethod.GET)
    public String getThemeData(@RequestParam(value = "theme", required = true) String theme,
                                 @RequestParam(value = "part", required = true) String department,
                                 @RequestParam(value = "startdate", required = true) String startdate,
                                 @RequestParam(value = "enddate", required = true) String enddate,
                                 @RequestParam(value = "page", required = true) Integer page,
                                 @RequestParam(value = "size", required = true) Integer size,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 HttpServletResponse response) {
        if (keyword == null || keyword.isEmpty()) {
            keyword = null;
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        return themeDepartSevice.getThemePageData(theme, department, startdate, enddate, keyword, page, size);
    }

    @RequestMapping(value = "/theme/download", method = RequestMethod.GET)
    public void getThemeDownLoad(@RequestParam(value = "theme", required = true) String theme,
                                       @RequestParam(value = "part", required = true) String department,
                                       @RequestParam(value = "startdate", required = true) String startdate,
                                       @RequestParam(value = "enddate", required = true) String enddate,
                                       @RequestParam(value = "keyword", required = false) String keyword,
                                       HttpServletResponse response) throws IOException {
        if (keyword == null || keyword.isEmpty()) {
            keyword = null;
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = themeDepartSevice.getThemeDownLoadData(theme, department, startdate, enddate, keyword);
//        System.out.println(list);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("主题主线");
        int rowNum = 1;
        String[] headers = { "序号", "账号名", "账号归属", "所属频道", "主题", "系类", "发稿日期",  "URL", "标题/内容", "阅读量", "转发量", "评论量", "点赞量"};
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
            row1.createCell(0).setCellValue(i+1);
            row1.createCell(1).setCellValue(map.get("name"));
            if (map.get("type") == null || map.get("type") == "null" || map.get("type") == "") {
                row1.createCell(2).setCellValue(" ");
            } else {
                row1.createCell(2).setCellValue(map.get("type"));
            }
            if (map.get("channel") == null || map.get("channel") == "null" || map.get("channel") == "") {
                row1.createCell(3).setCellValue(" ");
            } else {
                row1.createCell(3).setCellValue(map.get("channel"));
            }
            row1.createCell(4).setCellValue(map.get("theme"));
            row1.createCell(5).setCellValue(map.get("department"));
            row1.createCell(6).setCellValue(map.get("date"));
            row1.createCell(7).setCellValue(map.get("url"));
            row1.createCell(8).setCellValue(map.get("title"));
            if (themeDepartSevice.isInteger(map.get("read"))) {
                row1.createCell(9).setCellValue(Integer.valueOf(map.get("read")));
            } else {
                row1.createCell(9).setCellValue(0);
            }
            if (themeDepartSevice.isInteger(map.get("repost"))) {
                row1.createCell(10).setCellValue(Integer.valueOf(map.get("repost")));
            } else {
                row1.createCell(10).setCellValue(0);
            }
            if (themeDepartSevice.isInteger(map.get("comment"))) {
                row1.createCell(11).setCellValue(Integer.valueOf(map.get("comment")));
            } else {
                row1.createCell(11).setCellValue(0);
            }
            if (themeDepartSevice.isInteger(map.get("like"))) {
                row1.createCell(12).setCellValue(Integer.valueOf(map.get("like")));
            } else {
                row1.createCell(12).setCellValue(0);
            }
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
