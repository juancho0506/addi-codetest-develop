package com.addi.codetest.addicodetestdevelop.service;

import com.addi.codetest.addicodetestdevelop.configuration.WiremockConfig;
import com.addi.codetest.addicodetestdevelop.exception.RepublicSystemServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RepublicSystemServiceImplTest {

    private static CandidateMockService mockService;
    private RepublicSystemServiceImpl republicSystemService;
    private WiremockConfig wiremockConfig;


    @Before
    public void setUp() throws Exception{
        wiremockConfig = new WiremockConfig();
        wiremockConfig.setHost("localhost");
        wiremockConfig.setPort(8080);
        mockService = new CandidateMockService(wiremockConfig);
        mockService.launchWireMockServer();
        republicSystemService = new RepublicSystemServiceImpl(wiremockConfig);
    }

    @After
    public void tearDown() throws Exception{
        mockService.shutdownWiremockServer();
    }

    @Test
    public void exists() throws RepublicSystemServiceException {
        //Given
        String typeId = "CC";
        String numId = "121212";
        //Execute
        boolean result = republicSystemService.exists(typeId, numId);
        //Assert
        assertThat(result).isTrue();
    }
}