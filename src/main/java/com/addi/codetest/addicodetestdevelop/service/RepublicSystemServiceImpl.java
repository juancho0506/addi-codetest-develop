package com.addi.codetest.addicodetestdevelop.service;


import com.addi.codetest.addicodetestdevelop.configuration.WiremockConfig;
import com.addi.codetest.addicodetestdevelop.exception.RepublicSystemServiceException;
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
public class RepublicSystemServiceImpl implements RepublicSystemService {

    private static final String REPUBLIC_ID_SERVICE_PATH = "/republic/service/verify";

    private WiremockConfig wiremockConfig;


    @Autowired
    public RepublicSystemServiceImpl(WiremockConfig config) {
        this.wiremockConfig = config;
    }

    @Override
    public Boolean exists(String typeId, String id) throws RepublicSystemServiceException {
        //Call endpoint
            HttpGet request = new HttpGet("http://" + wiremockConfig.getHost() + ":" +
                wiremockConfig.getPort() + REPUBLIC_ID_SERVICE_PATH);

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(request);
            String response = convertResponseToString(httpResponse);
            System.out.println("Candidate exists on republic registration system? :  " + response);
            if (response.equalsIgnoreCase("true")) {
                return true;
            } else {
                return false;
            }

        } catch (IOException e) {
            throw new RepublicSystemServiceException("ERROR: Can't connect or service error.", e);
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
