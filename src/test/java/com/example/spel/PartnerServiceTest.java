package com.example.spel;

import com.example.spel.adapter.DynamicPartnerClient;
import com.example.spel.config.DynamicRequestBuilder;
import com.example.spel.config.FeignUrlResolver;
import com.example.spel.model.PartnerConfig;
import com.example.spel.service.PartnerConfigService;
import com.example.spel.service.PartnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PartnerServiceTest {
    @Mock
    private PartnerConfigService partnerConfigService;
    @Mock
    private DynamicPartnerClient dynamicPartnerClient;
    @Mock
    private FeignUrlResolver feignUrlResolver;
    @Mock
    private DynamicRequestBuilder dynamicRequestBuilder;

    private PartnerService partnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        partnerService = new PartnerService(partnerConfigService, dynamicPartnerClient, feignUrlResolver, dynamicRequestBuilder);
    }

    @Test
    void processPartnerRequest_whenExternalEndpointRequired_shouldCallEndpoint() {
        // Arrange
        String partnerName = "TestPartner";
        Object sourceData = new Object();
        PartnerConfig config = new PartnerConfig();
        config.setEndpointUrl("https://api.testpartner.com");
        config.setCallExternalEndpoint(true);

        Object builtRequest = new Object();
        Object endpointResponse = new Object();

        when(partnerConfigService.getPartnerConfig(partnerName)).thenReturn(config);
        when(dynamicRequestBuilder.buildRequest(config, sourceData)).thenReturn(builtRequest);
        when(dynamicPartnerClient.callPartnerEndpoint(builtRequest)).thenReturn(endpointResponse);

        // Act
        partnerService.processPartnerRequest(partnerName, sourceData);

        // Assert
        verify(partnerConfigService).getPartnerConfig(partnerName);
        verify(dynamicRequestBuilder).buildRequest(config, sourceData);
        verify(feignUrlResolver).setUrl("https://api.testpartner.com");
        verify(dynamicPartnerClient).callPartnerEndpoint(builtRequest);
        verify(feignUrlResolver).clear();
    }

    @Test
    void processPartnerRequest_whenExternalEndpointNotRequired_shouldNotCallEndpoint() {
        // Arrange
        String partnerName = "TestPartner";
        Object sourceData = new Object();
        PartnerConfig config = new PartnerConfig();
        config.setCallExternalEndpoint(false);

        when(partnerConfigService.getPartnerConfig(partnerName)).thenReturn(config);

        // Act
        partnerService.processPartnerRequest(partnerName, sourceData);

        // Assert
        verify(partnerConfigService).getPartnerConfig(partnerName);
        verifyNoInteractions(dynamicRequestBuilder);
        verifyNoInteractions(feignUrlResolver);
        verifyNoInteractions(dynamicPartnerClient);
    }
}
