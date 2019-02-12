package com.example.alex.demoJones.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class SendAmlRequestService {

    private String filePath = "templates/amlTemplate.xml";
    private static final String PERSONAL_DATA_HOLDER = "PERSONAL_DATA";
    private static final String ADDRESS_HOLDER = "ADDRESS";
    private static final String REQUEST_URL = "http://localhost:9001/itwGateWS/exec/FISPut";
    private static final String UTF_ENCODING = "UTF-8";

    public String getAmlResult(String addressData, String personalData) {
        try {
            InputStream resourceStream = SendAmlRequestService.class.getClassLoader().getResourceAsStream(filePath);
            StringWriter writer = new StringWriter();
            IOUtils.copy(resourceStream, writer, UTF_ENCODING);
            String xmlRequest = writer.toString().replace(ADDRESS_HOLDER, addressData).replace(PERSONAL_DATA_HOLDER, personalData);
            return sendRequest(xmlRequest);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String sendRequest(String data) throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost httpPost = new HttpPost(REQUEST_URL);
            httpPost.setEntity(new StringEntity(data, UTF_ENCODING));
            httpPost.setHeader("Content-Type", "text/xml");

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        }
    }
}
