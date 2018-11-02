package com.cctv.ewservice.common;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonDateFunction {
    /**
     * 根据日期和格式解析时间字符串
     *
     * @param date   日期字符串，2015-08-11
     * @param format 日期格式，yyyy-MM-dd
     * @return Date对象
     * @throws ParseException
     */
    public static Date parseDate(String date, String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        return df.parse(date);
    }

    /**
     * 获取当前周的第一天
     *
     * @return
     */
    public static String getFirstDayOfWeek() {
        Calendar cd = Calendar.getInstance();
        cd.setFirstDayOfWeek(Calendar.SUNDAY);
        cd.set(Calendar.DAY_OF_WEEK, cd.getFirstDayOfWeek());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getFirstDayOfWeek(String date) {
        if (date == null || date.isEmpty()) {
            return "";
        }

        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));

        cd.setFirstDayOfWeek(Calendar.SUNDAY);

        if (cd.get(Calendar.MONTH) == 0 && cd.get(Calendar.WEEK_OF_YEAR) == 1) {
            cd.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            cd.set(Calendar.DAY_OF_WEEK, cd.getFirstDayOfWeek());
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getFirstDayOfLastWeek(String date) {
        if (date == null || date.isEmpty()) {
            return "";
        }

        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));

        cd.setFirstDayOfWeek(Calendar.SUNDAY);
        if ((1 == Integer.parseInt(dateSplit1[1])) && (1 == cd.get(Calendar.WEEK_OF_YEAR) || 2 == cd.get(Calendar.WEEK_OF_YEAR))) {
            return String.valueOf(cd.get(Calendar.YEAR)) + "-01-01";
        } else {
            cd.set(Calendar.WEEK_OF_YEAR, cd.get(Calendar.WEEK_OF_YEAR) - 1);
            cd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(cd.getTime());
        }
    }

    public static String getLastDayOfLastWeek(String date) {
        if (date == null || date.isEmpty()) {
            return "";
        }

        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));

        cd.setFirstDayOfWeek(Calendar.SUNDAY);
        if (1 == cd.get(Calendar.WEEK_OF_YEAR)) {
            cd.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        } else {
            cd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            int day = cd.get(Calendar.DAY_OF_YEAR);
            cd.set(Calendar.DAY_OF_YEAR, day - 1);
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取当前周的最后一天
     *
     * @return
     */
    public static String getLastDayOfWeek(String date) {
        if (date == null || date.isEmpty()) {
            return "";
        }

        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));

        cd.setFirstDayOfWeek(Calendar.SUNDAY);

        String firstDay = CommonDateFunction.getFirstDayOfWeek(date);

        String[] dateSplit2 = firstDay.split("-");
        cd.set(Integer.parseInt(dateSplit2[0]), Integer.parseInt(dateSplit2[1]) - 1, Integer.parseInt(dateSplit2[2]));


        if (cd.get(Calendar.MONTH) == 11 &&
                cd.get(Calendar.DAY_OF_MONTH) + 6 > 31) {
            cd.set(Calendar.DAY_OF_MONTH, 31);
        } else {
            cd.setFirstDayOfWeek(Calendar.SUNDAY);
            cd.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取当前月的第一天
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.MONTH, 0);
        cd.set(Calendar.DAY_OF_MONTH, 1);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getFirstDayOfMonth(String date) {
        return date.split("-")[0] + "-" + date.split("-")[1] + "-01";
    }

    public static String getFirstDayOfLastMonth(String date) {
        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));

        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMinimum(Calendar.DAY_OF_MONTH));
        cd.set(Calendar.MONTH, cd.get(Calendar.MONTH) - 1);


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取当前月的最后一天
     *
     * @return
     */
    public static String getLastDayOfMonth() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMaximum(Calendar.DAY_OF_MONTH));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getLastDayOfMonth(String date) {

        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));

        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMaximum(Calendar.DAY_OF_MONTH));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getLastDayOfLastMonth(String date) {
        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));

        cd.set(Calendar.DAY_OF_MONTH, 1);
        cd.set(Calendar.MONTH, cd.get(Calendar.MONTH) - 1);
        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMaximum(Calendar.DAY_OF_MONTH));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取当前季度的第一天
     *
     * @return
     */
    public static String getFirstDayOfQuarter() {
        Calendar cd = Calendar.getInstance();
        int currentMonth = cd.get(Calendar.MONTH) + 1;

        if (currentMonth >= 1 && currentMonth <= 3) {
            cd.set(Calendar.MONTH, 0);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            cd.set(Calendar.MONTH, 3);

        } else if (currentMonth >= 7 && currentMonth <= 9) {
            cd.set(Calendar.MONTH, 4);

        } else if (currentMonth >= 10 && currentMonth <= 12) {
            cd.set(Calendar.MONTH, 9);
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getFirstDayOfQuarter(String date) {
        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));


        int currentMonth = cd.get(Calendar.MONTH) + 1;

        if (currentMonth >= 1 && currentMonth <= 3) {
            cd.set(Calendar.MONTH, 0);
            cd.set(Calendar.DAY_OF_MONTH, 1);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            cd.set(Calendar.MONTH, 3);
            cd.set(Calendar.DAY_OF_MONTH, 1);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            cd.set(Calendar.MONTH, 6);
            cd.set(Calendar.DAY_OF_MONTH, 1);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            cd.set(Calendar.MONTH, 9);
            cd.set(Calendar.DAY_OF_MONTH, 1);
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getFirstDayOfLastQuarter(String date) {
        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));

        int currentMonth = cd.get(Calendar.MONTH) + 1;

        if (currentMonth >= 1 && currentMonth <= 3) {
            cd.set(Calendar.YEAR, cd.get(Calendar.YEAR) - 1);
            cd.set(Calendar.MONTH, 9);
            cd.set(Calendar.DAY_OF_MONTH, 1);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            cd.set(Calendar.MONTH, 0);
            cd.set(Calendar.DAY_OF_MONTH, 1);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            cd.set(Calendar.MONTH, 3);
            cd.set(Calendar.DAY_OF_MONTH, 1);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            cd.set(Calendar.MONTH, 6);
            cd.set(Calendar.DAY_OF_MONTH, 1);
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取当前季度的最后一天
     *
     * @return
     */
    public static String getLastDayOfQuarter() {
        Calendar cd = Calendar.getInstance();
        int currentMonth = cd.get(Calendar.MONTH) + 1;

        if (currentMonth >= 1 && currentMonth <= 3) {
            cd.set(Calendar.MONTH, 2);
            cd.set(Calendar.DATE, 31);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            cd.set(Calendar.MONTH, 5);
            cd.set(Calendar.DATE, 30);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            cd.set(Calendar.MONTH, 8);
            cd.set(Calendar.DATE, 30);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            cd.set(Calendar.MONTH, 11);
            cd.set(Calendar.DATE, 31);
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getLastDayOfQuarter(String date) {
        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));

        int currentMonth = cd.get(Calendar.MONTH) + 1;

        if (currentMonth >= 1 && currentMonth <= 3) {
            cd.set(Calendar.MONTH, 2);
            cd.set(Calendar.DATE, 31);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            cd.set(Calendar.MONTH, 5);
            cd.set(Calendar.DATE, 30);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            cd.set(Calendar.MONTH, 8);
            cd.set(Calendar.DATE, 30);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            cd.set(Calendar.MONTH, 11);
            cd.set(Calendar.DATE, 31);
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getLastDayOfLastQuarter(String date) {
        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));

        int currentMonth = cd.get(Calendar.MONTH) + 1;

        if (currentMonth >= 1 && currentMonth <= 3) {
            cd.set(Calendar.YEAR, cd.get(Calendar.YEAR) - 1);
            cd.set(Calendar.MONTH, 11);
            cd.set(Calendar.DATE, 31);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            cd.set(Calendar.MONTH, 2);
            cd.set(Calendar.DATE, 31);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            cd.set(Calendar.MONTH, 5);
            cd.set(Calendar.DATE, 30);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            cd.set(Calendar.MONTH, 8);
            cd.set(Calendar.DATE, 30);
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取当前年的都一天
     *
     * @return
     */
    public static String getFirstDayOfYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.MONTH, 0);
        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMinimum(Calendar.DAY_OF_MONTH));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getFirstDayOfYear(String date) {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
        cd.set(Calendar.MONTH, 0);
        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMinimum(Calendar.DAY_OF_MONTH));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getFirstDayOfLastYear(String date) {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]) - 1);
        cd.set(Calendar.MONTH, 0);
        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMinimum(Calendar.DAY_OF_MONTH));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取当前年的最后一天
     *
     * @return
     */
    public static String getLastDayOfYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.MONTH, 11);
        cd.set(Calendar.DAY_OF_MONTH, cd.getMaximum(Calendar.DAY_OF_MONTH));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getLastDayOfYear(String date) {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
        cd.set(Calendar.MONTH, 11);
        cd.set(Calendar.DAY_OF_MONTH, cd.getMaximum(Calendar.DAY_OF_MONTH));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }


    public static String getLastDayOfLastYear(String date) {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]) - 1);
        cd.set(Calendar.MONTH, 11);
        cd.set(Calendar.DAY_OF_MONTH, cd.getMaximum(Calendar.DAY_OF_MONTH));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取两个日期的总天数
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @return 天数
     * @throws ParseException
     */
    public static long getTotalDay(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);

        Calendar cd = Calendar.getInstance();
        cd.setTime(start);
        long time1 = cd.getTimeInMillis();
        cd.setTime(end);
        long time2 = cd.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

        //计算总天数包括开始和结束的一天
        return betweenDays + 1;
    }

    /**
     * 转换时间，开始和结束时间同时加上一个增量
     *
     * @param startTime
     * @param endTime
     * @param offset
     * @return
     */
    public static List<String> convertTimeInterval(String startTime, String endTime, int offset) {
        int hour1 = Integer.parseInt(startTime.split(":")[0]);
        int hour2 = Integer.parseInt(endTime.split(":")[0]);

        int newHour1 = hour1 + offset;
        int newHour2 = hour2 + offset;

        String newStartTime = String.format("%02d", newHour1) + ":" + startTime.substring(3);
        String newEndTime = String.format("%02d", newHour2) + ":" + endTime.substring(3);

        List<String> ret = new ArrayList<String>();
        ret.add(newStartTime);
        ret.add(newEndTime);
        return ret;
    }


    /**
     * 获取两个时间相隔的分钟数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 分钟数
     * @throws ParseException
     */
    public static long getTotalMinute(String startTime, String endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date start = sdf.parse(startTime);
        Date end = sdf.parse(endTime);

        Calendar cd = Calendar.getInstance();

        cd.setTime(start);
        long time1 = cd.getTimeInMillis();
        cd.setTime(end);
        long time2 = cd.getTimeInMillis();

        long interval = (time2 - time1) / (1000 * 60);
        return interval;

    }

    public static String formatSqlDate(java.sql.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    public static String formatSqlTime(Time time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        return sdf.format(time);
    }

	/*public static String getFirstDayOfPreviousWeek(){

		Calendar cd = new GregorianCalendar();
		cd.add(Calendar.WEEK_OF_MONTH, -1);
		cd.set(Calendar.DAY_OF_WEEK, 2);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(cd.getTime());
	}

	public static String getLastDayOfPreviousWeek(){

		Calendar cd = new GregorianCalendar();
		cd.setFirstDayOfWeek(Calendar.MONDAY);
		cd.set(Calendar.DAY_OF_WEEK, cd.getFirstDayOfWeek());
		cd.add(Calendar.DATE, -1);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(cd.getTime());
	}*/

    /**
     * @param interval 时间范围，用逗号分隔，如02:00:00,22:00:00
     * @return 开始时间和结束时间，list[0]是开始时间，list[1]是结束时间，格式都是HH:mm:ss
     */
    public static List<String> parseInteval(String interval) {
        List<String> startEndTime = new ArrayList<String>(2);
        String[] intervals = interval.split(",");
        if (intervals.length > 1) {
            if (intervals[0].contains(":")) {
                startEndTime.add(String.format("%02d:%02d:%02d", Integer.parseInt(intervals[0].split(":")[0]), Integer.parseInt(intervals[0].split(":")[1]), Integer.parseInt(intervals[0].split(":")[2])));
                startEndTime.add(String.format("%02d:%02d:%02d", Integer.parseInt(intervals[1].split(":")[0]), Integer.parseInt(intervals[1].split(":")[1]), Integer.parseInt(intervals[1].split(":")[2])));
            } else {
                startEndTime.add(String.format("%02d", Integer.parseInt(intervals[0])) + ":00:00");
                startEndTime.add(String.format("%02d", Integer.parseInt(intervals[1])) + ":00:00");
            }
        } else {
            switch (Integer.parseInt(interval)) {
                case 1:
                    startEndTime.add("02:00:00");
                    startEndTime.add("06:00:00");
                    break;
                case 2:
                    startEndTime.add("06:00:00");
                    startEndTime.add("09:00:00");
                    break;
                case 3:
                    startEndTime.add("09:00:00");
                    startEndTime.add("12:00:00");
                    break;
                case 4:
                    startEndTime.add("12:00:00");
                    startEndTime.add("14:00:00");
                    break;
                case 5:
                    startEndTime.add("14:00:00");
                    startEndTime.add("17:00:00");
                    break;
                case 6:
                    startEndTime.add("17:00:00");
                    startEndTime.add("19:00:00");
                    break;
                case 7:
                    startEndTime.add("19:00:00");
                    startEndTime.add("22:00:00");
                    break;
                case 8:
                    startEndTime.add("22:00:00");
                    startEndTime.add("24:00:00");
                    break;
                case 9:
                    startEndTime.add("24:00:00");
                    startEndTime.add("26:00:00");
                    break;
                default:
                    startEndTime.add("02:00:00");
                    startEndTime.add("26:00:00");
            }

        }

        return startEndTime;
    }

    public static String addTime(String time, String duration) {
        int hour1 = Integer.parseInt(time.split(":")[0]);
        int hour2 = Integer.parseInt(duration.split(":")[0]);

        int minute1 = Integer.parseInt(time.split(":")[1]);
        int minute2 = Integer.parseInt(duration.split(":")[1]);

        int second1 = Integer.parseInt(time.split(":")[2]);
        int second2 = Integer.parseInt(duration.split(":")[2]);

        int newSecond = (second1 + second2) % 60;
        int minuteP = 0;
        if (second1 + second2 >= 60) {
            minuteP = 1;
        }
        int newMinute = (minuteP + minute1 + minute2) % 60;
        int hourP = 0;
        if (minuteP + minute1 + minute2 >= 60) {
            hourP = 1;
        }

        int newHour = hour1 + hour2 + hourP;

        String newTime = String.format("%02d", newHour) + ":" + String.format("%02d", newMinute) + ":" + String.format("%02d", newSecond);

        return newTime;
    }

    //判断两个时间段是否重合
    public static boolean isTimeIntervalCoincide(String timeInterval1, String timeInterval2) {
        List<String> startEndTime1 = CommonDateFunction.parseInteval(timeInterval1);
        List<String> startEndTime2 = CommonDateFunction.parseInteval(timeInterval2);

        if (startEndTime1.get(0).compareTo(startEndTime2.get(0)) <= 0 &&
                startEndTime1.get(1).compareTo(startEndTime2.get(0)) > 0) {
            return true;
        } else if (startEndTime1.get(0).compareTo(startEndTime2.get(0)) >= 0 &&
                startEndTime1.get(0).compareTo(startEndTime2.get(1)) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断两个日期是否是一整周
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static boolean isWeek(String startDate, String endDate) throws ParseException {
        Calendar cl = Calendar.getInstance();
        cl.setTime(CommonDateFunction.parseDate(startDate, "yyyy-MM-dd"));
        int startDayOfWeek = cl.get(Calendar.DAY_OF_WEEK);
        int startDay = cl.get(Calendar.DATE);
        int startMonth = cl.get(Calendar.MONTH);

        cl.setTime(CommonDateFunction.parseDate(endDate, "yyyy-MM-dd"));
        int endDay = cl.get(Calendar.DATE);
        int endMonth = cl.get(Calendar.MONTH);
        int endDayOfWeek = cl.get(Calendar.DAY_OF_WEEK);

        int days = (int) CommonDateFunction.getTotalDay(startDate, endDate);

        if (days == 7 && startDayOfWeek == 2 && endDayOfWeek == 1 ||
                //每年1月1号开始算第一周
                days < 7 && startMonth == 0 && startDay == 1 && endDayOfWeek == 1 ||
                //每年最后一周可能不满7天
                days < 7 && startMonth == 11 && endDay == 31 && startDayOfWeek == 2
                ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得环比的待比较时间范围，逗号分隔，返回待环比日期
     *
     * @param startDate 如："2014-01-01"，默认是一个月的第一天
     * @param endDate   如："2014-06-30"，默认与startDate在同一年
     * @return
     * @throws ParseException
     * @author Euray
     */
    public static List<String> getHuanBiDate(String startDate, String endDate) throws ParseException {
        String[] starts = startDate.split("-");
        String[] ends = endDate.split("-");
        int startYear = Integer.parseInt(starts[0]);
        int startMonth = Integer.parseInt(starts[1]);
        int startDay = Integer.parseInt(starts[2]);
        int endMonth = Integer.parseInt(ends[1]);
        int endDay = Integer.parseInt(ends[2]);


        String returnStartDate = "";
        String returnEndDate = "";
        List<String> startEndDate = new ArrayList<String>();

        if (startDate.equals(endDate)) {
            //如果是同一天，选择上一天
            Calendar cl = Calendar.getInstance();
            cl.set(startYear, startMonth - 1, startDay - 1);
            returnStartDate = CommonDateFunction.formatDate(cl.getTime());
            returnEndDate = returnStartDate;
        } else if (CommonDateFunction.isWeek(startDate, endDate)) {
            if (startMonth == 1 && startDay == 1) {
                Calendar cl = Calendar.getInstance();
                cl.set(startYear, startMonth - 1, endDay - 6);
                returnStartDate = CommonDateFunction.formatDate(cl.getTime());
                cl.set(startYear, startMonth - 1, startDay - 1);
                returnEndDate = CommonDateFunction.formatDate(cl.getTime());
            } else {
                Calendar cl = Calendar.getInstance();
                cl.set(startYear, startMonth - 1, startDay - 7);
                returnStartDate = CommonDateFunction.formatDate(cl.getTime());
                cl.set(startYear, startMonth - 1, startDay - 1);
                returnEndDate = CommonDateFunction.formatDate(cl.getTime());
            }
        } else {
            Calendar end = Calendar.getInstance();
            end.set(startYear, endMonth - 1, 1);
            int days = end.getActualMaximum(Calendar.DAY_OF_MONTH);

            int subNum = endMonth - startMonth + 1;//当前选择了几个月
            if (startMonth > subNum) { //待环比日期在同一年，还是不同一年
                int startMonthH = startMonth - subNum;
                int endMonthH = startMonth - 1;
                Calendar endH = Calendar.getInstance();
                endH.set(startYear, endMonthH - 1, 1);
                int daysH = endH.getActualMaximum(Calendar.DAY_OF_MONTH);
                returnStartDate = startYear + (startMonthH < 10 ? "-0" : "-") + startMonthH + "-01";
                returnEndDate = startYear + (endMonthH < 10 ? "-0" : "-") + endMonthH + "-" + (endDay < days ? ends[2] : daysH);
            } else {
                int startMonthH = 12 + startMonth - subNum;
                if (startMonth > 1) {
                    int endMonthH = startMonth - 1;
                    Calendar endH = Calendar.getInstance();
                    endH.set(startYear, endMonthH - 1, 1);
                    int daysH = endH.getActualMaximum(Calendar.DAY_OF_MONTH);
                    returnStartDate = (startYear - 1) + (startMonthH < 10 ? "-0" : "-") + startMonthH + "-01";
                    returnEndDate = startYear + (endMonthH < 10 ? "-0" : "-") + endMonthH + "-" + (endDay < days ? ends[2] : daysH);
                } else {
                    returnStartDate = (startYear - 1) + (startMonthH < 10 ? "-0" : "-") + startMonthH + "-01";
                    returnEndDate = (startYear - 1) + "-12-" + (endDay < 31 ? ends[2] : 31);
                }
            }
        }
        startEndDate.add(returnStartDate);
        startEndDate.add(returnEndDate);
        return startEndDate;
    }

    /**
     * 获取所在周的第一天
     *
     * @return
     */
    public static String getFirstDayOfWhereWeek(Date date) {

        Calendar cd = new GregorianCalendar();
        cd.setTime(date);
        cd.setFirstDayOfWeek(Calendar.MONDAY);
        cd.set(Calendar.DAY_OF_WEEK, cd.getFirstDayOfWeek());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取所在周的最后一天
     *
     * @return
     */
    public static String getLastDayOfWhereWeek(Date date) {

        Calendar cd = new GregorianCalendar();
        cd.setTime(date);
        cd.setFirstDayOfWeek(Calendar.MONDAY);
        cd.set(Calendar.DAY_OF_WEEK, cd.getFirstDayOfWeek() + 6);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取某天所在月的第一天
     *
     * @return
     */
    public static String getFirstDayOfWhereMonth(Date date) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.MONTH, 0);
        cd.set(Calendar.DAY_OF_MONTH, 1);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取所在月的最后一天
     *
     * @return
     */
    public static String getLastDayOfWhereMonth(Date date) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMaximum(Calendar.DAY_OF_MONTH));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取所在月的前一个月的第一天
     *
     * @return
     */
    public static String getFirstDayOfPreviousMonth(Date date) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.MONTH, -1);
        cd.set(Calendar.DAY_OF_MONTH, 1);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    /**
     * 获取所在月的前一个月的最后一天
     *
     * @return
     */
    public static String getLastDayOfPreviousMonth(Date date) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.set(Calendar.DAY_OF_MONTH, 1);
        cd.add(Calendar.DATE, -1);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getYesterday() {
        // new Date().getTime()
        Date yesterday = new Date(System.currentTimeMillis() - 3600 * 24 * 1000);
        return CommonDateFunction.formatDate(yesterday);
    }

    public static String getBeforeDate(String date, String format) throws ParseException {
        Date before = new Date(CommonDateFunction.parseDate(date, format).getTime() - 3600L * 24 * 1000);
        return CommonDateFunction.formatDate(before);
    }

    public static String getTenBeforeDate(String date, String format) throws ParseException {
        Date before = new Date(CommonDateFunction.parseDate(date, format).getTime() - 3600L * 24 * 1000 * 10);
        return CommonDateFunction.formatDate(before);
    }

    //转换日期字符串，可能出现的情况
    //2016；
    //2015，2016；
    //2016-01；
    //2016-01，2016-03；
    //2016-01-01；
    //2016-01-01~2016-02-03；
    //2016-01-01，2016-02-11，2016-03-15；
    //返回值：如果list.length==2,返回的是开始和结束日期，到天
    //如果list.length ==1,返回的是某一天
    //如果list.length>2,返回的是日期列表，可能是年，年-月或者年-月-日，需要再处理
    public static List<String> convertVariousDate(String dateString) {
        List<String> startEndDate = new ArrayList<String>();
        if (dateString.contains("~")) {
            startEndDate.add(dateString.split("~")[0]);
            startEndDate.add(dateString.split("~")[1]);
            return startEndDate;
        }

        if (dateString.contains(",")) {
            for (String s : dateString.split(",")) {
                startEndDate.add(s);
            }
            return startEndDate;
        }

        String[] dates = dateString.split("-");
        if (dates.length == 1) {
            startEndDate.add(dates[0] + "-01-01");
            startEndDate.add(dates[0] + "-12-31");
        } else if (dates.length == 2) {
            startEndDate.add(dateString + "-01");
            String lastDate = CommonDateFunction.getLastDayOfMonth(dateString + "-01");
            startEndDate.add(lastDate);
        } else {
            startEndDate.add(dateString);
        }


        return startEndDate;
    }

    //转换日期字符串，可能出现的情况
    //2016；
    //2015，2016；
    //2016-01；
    //2016-01，2016-03；
    //2016-01-01；
    //2016-01-01~2016-02-03；
    //2016-01-01，2016-02-11，2016-03-15；
    //返回值：枚举每一天
    public static List<String> convertVariousDateToSpread(String dateString) {
        List<String> dates = new ArrayList<String>();
        if (dateString.contains("~")) {
            try {
                Date startDate = CommonDateFunction.parseDate(dateString.split("~")[0], "yyyy-MM-dd");
                Date endDate = CommonDateFunction.parseDate(dateString.split("~")[1], "yyyy-MM-dd");

                while (!startDate.equals(endDate)) {
                    dates.add(CommonDateFunction.formatDate(startDate));
                    startDate = new Date(startDate.getTime() + 24 * 3600 * 1000L);
                }
                dates.add(CommonDateFunction.formatDate(startDate));

            } catch (ParseException e) {
            }

            return dates;
        }

        if (dateString.contains(",")) {
            for (String s : dateString.split(",")) {
                dates.addAll(convertVariousDateToSpread(s));
            }
            return dates;
        }

        String[] ds = dateString.split("-");
        if (ds.length == 1) {
            return convertVariousDateToSpread(ds[0] + "-01-01" + "~" + ds[0] + "-12-31");
        } else if (ds.length == 2) {
            String lastDate = CommonDateFunction.getLastDayOfMonth(dateString + "-01");
            return convertVariousDateToSpread(dateString + "-01" + "~" + lastDate);
        } else {
            dates.add(dateString);
        }


        return dates;
    }

    public static List<String> convertVariousDateToSpreadWeek(String dateString) {
        List<String> dates = new ArrayList<String>();
        if (dateString.contains("~")) {
            try {
                Date startDate = CommonDateFunction.parseDate(dateString.split("~")[0], "yyyy-MM-dd");
                Date endDate = CommonDateFunction.parseDate(dateString.split("~")[1], "yyyy-MM-dd");
                Calendar cdStart = Calendar.getInstance();
                while (!startDate.equals(endDate)) {
                    int week = CommonDateFunction.getWeekNumber(startDate);

                    cdStart.setTime(startDate);
                    String weekStr = cdStart.get(Calendar.YEAR) + "-" + ((week > 9) ? week + "" : "0" + week);
                    if (!dates.contains(weekStr)) {
                        dates.add(weekStr);
                    }
                    startDate = new Date(startDate.getTime() + 24 * 3600 * 1000L);
                }
                int week = CommonDateFunction.getWeekNumber(startDate);
                cdStart.setTime(startDate);
                String weekStr = cdStart.get(Calendar.YEAR) + "-" + ((week > 9) ? week + "" : "0" + week);
                if (!dates.contains(weekStr)) {
                    dates.add(weekStr);
                }

            } catch (ParseException e) {
            }

            return dates;
        }

        if (dateString.contains(",")) {
            for (String s : dateString.split(",")) {
                dates.addAll(convertVariousDateToSpreadWeek(s));
            }
            return dates;
        }

        String[] ds = dateString.split("-");
        if (ds.length == 1) {
            return convertVariousDateToSpreadWeek(ds[0] + "-01-01" + "~" + ds[0] + "-12-31");
        } else if (ds.length == 2) {
            String lastDate = CommonDateFunction.getLastDayOfMonth(dateString + "-01");
            return convertVariousDateToSpreadWeek(dateString + "-01" + "~" + lastDate);
        } else {
            dates.add(dateString);
        }

        return dates;
    }

    public static List<String> convertVariousDateToSpreadMonth(String dateString) {
        List<String> dates = new ArrayList<String>();
        if (dateString.contains("~")) {
            try {
                Date startDate = CommonDateFunction.parseDate(dateString.split("~")[0], "yyyy-MM-dd");
                Date endDate = CommonDateFunction.parseDate(dateString.split("~")[1], "yyyy-MM-dd");
                Calendar cdStart = Calendar.getInstance();
                while (!startDate.equals(endDate)) {
                    int month = CommonDateFunction.getMonthNumber(startDate);

                    cdStart.setTime(startDate);
                    String monthStr = cdStart.get(Calendar.YEAR) + "-" + ((month > 9) ? month + "" : "0" + month);
                    if (!dates.contains(monthStr)) {
                        dates.add(monthStr);
                    }
                    startDate = new Date(startDate.getTime() + 24 * 3600 * 1000L);
                }
                int month = CommonDateFunction.getMonthNumber(startDate);
                cdStart.setTime(startDate);
                String monthStr = cdStart.get(Calendar.YEAR) + "-" + ((month > 9) ? month + "" : "0" + month);
                if (!dates.contains(monthStr)) {
                    dates.add(monthStr);
                }

            } catch (ParseException e) {
            }

            return dates;
        }

        if (dateString.contains(",")) {
            for (String s : dateString.split(",")) {
                dates.addAll(convertVariousDateToSpreadMonth(s));
            }
            return dates;
        }

        String[] ds = dateString.split("-");
        if (ds.length == 1) {
            return convertVariousDateToSpreadMonth(ds[0] + "-01-01" + "~" + ds[0] + "-12-31");
        } else if (ds.length == 2) {
            String lastDate = CommonDateFunction.getLastDayOfMonth(dateString + "-01");
            return convertVariousDateToSpreadMonth(dateString + "-01" + "~" + lastDate);
        } else {
            dates.add(dateString);
        }

        return dates;
    }

    public static List<String> convertVariousDateToSpreadQuarter(String dateString) {
        List<String> dates = new ArrayList<String>();
        if (dateString.contains("~")) {
            try {
                Date startDate = CommonDateFunction.parseDate(dateString.split("~")[0], "yyyy-MM-dd");
                Date endDate = CommonDateFunction.parseDate(dateString.split("~")[1], "yyyy-MM-dd");
                Calendar cdStart = Calendar.getInstance();
                while (!startDate.equals(endDate)) {
                    int quarter = CommonDateFunction.getQuarterNumber(startDate);

                    cdStart.setTime(startDate);
                    String quarterStr = cdStart.get(Calendar.YEAR) + "-" + quarter;
                    if (!dates.contains(quarterStr)) {
                        dates.add(quarterStr);
                    }
                    startDate = new Date(startDate.getTime() + 24 * 3600 * 1000L);
                }
                int quarter = CommonDateFunction.getQuarterNumber(startDate);
                cdStart.setTime(startDate);
                String quarterStr = cdStart.get(Calendar.YEAR) + "-" + quarter;
                if (!dates.contains(quarterStr)) {
                    dates.add(quarterStr);
                }

            } catch (ParseException e) {
            }

            return dates;
        }

        if (dateString.contains(",")) {
            for (String s : dateString.split(",")) {
                dates.addAll(convertVariousDateToSpreadQuarter(s));
            }
            return dates;
        }

        String[] ds = dateString.split("-");
        if (ds.length == 1) {
            return convertVariousDateToSpreadQuarter(ds[0] + "-01-01" + "~" + ds[0] + "-12-31");
        } else if (ds.length == 2) {
            String lastDate = CommonDateFunction.getLastDayOfMonth(dateString + "-01");
            return convertVariousDateToSpreadQuarter(dateString + "-01" + "~" + lastDate);
        } else {
            dates.add(dateString);
        }

        return dates;
    }

    private static int getWeekNumber(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.setFirstDayOfWeek(Calendar.MONDAY);
        return cd.get(Calendar.WEEK_OF_YEAR);
    }

    private static int getMonthNumber(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        return cd.get(Calendar.MONTH);
    }

    private static int getQuarterNumber(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int month = cd.get(Calendar.MONTH);
        if (month >= 1 && month <= 3) {
            return 1;
        } else if (month >= 4 && month <= 6) {
            return 2;
        } else if (month >= 7 && month <= 9) {
            return 3;
        } else {
            return 4;
        }
    }

    private static int getYearNumber(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        return cd.get(Calendar.YEAR);
    }

    public static List<Date> convertDatesStringToDates(List<String> datesString) {
        List<Date> ret = new ArrayList<Date>();
        for (String s : datesString) {
            Date d = null;
            try {
                d = CommonDateFunction.parseDate(s, "yyyy-MM-dd");
                ret.add(d);
            } catch (ParseException e) {
            }

        }

        return ret;
    }

    public static String getOneDate(String dateString) {
        if (dateString.contains(",") || dateString.contains("~") || dateString.length() != 10) {
            if (dateString.contains(",") && dateString.split(",")[0].equals(dateString.split(",")[1])) {
                return dateString.split(",")[0];
            } else {
                return "";
            }
        }
        return dateString;
    }

    public static List<String> getLastYearDates(List<String> dates) {
        List<String> ret = new ArrayList<String>();
        for (String date : dates) {
            String lastDate = (Integer.parseInt(date.split("-")[0]) - 1) + "-" + date.split("-")[1] + "-" + date.split("-")[2];
            ret.add(lastDate);
        }
        return ret;
    }

    public static String getMonthMiddleDay(String date) {
        String[] dateSplit = date.split("-");
        int middle = new BigDecimal(dateSplit[2]).divide(new BigDecimal(2), BigDecimal.ROUND_HALF_UP).intValue();

        return dateSplit[0] + "-" + dateSplit[1] + "-" + (middle > 9 ? middle : "0" + middle);
    }

    public static String getYearMiddleDay(String date) {
        String firstDate = date.split("-")[0] + "-01-01";
        return CommonDateFunction.getMiddleDay(firstDate, date);
    }

    public static String getWeekMiddleDay(String date) {
        String firstDate = CommonDateFunction.getFirstDayOfWeek(date);
        return CommonDateFunction.getMiddleDay(firstDate, date);
    }

    public static String getQuarterMiddleDay(String date) {
        String firstDate = CommonDateFunction.getFirstDayOfQuarter(date);
        return CommonDateFunction.getMiddleDay(firstDate, date);
    }

    public static String getMiddleDay(String date1, String date2) {
        String[] dateSplit1 = date1.split("-");
        String[] dateSplit2 = date2.split("-");
        Calendar cd1 = Calendar.getInstance();
        Calendar cd2 = Calendar.getInstance();
        cd1.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));
        cd2.set(Integer.parseInt(dateSplit2[0]), Integer.parseInt(dateSplit2[1]) - 1, Integer.parseInt(dateSplit2[2]));
        long timeOffset = cd2.getTimeInMillis() - cd1.getTimeInMillis();
        int dayCount = (int) (timeOffset / 1000 / 3600 / 24 + 1);
        int halfDays = new BigDecimal(dayCount).divide(new BigDecimal(2), BigDecimal.ROUND_HALF_UP).intValue();
        Date dateRet = new Date(cd1.getTimeInMillis() + (halfDays - 1) * 24L * 3600 * 1000);
        return CommonDateFunction.formatDate(dateRet);
    }

    //circle: 0：天，1: 周，2：月，3：季度，4：年
    public static String convertCircleToStartDate(int circle, String date) {
        if (circle == 0) {
            return date;
        } else if (circle == 1) {
            return CommonDateFunction.getFirstDayOfWeek(date);
        } else if (circle == 2) {
            return CommonDateFunction.getFirstDayOfMonth(date);
        } else if (circle == 3) {
            return CommonDateFunction.getFirstDayOfQuarter(date);
        } else if (circle == 4) {
            return CommonDateFunction.getFirstDayOfYear(date);
        }

        return date;
    }

    //circle: 0：天，1: 周，2：月，3：季度，4：年
    public static String convertCircleToEndDate(int circle, String date) {
        if (circle == 0) {
            return date;
        } else if (circle == 1) {
            return CommonDateFunction.getLastDayOfWeek(date);
        } else if (circle == 2) {
            return CommonDateFunction.getLastDayOfMonth(date);
        } else if (circle == 3) {
            return CommonDateFunction.getLastDayOfQuarter(date);
        } else if (circle == 4) {
            return CommonDateFunction.getLastDayOfYear(date);
        }

        return date;
    }

    //circle: 0：天，1: 周，2：月，3：季度，4：年
    public static String convertCircleToBFStartDate(int circle, String date) {
        if (circle == 10) {
            String tenbfDate = null;
            try {
                tenbfDate = CommonDateFunction.getTenBeforeDate(date, "yyyy-MM-dd");
            } catch (ParseException e) {
            }
            return tenbfDate;
        }
        if (circle == 0) {
            String bfDate = null;
            try {
                bfDate = CommonDateFunction.getBeforeDate(date, "yyyy-MM-dd");
            } catch (ParseException e) {
            }
            return bfDate;
        } else if (circle == 1) {
            return CommonDateFunction.getFirstDayOfLastWeek(date);
        } else if (circle == 2) {
            return CommonDateFunction.getFirstDayOfLastMonth(date);
        } else if (circle == 3) {
            return CommonDateFunction.getFirstDayOfLastQuarter(date);
        } else if (circle == 4) {
            return CommonDateFunction.getFirstDayOfLastYear(date);
        }

        return date;
    }

    //circle: 0：天，1: 周，2：月，3：季度，4：年
    public static String convertCircleToBFEndDate(int circle, String date) {
        if (circle == 0) {
            return date;
        } else if (circle == 1) {
            return CommonDateFunction.getLastDayOfLastWeek(date);
        } else if (circle == 2) {
            return CommonDateFunction.getLastDayOfLastMonth(date);
        } else if (circle == 3) {
            return CommonDateFunction.getLastDayOfLastQuarter(date);
        } else if (circle == 4) {
            return CommonDateFunction.getLastDayOfLastYear(date);
        }

        return date;
    }

    public static String getLastWeekToday(String date) {
        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));
        int week = cd.get(Calendar.WEEK_OF_YEAR);
        cd.set(Calendar.WEEK_OF_YEAR, week - 1);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getNDayBefore(int n, String date) {
        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));

        int day = cd.get(Calendar.DAY_OF_YEAR);
        cd.set(Calendar.DAY_OF_YEAR, day - n + 1);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static List<String> generalYear(String date) {
        List<String> ret = new ArrayList<String>();
        String nextDate = CommonDateFunction.getFirstDayOfYear(date);
        for (int i = 0; i < 366; i++) {
            ret.add(nextDate);
            if (nextDate.equals(date)) {
                break;
            }
            nextDate = CommonDateFunction.nextDay(nextDate);
        }
        return ret;
    }

    public static String nextDay(String date) {
        if (date == null || date.isEmpty()) {
            return "";
        }

        String[] dateSplit1 = date.split("-");
        Calendar cd = Calendar.getInstance();
        cd.set(Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]) - 1, Integer.parseInt(dateSplit1[2]));
        int next = cd.get(Calendar.DAY_OF_YEAR) + 1;
        cd.set(Calendar.DAY_OF_YEAR, next);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cd.getTime());
    }

    public static String getGiveDateBefore(String date) {
        Calendar c = Calendar.getInstance();
        Date date1 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = sdf.parse(date);
        } catch (ParseException e) {

        }
        c.setTime(date1);
        int day1 = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day1 - 1);
        String dayAfter = sdf.format(c.getTime());
        return dayAfter;
    }

    //获取农历除夕，春节，十五，端午，中秋对应的公历日期
    /*public static Map<String, String> getLunarFestival(int year) {
        Map<String, String> ret = new LinkedHashMap<String, String>();
        Lunar chunjie = new Lunar(false, year, 1, 1);
        Lunar shiwu = new Lunar(false, year, 1, 15);
        Lunar duanwu = new Lunar(false, year, 5, 5);
        Lunar zhongqiu = new Lunar(false, year, 8, 15);

        List<Lunar> lunarList = new ArrayList<Lunar>();

        lunarList.add(chunjie);
        lunarList.add(shiwu);
        lunarList.add(duanwu);
        lunarList.add(zhongqiu);

        List<Date> dates = new ArrayList<Date>();
        for (Lunar l : lunarList) {
            Solar s = LunarCalendar.LunarToSolar(l);

            Date d = null;
            try {
                d = CommonDateFunction.parseDate(s.solarYear + "-" + s.solarMonth + "-" + s.solarDay, "yyyy-MM-dd");
            } catch (ParseException e) {
            }
            dates.add(d);
        }

        dates.add(0, new Date(dates.get(0).getTime() - 3600 * 24 * 1000));

        ret.put(CommonDateFunction.formatDate(dates.get(0)), "除夕");
        ret.put(CommonDateFunction.formatDate(dates.get(1)), "春节");
        ret.put(CommonDateFunction.formatDate(dates.get(2)), "元宵节");
        ret.put(CommonDateFunction.formatDate(dates.get(3)), "端午节");
        ret.put(CommonDateFunction.formatDate(dates.get(4)), "中秋节");
        return ret;
    }*/
}
