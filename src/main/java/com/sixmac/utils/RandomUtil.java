package com.sixmac.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class RandomUtil {
    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    public static String getCode() {
        StringBuffer sb = new StringBuffer("");
        // 产生6位随机数
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            int m = rand.nextInt(9);
            sb.append(m);
        }
        return sb.toString();

    }

    public static void main(String[] args) {
        System.out.println("八位随机数：" + generateShortUuid());
    }

    public static String generateShortUuid(String type, String orderNum) {
        // SM2015121000000001
        // SM + 20151210 + 00000001

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String today = formatter.format(currentTime);
        Integer temp = 0;
        String str = "";

        if (null == orderNum || orderNum.equals("")) {
            return type + today + "00000001";
        } else {
            String orderDate = orderNum.substring(2, 10);
            Integer orderInteger = Integer.parseInt(orderNum.substring(10));
            // 如果相等,则代表当天已经有流水,流水号递增
            if (orderDate.equals(today)) {
                orderInteger += 1;
                temp = orderInteger.toString().length();
                for (int i = 0; i < 8 - temp; i++) {
                    str += "0";
                }
                return type + today + str + orderInteger;
            } else {
                // 否则，重新生成新的流水号
                return type + today + "00000001";
            }
        }
    }
}
