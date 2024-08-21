/**
 * @Time: 2024/8/21 16:08
 * @Author: guoxun
 * @File: RestTemplate
 * @Description:
 */

package com.ncepu.utils.template;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExternalRestTemplate {

    @Value("${external-api.randomPic}")
    private String randomPicUrl;

    /**
     * 图片template
     */
    public String getRandomPicUrl(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("lx", "fengjing");
        queryParam.put("format", "json");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        URI uri = UriComponentsBuilder.fromHttpUrl(randomPicUrl).queryParam("lx", "fengjing")
                .queryParam("format", "json").build().toUri();
        String responseJson = restTemplate.getForObject(uri, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseJson);
        if (!jsonObject.getString("imgurl").equals("")){
            return jsonObject.getString("imgurl");
        }
        else {
            throw new RuntimeException("无法获取随机图片路径");
        }
    }
}
