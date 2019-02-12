package com.example.alex.demoJones.service;

import org.junit.Test;

public class TestSendAmlRequest {

    private SendAmlRequestService service = new SendAmlRequestService();

    @Test
    public void testSendRequest() {
        String response = service.getAmlResult("p o box 441645 miamiflorida united states", "Иванов иван иванович");
        System.out.println(response);
    }
}
