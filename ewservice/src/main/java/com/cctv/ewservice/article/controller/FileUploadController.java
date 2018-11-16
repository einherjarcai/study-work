package com.cctv.ewservice.article.controller;

import com.cctv.ewservice.article.service.FileUploadService;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author wangcai
 * @Date Created in 2018/11/7
 */
@RestController
public class FileUploadController {
    @Autowired
    FileUploadService fileUploadService;

//    private List<String> keyword = new ArrayList<String>();

    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    public String importResult(@RequestParam("file") MultipartFile file,
                                HttpServletResponse response) throws Exception {
        List<String> list = fileUploadService.batchImport(file);
        /*try {
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(file.getOriginalFilename())));
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//        keyword = list;
        return list.toString();
    }

    @RequestMapping(value = "/filedata", method = RequestMethod.POST)
    public String getFileUploadData(@RequestParam(value = "type", required = true) Integer type,
                                    @RequestParam(value = "channel", required = true) Integer channel,
                                    @RequestParam(value = "startdate", required = true) String startdate,
                                    @RequestParam(value = "enddate", required = true) String enddate,
                                    @RequestParam(value = "accu", required = true) Integer accu,
                                    @RequestParam(value = "key", required = true) List<String> key,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "size", required = false) Integer size,
                                    HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (accu == 1) {
            list = fileUploadService.getFileUploadSecData(type, channel, startdate, enddate, key);
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
            jsonObject.put("tableData", result);
            return jsonObject.toString();
        } else {
            list = fileUploadService.getFileUploadAccuSecData(type, channel, startdate, enddate, key);
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
            jsonObject.put("tableData", result);
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "/file/download", method = RequestMethod.POST)
    public void getFileUploadDataDownload(@RequestParam(value = "type", required = true) Integer type,
                                    @RequestParam(value = "channel", required = true) Integer channel,
                                    @RequestParam(value = "startdate", required = true) String startdate,
                                    @RequestParam(value = "enddate", required = true) String enddate,
                                    @RequestParam(value = "key", required = true) List<String> key,
                                    @RequestParam(value = "accu", required = true) Integer accu,
                                    HttpServletResponse response) throws IOException {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (accu == 1) {
            list = fileUploadService.getFileUploadSecData(type, channel, startdate, enddate, key);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("稿件");
            int rowNum = 1;
            String[] headers = { "序号", "账号名", "账号归属", "所属频道", "发稿日期", "发稿时间", "稿件名称", "来源", "URL", "标题", "阅读量", "互动量", "微博视频播放量"};
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
                if (fileUploadService.isInteger(map.get("read"))) {
                    row1.createCell(10).setCellValue(Integer.valueOf(map.get("read")));
                } else {
                    row1.createCell(10).setCellValue(0);
                }
                if (fileUploadService.isInteger(map.get("hd"))) {
                    row1.createCell(11).setCellValue(Integer.valueOf(map.get("hd")));
                } else {
                    row1.createCell(11).setCellValue(0);
                }
                if (fileUploadService.isInteger(map.get("videoNum"))) {
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
        } else {
            list = fileUploadService.getFileUploadAccuSecData(type, channel, startdate, enddate, key);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("稿件");
            int rowNum = 1;
            String[] headers = { "序号", "稿件名称", "发稿日期", "微信发稿量", "微信阅读量", "微信互动量","微博发稿量", "微博阅读量", "微博互动量", "微博视频播放量"};
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
                row1.createCell(1).setCellValue(map.get("brand"));
                row1.createCell(2).setCellValue(map.get("date"));
                if (fileUploadService.isInteger(map.get("wx_count"))) {
                    row1.createCell(3).setCellValue(Integer.valueOf(map.get("wx_count")));
                } else {
                    row1.createCell(3).setCellValue(0);
                }
                if (fileUploadService.isInteger(map.get("wx_read"))) {
                    row1.createCell(4).setCellValue(Integer.valueOf(map.get("wx_read")));
                } else {
                    row1.createCell(4).setCellValue(0);
                }
                if (fileUploadService.isInteger(map.get("wx_hd"))) {
                    row1.createCell(5).setCellValue(Integer.valueOf(map.get("wx_hd")));
                } else {
                    row1.createCell(5).setCellValue(0);
                }
                if (fileUploadService.isInteger(map.get("wb_count"))) {
                    row1.createCell(6).setCellValue(Integer.valueOf(map.get("wb_count")));
                } else {
                    row1.createCell(6).setCellValue(0);
                }
                if (fileUploadService.isInteger(map.get("wb_read"))) {
                    row1.createCell(7).setCellValue(Integer.valueOf(map.get("wb_read")));
                } else {
                    row1.createCell(7).setCellValue(0);
                }
                if (fileUploadService.isInteger(map.get("wb_hd"))) {
                    row1.createCell(8).setCellValue(Integer.valueOf(map.get("wb_hd")));
                } else {
                    row1.createCell(8).setCellValue(0);
                }
                if (fileUploadService.isInteger(map.get("wb_video"))) {
                    row1.createCell(9).setCellValue(Integer.valueOf(map.get("wb_video")));
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



    }
}
