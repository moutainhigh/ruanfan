package com.sixmac.core;

/**
 * Created by wangbin on 2015/9/28.
 */
public enum  OrderStatus {

    CANCEL(0),//取消订单
    UN_PAY(1),//待付款
    ALREADY_PAY(2),//已付款
    UN_SEND(3),//待发货
    ALREADY_SEND(4),//已发货
    FINISH(5);//完成

    private Integer status;

    OrderStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }


    public static Integer getValue(String name){
        for(OrderStatus eventType : OrderStatus.values()) {
            if(eventType.name().equals(name)){
                return eventType.getStatus();
            }
        }
        return null;
    }

    public static String getText(Integer status){

        String text ="";
        switch (status){
            case 0:
                text="取消订单";
                break;
            case 1:
                text="待付款";
                break;
            case 2:
                text="已付款";
                break;
            case 3:
                text="待发货";
                break;
            case 4:
                text="已发货";
                break;
            case 5:
                text="已完成";
                break;
            default:
                text="取消订单";
        }

        return text;

    }

}
