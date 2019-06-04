package com.addi.codetest.addicodetestdevelop.service;

import com.addi.codetest.addicodetestdevelop.configuration.WiremockConfig;
import com.addi.codetest.addicodetestdevelop.exception.LawCriminalHIstoryServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wiremock.org.apache.http.HttpResponse;
import wiremock.org.apache.http.client.methods.HttpGet;
import wiremock.org.apache.http.impl.client.CloseableHttpClient;
import wiremock.org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Service
public class LawCriminalRepublicServiceImpl implements LawCriminalRepublicService {

    private static final String LAWCRIMINAL_HISTORY_VERIFY = "/lawcriminal/history/verify";

    private WiremockConfig wiremockConfig;
    private CloseableHttpClient httpClient;

    @Autowired
    public LawCriminalRepublicServiceImpl(WiremockConfig wiremockConfig) {
        this.wiremockConfig = wiremockConfig;
    }

    @Override
    public Boolean isClean(String typeId, String id) throws LawCriminalHIstoryServiceException {
        //Call endpoint
        HttpGet request = new HttpGet("http://" + wiremockConfig.getHost() + ":" +
                wiremockConfig.getPort() + LAWCRIMINAL_HISTORY_VERIFY);
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(request);
            String response = convertResponseToString(httpResponse);
            System.out.println("Candidate is clean on law and criminal system? :  " + response);
            if (response.equalsIgnoreCase("clean")) {
                return true;
            } else {
                return false;
            }

        } catch (IOException e) {
            throw new LawCriminalHIstoryServiceException("ERROR: Can't connect or service error.", e);
        }
    }

    private String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }
}
