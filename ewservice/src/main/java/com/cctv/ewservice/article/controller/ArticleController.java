package com.cctv.ewservice.article.controller;

import com.cctv.ewservice.article.service.ArticleService;
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
 * @Date Created in 2018/9/19
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/weixin", method = RequestMethod.GET)
    public String getWeixinArticle(@RequestParam(value = "type", required = true) Integer type,
                                   @RequestParam(value = "level", required = true) Integer level,
                                   @RequestParam(value = "channel", required = false) Integer channel,
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
        return articleService.getWeiXinArticlePageData(type, level, channel, startdate, enddate, keyword, page, size).toString();
    }

    @RequestMapping(value = "/weixin/update", method = RequestMethod.GET)
    public String getWeixinUpdate(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return articleService.getWeiXinLatestUpdate();
    }

    @RequestMapping(value = "/weibo", method = RequestMethod.GET)
    public String getWeiBoArticle(@RequestParam(value = "type", required = true) Integer type,
                                  @RequestParam(value = "level", required = true) Integer level,
                                  @RequestParam(value = "channel", required = false) Integer channel,
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
        return articleService.getWeiBoAccountPageData(type, level, channel, startdate, enddate, keyword, page, size).toString();
    }

    @RequestMapping(value = "/weixin/download", method = RequestMethod.GET)
    public void getWeixinArticleDownLoad(@RequestParam(value = "type", required = true) Integer type,
                                         @RequestParam(value = "level", required = true) Integer level,
                                         @RequestParam(value = "channel", required = false) Integer channel,
                                         @RequestParam(value = "startdate", required = true) String startdate,
                                         @RequestParam(value = "enddate", required = true) String enddate,
                                         @RequestParam(value = "keyword", required = false) String keyword,
                                         HttpServletResponse response) throws IOException {

        if (keyword == null || keyword.isEmpty()) {
            keyword = null;
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = articleService.getWeiXinArticleList(type, level, channel, startdate, enddate, keyword);
//        System.out.println(list);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("微信");
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
            row1.createCell(0).setCellValue(i+1);
            row1.createCell(1).setCellValue(map.get("name"));
            row1.createCell(2).setCellValue(map.get("type"));
            row1.createCell(3).setCellValue(map.get("level"));
            row1.createCell(4).setCellValue(map.get("channel"));
            row1.createCell(5).setCellValue(map.get("pub_date"));
            row1.createCell(6).setCellValue(map.get("istop"));
            row1.createCell(7).setCellValue(map.get("url"));
            row1.createCell(8).setCellValue(map.get("title"));
            if (articleService.isInteger(map.get("read"))) {
                row1.createCell(9).setCellValue(Integer.valueOf(map.get("read")));
            } else {
                row1.createCell(9).setCellValue(0);
            }
            if (articleService.isInteger(map.get("share"))) {
                row1.createCell(10).setCellValue(Integer.valueOf(map.get("share")));
            } else {
                row1.createCell(10).setCellValue(0);
            }
            if (articleService.isInteger(map.get("add"))) {
                row1.createCell(11).setCellValue(Integer.valueOf(map.get("add")));
            } else {
                row1.createCell(11).setCellValue(0);
            }
            if (articleService.isInteger(map.get("like"))) {
                row1.createCell(12).setCellValue(Integer.valueOf(map.get("like")));
            } else {
                row1.createCell(12).setCellValue(0);
            }
            /*row1.createCell(9).setCellValue(Integer.valueOf(map.get("read")));
            row1.createCell(10).setCellValue(Integer.valueOf(map.get("share")));
            row1.createCell(11).setCellValue(Integer.valueOf(map.get("add")));
            row1.createCell(12).setCellValue(Integer.valueOf(map.get("like")));*/
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
    public void getWeiBoArticleDownLoad(@RequestParam(value = "type", required = true) Integer type,
                                         @RequestParam(value = "level", required = true) Integer level,
                                         @RequestParam(value = "channel", required = false) Integer channel,
                                         @RequestParam(value = "startdate", required = true) String startdate,
                                         @RequestParam(value = "enddate", required = true) String enddate,
                                         @RequestParam(value = "keyword", required = false) String keyword,
                                         HttpServletResponse response) throws IOException {

        if (keyword == null || keyword.isEmpty()) {
            keyword = null;
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = articleService.getWeiBoArticleList(type, level, channel, startdate, enddate, keyword);
//        System.out.println(list);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("微博");
        int rowNum = 1;
        String[] headers = { "序号", "账号名", "账号归属", "账号级别", "所属频道", "发稿日期", "发稿时间", "更新时间", "是否原创", "URL", "内容", "阅读量", "转发量", "评论量", "点赞量", "直播播放量", "点播播放量", "视频URL"};
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
            row1.createCell(2).setCellValue(map.get("type"));
            row1.createCell(3).setCellValue(map.get("level"));
            row1.createCell(4).setCellValue(map.get("channel"));
            row1.createCell(5).setCellValue(map.get("date"));
            row1.createCell(6).setCellValue(map.get("time"));
            row1.createCell(7).setCellValue(map.get("latest"));
            row1.createCell(8).setCellValue(map.get("isori"));
            row1.createCell(9).setCellValue(map.get("url"));
            row1.createCell(17).setCellValue(map.get("video_url"));
            row1.createCell(10).setCellValue(map.get("weibo_text"));
            if (articleService.isInteger(map.get("read"))) {
                row1.createCell(11).setCellValue(Integer.valueOf(map.get("read")));
            } else {
                row1.createCell(11).setCellValue(0);
            }
            if (articleService.isInteger(map.get("repost"))) {
                row1.createCell(12).setCellValue(Integer.valueOf(map.get("repost")));
            } else {
                row1.createCell(12).setCellValue(0);
            }
            if (articleService.isInteger(map.get("comment"))) {
                row1.createCell(13).setCellValue(Integer.valueOf(map.get("comment")));
            } else {
                row1.createCell(13).setCellValue(0);
            }
            if (articleService.isInteger(map.get("like"))) {
                row1.createCell(14).setCellValue(Integer.valueOf(map.get("like")));
            } else {
                row1.createCell(14).setCellValue(0);
            }
            if (articleService.isInteger(map.get("live"))) {
                row1.createCell(15).setCellValue(Integer.valueOf(map.get("live")));
            } else {
                row1.createCell(15).setCellValue(0);
            }
            if (articleService.isInteger(map.get("video"))) {
                row1.createCell(16).setCellValue(Integer.valueOf(map.get("video")));
            } else {
                row1.createCell(16).setCellValue(0);
            }
            /*row1.createCell(9).setCellValue(map.get("read"));
            row1.createCell(10).setCellValue(map.get("repost"));
            row1.createCell(11).setCellValue(map.get("comment"));
            row1.createCell(12).setCellValue(map.get("like"));*/
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
