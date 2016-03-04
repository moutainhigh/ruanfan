package com.sixmac.utils;

import java.math.BigDecimal;

/**
 * Created by wangbin on 2015/7/20.
 */
public class LocationUtil {

    private static double EARTH_RADIUS = 6378.137;


    public static void main(String[] args) {
        Double dis = geographicDistance(114.438969,30.426316,114.403682,30.477379);
        System.out.println(dis);
    }

    public static double geographicDistance(double lng1, double lat1, double lng2, double lat2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;
        return new BigDecimal(distance).intValue();
    }





}
