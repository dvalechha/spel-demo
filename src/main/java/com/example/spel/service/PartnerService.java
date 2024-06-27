package com.example.spel.service;

import com.example.spel.adapter.DynamicPartnerClient;
import com.example.spel.config.DynamicRequestBuilder;
import com.example.spel.config.FeignUrlResolver;
import com.example.spel.model.PartnerConfig;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class PartnerService {
    private final PartnerConfigService partnerConfigService;
    private final DynamicPartnerClient dynamicPartnerClient;
    private final FeignUrlResolver feignUrlResolver;
    private final DynamicRequestBuilder requestBuilder;

    // Constructor

    public void processPartnerRequest(String partnerName, Object sourceData) {
        PartnerConfig config = partnerConfigService.getPartnerConfig(partnerName);

        // Steps 1-3 remain the same

        // Step 4: Call external endpoint if required
        if (config.isCallExternalEndpoint()) {
            Object requestData = requestBuilder.buildRequest(config, sourceData);
            callExternalEndpoint(config, requestData);
        }
    }

    private void callExternalEndpoint(PartnerConfig config, Object requestData) {
        try {
            feignUrlResolver.setUrl(config.getEndpointUrl());
            Object response = dynamicPartnerClient.callPartnerEndpoint(requestData);
            // Process the response as needed
        } finally {
            feignUrlResolver.clear();
        }
    }
}
