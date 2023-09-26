package com.example.demo.service;
import com.example.demo.model.BrandInfo;
import com.example.demo.model.HistoryIncomeSummarys;
import com.example.demo.model.StoreListInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import javax.net.ssl.SSLContext;

import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.LoggerFactory;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ApiService {

    private RestTemplate restTemplate;

    @Value("${api.base.url}")
    private String apiBaseUrl;

    @Value("${api.recommend.endpoint}")
    private String recommendEndpoint;

    @Value("${api.financial.stat.endpoint}")
    private String financialStatEndpoint;
    
    private final JdbcTemplate jdbcTemplate; // Inject a JdbcTemplate
    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);


    public ApiService(RestTemplateBuilder restTemplateBuilder, JdbcTemplate jdbcTemplate) throws Exception {
        // If using a self-signed certificate
        TrustSelfSignedStrategy trustStrategy = new TrustSelfSignedStrategy();
        SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(null, trustStrategy).build();
        CloseableHttpClient httpClient = HttpClients.custom().setSslcontext(sslContext).build();
        this.restTemplate = restTemplateBuilder.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient)).build();

        this.jdbcTemplate = jdbcTemplate;
    }

    public ResponseEntity<Map> sendRecommendData(
        String serialNo, String partnerMchId, BrandInfo brandInfo, List<StoreListInfo> storeListInfo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> data = new HashMap<>();
        data.put("serialNo", serialNo);
        data.put("partnerMchId", partnerMchId);
        data.put("brandInfo", brandInfo);
        data.put("storeListInfo", storeListInfo);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(data, headers);

        String recommendApiUrl = apiBaseUrl + recommendEndpoint;

        try {
            return restTemplate.postForEntity(recommendApiUrl, requestEntity, Map.class);
        } catch (HttpClientErrorException e) {
            // Handle specific HTTP errors if needed
            throw new RuntimeException("recommend API call failed: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // General exception handling
            throw new RuntimeException("recommend API call failed: " + e.getMessage());
        }
    }

    public ResponseEntity<Map> sendHistoryFinancialStatData(
        String batchNo, 
        String partnerMchId, 
        String brandName, 
        Date batchDatetime, 
        Integer totalCount, 
        Double totalTurnover, 
        Double totalActualAmount, 
        String remark, 
        List<HistoryIncomeSummarys> historyIncomeSummarys) { // assuming historyIncomeSummarys data is passed as a List of Map
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, Object> data = new HashMap<>();
        data.put("batchNo", batchNo);
        data.put("partnerMchId", partnerMchId);
        data.put("brandName", brandName);
        data.put("batchDatetime", batchDatetime);
        data.put("totalCount", totalCount);
        data.put("totalTurnover", totalTurnover);
        data.put("totalActualAmount", totalActualAmount);
        data.put("remark", remark);
        data.put("historyIncomeSummarys", historyIncomeSummarys);
    
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(data, headers);
    
        String financialStatApiUrl = apiBaseUrl + financialStatEndpoint;

        try {
            return restTemplate.postForEntity(financialStatApiUrl, requestEntity, Map.class);
        } catch (HttpClientErrorException e) {
            logger.error("Financial Stat API call failed: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Financial Stat API call failed: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Financial Stat API call failed: {}", e.getMessage());
            throw new RuntimeException("Financial Stat API call failed: " + e.getMessage());
        }
    }
    
    

    public ResponseEntity<Map> sendDataFromDB() {
        // Assuming you have a SQL query to retrieve BrandInfo from the database
        String sqlQuery = "SELECT brandEntName, brandCode, industry, contacts, telephone, contactsPost FROM brand_info limit 1;";

        // Execute the query and map the result to BrandInfo
        BrandInfo brandInfo = jdbcTemplate.queryForObject(sqlQuery, (resultSet, rowNum) -> {
            return new BrandInfo(
                resultSet.getString("brandEntName"),
                resultSet.getString("brandCode"),
                resultSet.getString("industry"),
                resultSet.getString("contacts"),
                resultSet.getString("telephone"),
                resultSet.getString("contactsPost")
            );
        });

        // Create sample data for storeListInfo
        List<StoreListInfo> storeListInfo = new ArrayList<>();
        StoreListInfo store1 = new StoreListInfo("StoreCode1", "StoreName1", "2021-01-01", "StoreEntName1", "Address1", "GAODE", 114.06455, 22.55031);
        StoreListInfo store2 = new StoreListInfo("StoreCode2", "StoreName2", "2021-01-02", "StoreEntName2", "Address2", "GAODE", 114.06456, 22.55032);

        storeListInfo.add(store1);
        storeListInfo.add(store2);

        // Call the sendDataToApi method with the retrieved BrandInfo
        logger.info("API call brandInfo: {}", brandInfo);
        ResponseEntity<Map> response = sendRecommendData("serialNo", "partnerMchId", brandInfo, storeListInfo);
        logger.info("API returns: {}", response);

        return response;
    }    

    @Scheduled(fixedRate = 10000) // Run every 60 seconds
    public void sendDataFromDBPeriodically() {
        // Implement your logic here
        ResponseEntity<Map> response = sendDataFromDB();
        // You can log the result or perform other actions with the response
    }    
}
