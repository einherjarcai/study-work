package com.cctv.ewservice.article.controller;

import com.cctv.ewservice.article.service.BrandService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BrandController {
    @Autowired
    BrandService brandService;

    @RequestMapping("/brand")
    public String getBrandData(@RequestParam(value = "type", required = true) Integer type,
                               @RequestParam(value = "channel", required = true) Integer channel,
                               @RequestParam(value = "startdate", required = true) String startdate,
                               @RequestParam(value = "enddate", required = true) String enddate,
                               @RequestParam(value = "page", required = true) Integer page,
                               @RequestParam(value = "size", required = true) Integer size,
                               @RequestParam(value = "key", required = true) Integer key,
                               HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return brandService.getBrandPageData(type, channel, key, startdate, enddate, page, size);
    }

    @RequestMapping("/brand/download")
    public void getBrandDownLoadData(@RequestParam(value = "type", required = true) Integer type,
                               @RequestParam(value = "channel", required = true) Integer channel,
                               @RequestParam(value = "startdate", required = true) String startdate,
                               @RequestParam(value = "enddate", required = true) String enddate,
                               @RequestParam(value = "key", required = true) Integer key,
                               HttpServletResponse response) throws IOException {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list = brandService.getAllBrandData(type, channel, key, startdate, enddate);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("新媒体品牌");
        int rowNum = 1;
        String[] headers = { "序号", "账号名", "账号归属", "所属频道", "发稿日期", "发稿时间", "品牌", "来源", "URL", "标题", "阅读量", "互动量", "微博视频播放量"};
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
            row1.createCell(3).setCellValue(map.get("channel"));
            row1.createCell(4).setCellValue(map.get("pub_date"));
            row1.createCell(5).setCellValue(map.get("time"));
            row1.createCell(6).setCellValue(map.get("brand"));
            row1.createCell(7).setCellValue(map.get("source"));
            row1.createCell(8).setCellValue(map.get("url"));
            row1.createCell(9).setCellValue(map.get("title"));
            if (brandService.isInteger(map.get("read"))) {
                row1.createCell(10).setCellValue(Integer.valueOf(map.get("read")));
            } else {
                row1.createCell(10).setCellValue(0);
            }
            if (brandService.isInteger(map.get("hd"))) {
                row1.createCell(11).setCellValue(Integer.valueOf(map.get("hd")));
            } else {
                row1.createCell(11).setCellValue(0);
            }
            if (brandService.isInteger(map.get("videoNum"))) {
                row1.createCell(12).setCellValue(Integer.valueOf(map.get("videoNum")));
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
