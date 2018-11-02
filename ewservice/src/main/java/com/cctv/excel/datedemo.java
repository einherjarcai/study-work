package com.cctv.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class datedemo {
        /**
         * 获取两个日期字符串之间的日期集合
         * @param startTime:String
         * @param endTime:String
         * @return list:yyyy-MM-dd
         */
        public static List<String> getBetweenDate(String startTime, String endTime){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 声明保存日期集合
            List<String> list = new ArrayList<String>();
            try {
                // 转化成日期类型
                Date startDate = sdf.parse(startTime);
                Date endDate = sdf.parse(endTime);

                //用Calendar 进行日期比较判断
                Calendar calendar = Calendar.getInstance();
                while (startDate.getTime()<=endDate.getTime()){
                    // 把日期添加到集合
                    list.add(sdf.format(startDate));
                    // 设置日期
                    calendar.setTime(startDate);
                    //把日期增加一天
                    calendar.add(Calendar.DATE, 1);
                    // 获取增加后的日期
                    startDate=calendar.getTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(list);
            return list;
        }



    public static void main(String[] args) throws ParseException {
        getBetweenDate("2018-09-18", "2018-09-20");
    }
}
