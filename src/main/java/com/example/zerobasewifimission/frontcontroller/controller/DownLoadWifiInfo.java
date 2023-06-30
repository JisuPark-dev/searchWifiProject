package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.WifiInfoRepository;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DownLoadWifiInfo implements Controller {
    WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String oneWifiInfoFromApi = wifiInfoRepository.getWifiInfoFromApi(1, 1);
        int totalCount = wifiInfoRepository.getTotalCount(oneWifiInfoFromApi);
        model.put("totalCount", totalCount);

        for (int i = 0; ; i++) {
            final int startIdx = 1 + i * 1000;
            final int endIdx = 1000 + i * 1000;
            final int cnt = 1 + i * 1000;

            CompletableFuture.supplyAsync(() -> wifiInfoRepository.getWifiInfoFromApi(startIdx, endIdx))
                    .thenApply(jsonString -> {
                        if (jsonString.equals("failed to get response")) {
                            throw new RuntimeException("Failed to get response");
                        }
                        return wifiInfoRepository.parseWifiInfo(jsonString);
                    })
                    .thenAccept(rows -> wifiInfoRepository.saveWifiInfo(cnt, rows))
                    .exceptionally(ex -> {
                        System.err.println("An error occurred during the Wi-Fi data processing: " + ex.getMessage());
                        return null;
                    });

            if (i > totalCount / 1000) break;
        }

        return "DownLoadWifiInfo";
    }
}

