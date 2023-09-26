package com.example.demo.model;

public class StoreListInfo {
    private String storeCode;                 // 门店在平台方系统中的编码
    private String storeName;                 // 门店在平台方系统中的简称
    private String storeEntryTime;            // 门店入驻平台时间
    private String storeEntName;              // 门店营业执照上的企业名称
    private String address;                   // 门店经营所在地址
    private String geographicPlatform;        // 提供经纬度平台
    private Double longitude;                 // 门店经度信息
    private Double latitude;                  // 门店纬度信息

    // Default constructor
    public StoreListInfo() {}

    // All args constructor
    public StoreListInfo(String storeCode, String storeName, String storeEntryTime, 
                         String storeEntName, String address, String geographicPlatform, 
                         Double longitude, Double latitude) {
        this.storeCode = storeCode;
        this.storeName = storeName;
        this.storeEntryTime = storeEntryTime;
        this.storeEntName = storeEntName;
        this.address = address;
        this.geographicPlatform = geographicPlatform;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Getters and setters
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreEntryTime() {
        return storeEntryTime;
    }

    public void setStoreEntryTime(String storeEntryTime) {
        this.storeEntryTime = storeEntryTime;
    }

    public String getStoreEntName() {
        return storeEntName;
    }

    public void setStoreEntName(String storeEntName) {
        this.storeEntName = storeEntName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeographicPlatform() {
        return geographicPlatform;
    }

    public void setGeographicPlatform(String geographicPlatform) {
        this.geographicPlatform = geographicPlatform;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
