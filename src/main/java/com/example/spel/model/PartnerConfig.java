package com.example.spel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PartnerConfig {
    private String partnerName;
    private String endpointUrl;
    private boolean callExternalEndpoint;
    private Map<String, String> requestMapping;
}
