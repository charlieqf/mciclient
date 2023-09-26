package com.example.demo.controller;
import com.example.demo.model.BrandInfo;
import com.example.demo.model.StoreListInfo;
import com.example.demo.model.HistoryIncomeSummarys;

import com.example.demo.service.ApiService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

@RestController
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/sendRecommendData")
    public Map<String, Object> sendRecommendData(@RequestBody Map<String, Object> data) {
        String serialNo = (String) data.get("serialNo");
        String partnerMchId = (String) data.get("partnerMchId");
        
        // Assuming BrandInfo has a proper constructor or method to convert a Map to BrandInfo
        // If not, this needs to be handled accordingly.
        Map<String, Object> brandInfoMap = (Map<String, Object>) data.get("brandInfo");
        BrandInfo brandInfo = new BrandInfo(
            (String) brandInfoMap.get("brandEntName"),
            (String) brandInfoMap.get("brandCode"),
            (String) brandInfoMap.get("industry"),
            (String) brandInfoMap.get("contacts"),
            (String) brandInfoMap.get("telephone"),
            (String) brandInfoMap.get("contactsPost")
        );
        List<Map<String, Object>> storeListInfoList = (List<Map<String, Object>>) data.get("storeListInfo");
        List<StoreListInfo> storeListInfo = convertToStoreListInfo(storeListInfoList);

        ResponseEntity<Map> response = apiService.sendRecommendData(serialNo, partnerMchId, brandInfo, storeListInfo);

        return response.getBody();
    }

    @PostMapping("/sendHistoryFinancialStatData")
    public Map<String, Object> sendHistoryFinancialStatData(@RequestBody Map<String, Object> data) {
        // Extracting data from request
        String batchNo = (String) data.get("batchNo");
        String partnerMchId = (String) data.get("partnerMchId");
        String brandName = (String) data.get("brandName");
        Date batchDatetime = new Date((Long) data.get("batchDatetime"));  // Assuming the date is sent as a timestamp
        Integer totalCount = (Integer) data.get("totalCount");
        Double totalTurnover = (Double) data.get("totalTurnover");
        Double totalActualAmount = (Double) data.get("totalActualAmount");
        String remark = (String) data.get("remark");

        List<Map<String, Object>> historyIncomeSummarysMaps = (List<Map<String, Object>>) data.get("historyIncomeSummarys");
        List<HistoryIncomeSummarys> historyIncomeSummarys = convertToHistoryIncomeSummarys(historyIncomeSummarysMaps);
    
        ResponseEntity<Map> response = apiService.sendHistoryFinancialStatData(
            batchNo, partnerMchId, brandName, batchDatetime, totalCount, 
            totalTurnover, totalActualAmount, remark, historyIncomeSummarys);
    
        return response.getBody();
    }
    

   private List<StoreListInfo> convertToStoreListInfo(List<Map<String, Object>> storeListInfoList) {
        List<StoreListInfo> result = new ArrayList<>();

        for (Map<String, Object> storeInfoMap : storeListInfoList) {
            String storeCode = (String) storeInfoMap.get("storeCode");
            String storeName = (String) storeInfoMap.get("storeName");
            String storeEntryTime = (String) storeInfoMap.get("storeEntryTime");
            String storeEntName = (String) storeInfoMap.get("storeEntName");
            String address = (String) storeInfoMap.get("address");
            String geographicPlatform = (String) storeInfoMap.get("geographicPlatform");
            Double longitude = (Double) storeInfoMap.get("longitude");
            Double latitude = (Double) storeInfoMap.get("latitude");

            StoreListInfo storeListInfo = new StoreListInfo(
                storeCode,
                storeName,
                storeEntryTime,
                storeEntName,
                address,
                geographicPlatform,
                longitude,
                latitude
            );

            result.add(storeListInfo);
        }

        return result;
    }

    private List<HistoryIncomeSummarys> convertToHistoryIncomeSummarys(List<Map<String, Object>> historyIncomeSummarysMaps) {
        List<HistoryIncomeSummarys> result = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Use appropriate format 

        for (Map<String, Object> summaryMap : historyIncomeSummarysMaps) {
            try {
                HistoryIncomeSummarys summary = new HistoryIncomeSummarys();
                summary.setStoreId((String) summaryMap.get("storeId"));
                summary.setStoreName((String) summaryMap.get("storeName"));
                summary.setBusinessDate(format.parse((String) summaryMap.get("businessDate"))); // parsing date
                summary.setTurnover((Double) summaryMap.get("turnover"));
                summary.setActualAmount((Double) summaryMap.get("actualAmount"));
                summary.setOrderCount((Integer) summaryMap.get("orderCount"));
                result.add(summary);
            } catch (Exception e) {
                // Handle the exception accordingly. Perhaps log the error or skip this entry.
            }
        }

        return result;
    }
}
