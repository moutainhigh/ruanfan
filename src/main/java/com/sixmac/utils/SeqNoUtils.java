package com.sixmac.utils;



import java.util.Date;


public final class SeqNoUtils {
	
	public static void main(String[] args){
        String code = getOrderCode(10,8);

        System.out.println(code);
        System.out.println(code.length());
    }

	public static final String getBusinessCode(Integer id){
        String result = get("buss", id, 6);
		return result;
	}


    public static final String getUserCode(Integer id, int len){
        String seq=id+"";
        for(int i=seq.length(); i<len;i++){
            seq=(int)(Math.random()*10)+seq;
        }
        return DateUtils.dateToStringWithFormat(new Date(), "dd")+seq;
    }

    public static final String getOrderCode(Integer id, int len){
        String seq=id+"";
        for(int i=seq.length(); i<len;i++){
            seq=(int)(Math.random()*10)+seq;
        }
        return DateUtils.dateToStringWithFormat(new Date(), "yyMMddhh")+seq;
    }


    public static final String getCouponCode(Integer id, int len){
        String seq=id+"";
        for(int i=seq.length(); i<len;i++){
            seq=(int)(Math.random()*10)+seq;
        }
        return DateUtils.dateToStringWithFormat(new Date(), "yyMMdd")+seq;
    }

	
	public static final String get(String prefix, Integer id, int len){
		String seq=id+"";
		for(int i=seq.length(); i<len;i++){
			seq="0"+seq;
		}
		return prefix+ DateUtils.dateToStringWithFormat(new Date(), "yy")+seq;
	}

    public static final String getMallOrderCode(Integer id){
        int len = 3;
        String seq=id+"";
        for(int i=seq.length(); i<len;i++){
            seq="0"+seq;
        }
        return DateUtils.dateToStringWithFormat(new Date(), "yyyyMMddHHmmss")+seq;
    }




}
