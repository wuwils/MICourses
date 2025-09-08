package com.xiaomi.wusheng.work_0223.question_1;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Main{
    public static void main(String[] args){
        String url = "https://api.vvhan.com/api/dailyEnglish?type=sj";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", "Ubuntu 20.04");
            request.setHeader("Accept", "application/json");
            request.setHeader("Accept-Language", "en-US,en");

            // 执行HTTP请求并获取响应对象
            try (CloseableHttpResponse response = httpClient.execute(request)){
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("Response Status Code: " + statusCode);

                if (statusCode == 200){
                    HttpEntity entity = response.getEntity();
                    if (entity != null){
                        // 打印字符串形式的JSON数据
                        String jsonResponse = EntityUtils.toString(entity);
                        System.out.println("Response JSON: " + jsonResponse);

                        // 解析JSON数据
                        ObjectMapper objectMapper = new ObjectMapper();
                        ApiResponse apiResponse = objectMapper.readValue(jsonResponse, ApiResponse.class);

                        System.out.println("Success: " + apiResponse.isSuccess());
                        System.out.println("中文: " + apiResponse.getData().getZh());
                        System.out.println("英文: " + apiResponse.getData().getEn());
                        System.out.println("图片: " + apiResponse.getData().getPic());
                    }
                }else{
                    System.out.println("Error! Status code: " + statusCode);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

