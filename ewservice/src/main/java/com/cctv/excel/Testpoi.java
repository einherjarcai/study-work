package com.cctv.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentType;

public class Testpoi {

    public static void main(String[] args) {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        String filePath = "C:\\Users\\neusoft\\Desktop\\account\\weibo.xlsx";
//        String filePath = "C:\\Users\\neusoft\\Desktop\\account\\weixin.xlsx";
        String columns[] = {"UID", "昵称", "所属频道/中心", "归属", "级别"};
//        String weixin_columns[] = {"微信号", "名称", "所属频道/中心", "归属", "级别", "biz"};
        wb = readExcel(filePath);
        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<Map<String, String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i < rownum; i++) {
                Map<String, String> map = new LinkedHashMap<String, String>();
                row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
//                        map.put(weixin_columns[j], cellData);
                    }
                } else {
                    break;
                }
                list.add(map);
            }
        }
        InsertWeiBoDataToEs(list);
//        InsertWeiXinDataToEs(list);


        //遍历解析出来的list
        /*for (Map<String, String> map : list) {
            for (Entry<String, String> entry : map.entrySet()) {
                System.out.print(entry.getKey() + ":" + entry.getValue() + ",");
            }
            System.out.println();
        }*/

    }

    public static void InsertWeiBoDataToEs(List<Map<String, String>> list ) {
        for (Map<String, String> map : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UID", map.get("UID"));
            jsonObject.put("nickname", map.get("昵称"));
            jsonObject.put("channel", map.get("所属频道/中心"));
            jsonObject.put("type", map.get("归属"));
            jsonObject.put("level", map.get("级别"));
            IndexResponse response = EsClientTools.getEsClient().prepareIndex("weibo_account_list", "type")
                    .setSource(jsonObject, XContentType.JSON)
                    .get();
        }
    }

    public static void InsertWeiXinDataToEs(List<Map<String, String>> list ) {
        for (Map<String, String> map : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("wx_id", map.get("微信号"));
            jsonObject.put("nickname", map.get("名称"));
            jsonObject.put("channel", map.get("所属频道/中心"));
            jsonObject.put("type", map.get("归属"));
            jsonObject.put("level", map.get("级别"));
            jsonObject.put("biz", map.get("biz"));
            IndexResponse response = EsClientTools.getEsClient().prepareIndex("weixin_account_list", "type")
                    .setSource(jsonObject, XContentType.JSON)
                    .get();
        }
    }

    //读取excel
    public static Workbook readExcel(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    DecimalFormat df = new DecimalFormat("0");
                    cellValue = df.format(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

}
