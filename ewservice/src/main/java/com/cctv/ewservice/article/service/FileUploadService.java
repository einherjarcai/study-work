package com.cctv.ewservice.article.service;

import com.cctv.ewservice.article.dao.BrandAccuSecDao;
import com.cctv.ewservice.article.dao.BrandSecDao;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangcai
 * @Date Created in 2018/11/7
 */
@Service
public class FileUploadService {
    @Autowired
    BrandService brandService;

    @Autowired
    BrandAccuSecDao brandAccuSecDao;

    @Autowired
    BrandSecDao brandSecDao;

    /**
     * 是否为整数
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
     * 返回上传的 Excel 的稿件名称列表
     * @param mfile
     * @return
     */
    public  List<String> batchImport(MultipartFile mfile) {
        InputStream is = null;
        String fileName = mfile.getOriginalFilename();
//        System.out.println(fileName);
        List<String> list = new ArrayList<String>();
        try {
            is = mfile.getInputStream();
            boolean isExcel2003 = true;
            if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                isExcel2003 = false;
            }
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);
            }
            if (!(wb == null)) {
                Sheet sheet = wb.getSheetAt(0);
                int lastRowNum = sheet.getLastRowNum();
                for (int j = 1; j <= lastRowNum; j++) {
                    Row row = sheet.getRow(j);
                    if (row.getCell(1) != null) {
                        String str = row.getCell(1).getStringCellValue();
//                        String pattern = "\\([^)]*\\)";
                        /*String pattern = "\\(.*?\\)|（.*?）";
                        str = str.replaceAll(pattern, " ");*/
                        String regEx = "[,，]";
                        Pattern p = Pattern.compile(regEx);
                        Matcher m = p.matcher(str);
                        str = m.replaceAll(" ").trim().replace(" ", " ").replace("\\", " ");
                        list.add(str);
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(is !=null)
            {
                try{
                    is.close();
                }catch(IOException e){
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 分账号查 分天分条数据
     * @param type
     * @param channel
     * @param startdate
     * @param enddate
     * @param keywordList
     * @return
     */
    /*public List<Map<String, String>> getFileUploadData(int type, int channel, String startdate, String enddate, List<String> keywordList) {
        List<Map<String, String>> wbidList = new ArrayList<Map<String, String>>();
        wbidList = brandService.getWeiBoIDList(type, channel);
        List<Map<String, String>> wxidList = new ArrayList<Map<String, String>>();
        wxidList = brandService.getWeiXinIDList(type, channel);
        List<String> dateList = new ArrayList<String>();
        dateList = brandService.getBetweenDate(startdate, enddate);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        for (int i = 0; i < keywordList.size(); ++i) {
            List<Map<String, String>> wx_result = new ArrayList<Map<String, String>>();
            List<Map<String, String>> wb_result = new ArrayList<Map<String, String>>();
            for (Map<String, String> map : wxidList) {
                List<Map<String, String>> wxid_result_list = new ArrayList<Map<String, String>>();
                String weixinid = map.get("id");
                wxid_result_list = brandDao.getWeiXinAllMsgidInfo(weixinid, startdate, enddate, keywordList.get(i), map);
                wx_result.addAll(wxid_result_list);
            }
            wx_result = brandService.DateSortList(wx_result, dateList);
            for (Map<String, String> map : wbidList) {
                List<Map<String, String>> wbid_result_list = new ArrayList<Map<String, String>>();
                String weiboId = map.get("id");
                if (brandDao.isIdExits(weiboId, startdate, enddate)) {
                    wbid_result_list = brandDao.getWeiBoArticleMid(weiboId, startdate, enddate, keywordList.get(i), map);
                } else {
                    wbid_result_list = brandDao.getQingBoWeiBoArticleInfo(weiboId, startdate, enddate, keywordList.get(i), map);
                }
                wb_result.addAll(wbid_result_list);
            }
            wb_result = brandService.TimeSortList(wb_result);
            list.addAll(wx_result);
            list.addAll(wb_result);
        }
        return list;
    }

    *//**
     * 分账号查 累计数据
     * @param type
     * @param channel
     * @param startdate
     * @param enddate
     * @param keywordList
     * @return
     *//*
    public List<Map<String, String>> getFileUploadAccuData(int type, int channel, String startdate, String enddate, List<String> keywordList) {
        List<Map<String, String>> wbidList = new ArrayList<Map<String, String>>();
        wbidList = brandService.getWeiBoIDList(type, channel);
        List<Map<String, String>> wxidList = new ArrayList<Map<String, String>>();
        wxidList = brandService.getWeiXinIDList(type, channel);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        for (int i = 0; i < keywordList.size(); ++i) {
            Map<String, String> result = new HashMap<String, String>();
            int weixinCount = 0;
            int weixinRead = 0;
            int weixinHd = 0;
            int weiboCount = 0;
            int weiboRead = 0;
            int weiboHd = 0;
            int weiboVideo = 0;

            String str = keywordList.get(i);
            String pattern = "\\(.*?\\)|（.*?）";
            str = str.replaceAll(pattern, " ");
            String regEx = "[`~☆★!@#$%^&*()+=|{}':;,\\[\\]》·.<>/?~！@#￥%……（）——+|{}【】‘；：”“’。，、？丨]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            str = m.replaceAll(" ").trim().replace(" ", " ").replace("\\", " ");

            for (Map<String, String> map : wxidList) {
                List<Integer> wx_data = new ArrayList<Integer>();
                String weixinid = map.get("id");
                wx_data = brandAccuDao.getWeiXinAccuAllMsgidInfo(weixinid, startdate, enddate, str);
                if (wx_data.size() == 3) {
                    weixinCount += wx_data.get(0);
                    weixinRead += wx_data.get(1);
                    weixinHd += wx_data.get(2);
                }
            }
            for (Map<String, String> map : wbidList) {
                String weiboId = map.get("id");
                List<Integer> wb_data = new ArrayList<Integer>();
                if (brandDao.isIdExits(weiboId, startdate, enddate)) {
                    wb_data = brandAccuDao.getWeiBoAccuAllMsgidInfo(weiboId, startdate, enddate, str);
                } else {
                    wb_data = brandAccuDao.getQingBoWeiBoArticleInfo(weiboId, startdate, enddate, str);
                }
                if (wb_data.size() == 4) {
                    weiboCount += wb_data.get(0);
                    weiboRead += wb_data.get(1);
                    weiboHd += wb_data.get(2);
                    weiboVideo += wb_data.get(3);
                }
            }
            result.put("date", startdate + "~" + enddate);
            result.put("brand", keywordList.get(i));
            result.put("wx_count", String.valueOf(weixinCount));
            result.put("wx_read", String.valueOf(weixinRead));
            result.put("wx_hd", String.valueOf(weixinHd));
            result.put("wb_count", String.valueOf(weiboCount));
            result.put("wb_read", String.valueOf(weiboRead));
            result.put("wb_hd", String.valueOf(weiboHd));
            result.put("wb_video", String.valueOf(weiboVideo));
            list.add(result);
        }
//        System.out.println(list.toString());
        return list;
    }*/


    /**
     * 一次性查所有账号， 返回分天分条数据
     * @param type
     * @param channel
     * @param startdate
     * @param enddate
     * @param keywordList
     * @return
     */
    public List<Map<String, String>> getFileUploadSecData(int type, int channel, String startdate, String enddate, List<String> keywordList) {
        List<Map<String, String>> wbidList = new ArrayList<Map<String, String>>();
        wbidList = brandService.getWeiBoIDList(type, channel);
        List<Map<String, String>> wxidList = new ArrayList<Map<String, String>>();
        wxidList = brandService.getWeiXinIDList(type, channel);

        List<String> dateList = new ArrayList<String>();
        dateList = brandService.getBetweenDate(startdate, enddate);

        List<String> wbList = new ArrayList<String>();
        wbList = getWeiBoIDList(type, channel);
        List<String> wxList = new ArrayList<String>();
        wxList = getWeiXinIDList(type, channel);

        /**
         * 获取清博账号
         */
        List<String> wbQingBoList = new ArrayList<String>();
        List<String> wxQingBoList = new ArrayList<String>();
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
//        System.out.println("上传后" + System.currentTimeMillis());
        // 最终结果
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        for (int i = 0; i < keywordList.size(); ++i) {
            String str = keywordList.get(i);
            String pattern = "\\[";
            str = str.replaceAll(pattern, "");
            String pattern1 = "\\]";
            str = str.replaceAll(pattern1, "");

            List<Map<String, String>> wx_result = new ArrayList<Map<String, String>>();
            List<Map<String, String>> wb_result = new ArrayList<Map<String, String>>();

            List<Map<String, String>> wxid_result_list = new ArrayList<Map<String, String>>();
            wxid_result_list = brandSecDao.getWeiXinAllMsgidInfo(wxList, startdate, enddate, str, wxidList);
            wx_result.addAll(wxid_result_list);
            if (wxQingBoList.size() > 0) {
                List<Map<String, String>> qbid_result_list = new ArrayList<Map<String, String>>();
                qbid_result_list = brandSecDao.getWeiXinQingBoArticleInfo(wxQingBoList, startdate, enddate, str, wxidList);
                wx_result.addAll(qbid_result_list);
            }
            wx_result = brandService.DateSortList(wx_result, dateList);

            List<Map<String, String>> wbid_result_list = new ArrayList<Map<String, String>>();
            wbid_result_list = brandSecDao.getWeiBoArticleMid(wbList, startdate, enddate, str, wbidList);
            wb_result.addAll(wbid_result_list);
            if (wbQingBoList.size() > 0) {
                List<Map<String, String>> qb_result_list = new ArrayList<Map<String, String>>();
                qb_result_list = brandSecDao.getQingBoWeiBoArticleInfo(wbQingBoList, startdate, enddate, str, wbidList);
                wb_result.addAll(qb_result_list);
            }
            wb_result = brandService.TimeSortList(wb_result);

            list.addAll(wx_result);
            list.addAll(wb_result);
        }
        return list;
    }


    /**
     * 一次性查所有账号，返回累计数据
     * @param type
     * @param channel
     * @param startdate
     * @param enddate
     * @param keywordList
     * @return
     */
    public List<Map<String, String>> getFileUploadAccuSecData(int type, int channel, String startdate, String enddate, List<String> keywordList) {
        List<String> wbidList = new ArrayList<String>();
        wbidList = getWeiBoIDList(type, channel);
        List<String> wxidList = new ArrayList<String>();
        wxidList = getWeiXinIDList(type, channel);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        List<String> wbQingBoList = new ArrayList<String>();
        List<String> wxQingBoList = new ArrayList<String>();
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
            Map<String, String> result = new HashMap<String, String>();
            int weixinCount = 0;
            int weixinRead = 0;
            int weixinHd = 0;
            int weiboCount = 0;
            int weiboRead = 0;
            int weiboHd = 0;
            int weiboVideo = 0;

            String str1 = keywordList.get(i);
            String pattern2 = "\\[";
            str1 = str1.replaceAll(pattern2, "");
            String pattern1 = "\\]";
            str1 = str1.replaceAll(pattern1, "");

            String str = keywordList.get(i);
            String pattern = "\\(.*?\\)|（.*?）";
            str = str.replaceAll(pattern, " ");
            String regEx = "[`~☆★!@#$%^&*()+=|{}':;,\\[\\]》·.<>/?~！@#￥%……（）——+|{}【】‘；：”“’。，、？丨]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            str = m.replaceAll(" ").trim().replace(" ", " ").replace("\\", " ");

            List<Integer> wx_data = new ArrayList<Integer>();
            wx_data = brandAccuSecDao.getWeiXinAccuAllMsgidInfo(wxidList, startdate, enddate, str);
            if (wx_data.size() == 3) {
                weixinCount += wx_data.get(0);
                weixinRead += wx_data.get(1);
                weixinHd += wx_data.get(2);
            }
            if (wxQingBoList.size() > 0) {
                List<Integer> qb_wx_data = new ArrayList<Integer>();
                qb_wx_data = brandAccuSecDao.getWeiXinQingBoArticleInfo(wxQingBoList, startdate, enddate, str);
                if (qb_wx_data.size() == 3) {
                    weixinCount += qb_wx_data.get(0);
                    weixinRead += qb_wx_data.get(1);
                    weixinHd += qb_wx_data.get(2);
                }
            }

            List<Integer> wb_data = new ArrayList<Integer>();
            wb_data = brandAccuSecDao.getWeiBoAccuAllMsgidInfo(wbidList, startdate, enddate, str);
            if (wb_data.size() == 4) {
                weiboCount += wb_data.get(0);
                weiboRead += wb_data.get(1);
                weiboHd += wb_data.get(2);
                weiboVideo += wb_data.get(3);
            }

            // 清博账号对应的指标
            if (wbQingBoList.size() > 0) {
                List<Integer> qb_wb_data = new ArrayList<Integer>();
                qb_wb_data = brandAccuSecDao.getQingBoWeiBoArticleInfo(wbQingBoList, startdate, enddate, str);
                if (qb_wb_data.size() == 4) {
                    weiboCount += qb_wb_data.get(0);
                    weiboRead += qb_wb_data.get(1);
                    weiboHd += qb_wb_data.get(2);
                    weiboVideo += qb_wb_data.get(3);
                }
            }

            result.put("date", startdate + "~" + enddate);
            result.put("brand", str1);
            result.put("wx_count", String.valueOf(weixinCount));
            result.put("wx_read", String.valueOf(weixinRead));
            result.put("wx_hd", String.valueOf(weixinHd));
            result.put("wb_count", String.valueOf(weiboCount));
            result.put("wb_read", String.valueOf(weiboRead));
            result.put("wb_hd", String.valueOf(weiboHd));
            result.put("wb_video", String.valueOf(weiboVideo));
            list.add(result);
        }
        return list;
    }

    /**
     * 只返回账号的 uid
     * @param type
     * @param channel
     * @return
     */
    public List<String> getWeiXinIDList(int type, int channel) {
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
        List<String> list = new ArrayList<String>();
        list = brandAccuSecDao.getWeiXinIdList(queryType, querychannel);
//        System.out.println(list);
        return list;
    }

    /**
     * 只返回账号的 uid
     * @param type
     * @param channel
     * @return
     */
    public List<String> getWeiBoIDList(int type, int channel) {
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
        List<String> list = new ArrayList<String>();
        list = brandAccuSecDao.getWeiBoIdList(queryType, querychannel);
        return list;
    }
}
