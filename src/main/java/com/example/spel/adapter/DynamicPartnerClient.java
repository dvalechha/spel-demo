package com.example.spel.adapter;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "dynamicPartnerClient", url = "#{@feignUrlResolver.getUrl()}")
public interface DynamicPartnerClient {
    @PostMapping
    Object callPartnerEndpoint(@RequestBody Object request);
}
