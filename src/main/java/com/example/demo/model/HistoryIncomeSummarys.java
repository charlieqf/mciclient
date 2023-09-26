package com.example.demo.model;

import java.util.Date;

public class HistoryIncomeSummarys {

    private String storeId; // 门店编号
    private String storeName; // 门店名称
    private Date businessDate; // 营业日期
    private Double turnover; // 营业额
    private Double actualAmount; // 实收金额
    private Integer orderCount; // 当日订单数据量

    // Default constructor
    public HistoryIncomeSummarys() {}

    // All args constructor
    public HistoryIncomeSummarys(String storeId, String storeName, Date businessDate, 
                                 Double turnover, Double actualAmount, Integer orderCount) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.businessDate = businessDate;
        this.turnover = turnover;
        this.actualAmount = actualAmount;
        this.orderCount = orderCount;
    }

    // Getters and setters
    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }
}
