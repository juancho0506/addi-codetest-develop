package com.addi.codetest.addicodetestdevelop.service;

import com.addi.codetest.addicodetestdevelop.configuration.WiremockConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Class used to mock the core services.
 */
@Component
public class CandidateMockService {

    private static final String DEFAULT_PATH = "/hello";
    private static final String REPUBLIC_ID_SERVICE_PATH = "/republic/service/verify";
    private static final String LAWCRIMINAL_HISTORY_VERIFY = "/lawcriminal/history/verify";

    private WiremockConfig wiremockConfig;
    private WireMockServer wireMockServer = new WireMockServer();

    @Autowired
    public CandidateMockService(WiremockConfig wiremockConfig) {
        this.wiremockConfig = wiremockConfig;
    }

    public void launchWireMockServer() {
        /**  Start mockserver  **/
        wireMockServer.start();

        configureFor(wiremockConfig.getHost(), wiremockConfig.getPort());
        //default test stub
        stubFor(get(urlEqualTo(DEFAULT_PATH)).willReturn(aResponse().withBody("Hello world!")));

        generateServicesStubs();
    }

    private void generateServicesStubs() {

        stubFor(get(urlEqualTo(REPUBLIC_ID_SERVICE_PATH))
                .willReturn(aResponse()
                        .withFixedDelay(5000)
                        .withBody("true")));

        stubFor(get(urlEqualTo(LAWCRIMINAL_HISTORY_VERIFY))
                .willReturn(aResponse()
                        .withFixedDelay(10000)
                        .withBody("clean")));

    }

    public void shutdownWiremockServer(){
        wireMockServer.shutdown();
    }

}
