package com.cctv.excel;

import java.util.*;

public class ListMapSort {
    /*public static void main(String[] args) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        list.add(getData(0));
        list.add(getData(3));
        list.add(getData(5));
        list.add(getData(6));
        list.add(getData(2));

        System.out.println("排序前" + list);

        Collections.sort(list, new Comparator<Map<String, String>>() {
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                int a = Integer.valueOf(o1.get("countScore"));
                int b = Integer.valueOf(o2.get("countScore"));
                    return String.valueOf(a).compareTo(String.valueOf(b));
            }
        });

        System.out.println("排序后" + list);
    }

    private static Map<String, String> getData(int num) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("countScore", String.valueOf(num));
        return map;
    }*/

    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("name", "p");
        map1.put("cj", "5");
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("name", "h");
        map2.put("cj", "12");
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("name", "f");
        map3.put("cj", "31");
        list.add(map1);
        list.add(map3);
        list.add(map2);
        //排序前
        for (Map<String, Object> map : list) {
            System.out.println(map.get("cj"));
        }
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer name1 = Integer.valueOf(o1.get("cj").toString()) ;//name1是从你list里面拿出来的一个
                Integer name2 = Integer.valueOf(o2.get("cj").toString()) ; //name1是从你list里面拿出来的第二个name
                return name1.compareTo(name2);
            }
        });
        //排序后
        System.out.println("-------------------");
        for (Map<String, Object> map : list) {
            System.out.println(map.get("cj"));
        }
    }
}
