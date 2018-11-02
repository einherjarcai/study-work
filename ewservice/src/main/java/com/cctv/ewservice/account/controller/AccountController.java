package com.cctv.ewservice.account.controller;

import com.cctv.ewservice.account.service.AccountService;
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
 * @Date Created in 2018/9/18
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/weixin", method = RequestMethod.GET)
    public String getWeixinAccount(@RequestParam(value = "type", required = true) Integer type,
                             @RequestParam(value = "level", required = true) Integer level,
                             @RequestParam(value = "page", required = true) Integer page,
                             @RequestParam(value = "size", required = true) Integer size,
                             @RequestParam(value = "accu", required = true) Integer accu,
                             @RequestParam(value = "channel", required = true) Integer channel,
                             @RequestParam(value = "startdate", required = true) String startdate,
                             @RequestParam(value = "enddate", required = true) String enddate,
                             HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin", "*");
//        return accountService.getWeiXinAccountList(0, 0, null, "2018-09-18").toString();
        return accountService.getWeiXinAccountPageData(type, level, channel, startdate, enddate, page, size, accu);
    }


    @RequestMapping(value = "/weixin/update", method = RequestMethod.GET)
    public String getWeixinUpdate(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return accountService.getWeiXinLatestUpdate();
    }

    @RequestMapping(value = "/weixin/download", method = RequestMethod.GET)
    public void getWeixinAccountDownLoad(@RequestParam(value = "type", required = true) Integer type,
                                         @RequestParam(value = "level", required = true) Integer level,
                                         @RequestParam(value = "accu", required = true) Integer accu,
                                         @RequestParam(value = "channel", required = false) Integer channel,
                                         @RequestParam(value = "startdate", required = true) String startdate,
                                         @RequestParam(value = "enddate", required = true) String enddate,
                                         HttpServletResponse response) throws IOException {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = accountService.getWeiXinAccountList(type, level, channel, startdate, enddate, accu);
//        System.out.println(list);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("微信");
        int rowNum = 1;
        String[] headers = { "序号", "时间", "账号名", "账号归属", "账号级别", "所属频道", "订阅数", "发稿量", "阅读量", "互动量"};
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
            row1.createCell(1).setCellValue(map.get("date"));
            row1.createCell(2).setCellValue(map.get("name"));
            row1.createCell(3).setCellValue(map.get("type"));
            row1.createCell(4).setCellValue(map.get("level"));
            row1.createCell(5).setCellValue(map.get("channel"));
            /*row1.createCell(5).setCellValue(map.get("flowers"));
            row1.createCell(6).setCellValue(map.get("article_count"));
            row1.createCell(7).setCellValue(map.get("read"));
            row1.createCell(8).setCellValue(map.get("hd"));*/
            if (accountService.isInteger(map.get("flowers"))) {
                row1.createCell(6).setCellValue(Integer.valueOf(map.get("flowers")));
            } else {
                row1.createCell(6).setCellValue(0);
            }
            if (accountService.isInteger(map.get("article_count"))) {
                row1.createCell(7).setCellValue(Integer.valueOf(map.get("article_count")));
            } else {
                row1.createCell(7).setCellValue(0);
            }
            if (accountService.isInteger(map.get("read"))) {
                row1.createCell(8).setCellValue(Integer.valueOf(map.get("read")));
            } else {
                row1.createCell(8).setCellValue(0);
            }
            if (accountService.isInteger(map.get("hd"))) {
                row1.createCell(9).setCellValue(Integer.valueOf(map.get("hd")));
            } else {
                row1.createCell(9).setCellValue(0);
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

    @RequestMapping(value = "/weibo", method = RequestMethod.GET)
    public String getWeiBoAccount(@RequestParam(value = "type", required = true) Integer type,
                                   @RequestParam(value = "level", required = true) Integer level,
                                   @RequestParam(value = "page", required = true) Integer page,
                                   @RequestParam(value = "size", required = true) Integer size,
                                   @RequestParam(value = "accu", required = true) Integer accu,
                                   @RequestParam(value = "channel", required = false) Integer channel,
                                  @RequestParam(value = "startdate", required = true) String startdate,
                                  @RequestParam(value = "enddate", required = true) String enddate,
                                   HttpServletResponse response) {
//        System.out.println(channel);
        response.setHeader("Access-Control-Allow-Origin", "*");
        return accountService.getWeiBoAccountPageData(type, level, channel, startdate, enddate, page, size, accu);
    }


    @RequestMapping(value = "/weibo/download", method = RequestMethod.GET)
    public void getWeiBoAccountDownLoad(@RequestParam(value = "type", required = true) Integer type,
                                         @RequestParam(value = "level", required = true) Integer level,
                                        @RequestParam(value = "accu", required = true) Integer accu,
                                         @RequestParam(value = "channel", required = false) Integer channel,
                                        @RequestParam(value = "startdate", required = true) String startdate,
                                        @RequestParam(value = "enddate", required = true) String enddate,
                                         HttpServletResponse response) throws IOException {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = accountService.getWeiBoAccountList(type, level, channel, startdate, enddate, accu);
//        System.out.println(list);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("微博");
        int rowNum = 1;
        String[] headers = { "序号", "时间", "账号名", "账号归属", "账号级别", "所属频道", "粉丝数", "发稿量", "阅读量", "直播视频播放量", "点播视频播放量", "互动量"};
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
            row1.createCell(1).setCellValue(map.get("date"));
            row1.createCell(2).setCellValue(map.get("name"));
            row1.createCell(3).setCellValue(map.get("type"));
            row1.createCell(4).setCellValue(map.get("level"));
            row1.createCell(5).setCellValue(map.get("channel"));
            /*row1.createCell(5).setCellValue(map.get("flowers"));
            row1.createCell(6).setCellValue(map.get("article_count"));
            row1.createCell(7).setCellValue(map.get("read"));
            row1.createCell(8).setCellValue(map.get("live"));
            row1.createCell(9).setCellValue(map.get("video"));
            row1.createCell(10).setCellValue(map.get("hd"));*/
            if (accountService.isInteger(map.get("flowers"))) {
                row1.createCell(6).setCellValue(Integer.valueOf(map.get("flowers")));
            } else {
                row1.createCell(6).setCellValue(0);
            }
            if (accountService.isInteger(map.get("article_count"))) {
                row1.createCell(7).setCellValue(Integer.valueOf(map.get("article_count")));
            } else {
                row1.createCell(7).setCellValue(0);
            }
            if (accountService.isInteger(map.get("read"))) {
                row1.createCell(8).setCellValue(Integer.valueOf(map.get("read")));
            } else {
                row1.createCell(8).setCellValue(0);
            }
            if (accountService.isInteger(map.get("live"))) {
                row1.createCell(9).setCellValue(Integer.valueOf(map.get("live")));
            } else {
                row1.createCell(9).setCellValue(0);
            }
            if (accountService.isInteger(map.get("video"))) {
                row1.createCell(10).setCellValue(Integer.valueOf(map.get("video")));
            } else {
                row1.createCell(10).setCellValue(0);
            }
            if (accountService.isInteger(map.get("hd"))) {
                row1.createCell(11).setCellValue(Integer.valueOf(map.get("hd")));
            } else {
                row1.createCell(11).setCellValue(0);
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
