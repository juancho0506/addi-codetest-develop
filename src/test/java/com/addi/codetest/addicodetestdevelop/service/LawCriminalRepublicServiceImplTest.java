package com.addi.codetest.addicodetestdevelop.service;

import com.addi.codetest.addicodetestdevelop.configuration.WiremockConfig;
import com.addi.codetest.addicodetestdevelop.exception.LawCriminalHIstoryServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LawCriminalRepublicServiceImplTest {

    private static CandidateMockService mockService;
    private LawCriminalRepublicServiceImpl lawCriminalRepublicService;
    private WiremockConfig wiremockConfig;

    @Before
    public void setUp() throws Exception {
        wiremockConfig = new WiremockConfig();
        wiremockConfig.setHost("localhost");
        wiremockConfig.setPort(8080);
        mockService = new CandidateMockService(wiremockConfig);
        mockService.launchWireMockServer();
        lawCriminalRepublicService = new LawCriminalRepublicServiceImpl(wiremockConfig);
    }

    @After
    public void tearDown() throws Exception {
        mockService.shutdownWiremockServer();
    }

    @Test
    public void isClean() throws LawCriminalHIstoryServiceException {
        //Given
        String typeId = "CC";
        String numId = "121212";
        //Execute
        boolean result = lawCriminalRepublicService.isClean(typeId, numId);
        //Assert
        assertThat(result).isTrue();
    }
}