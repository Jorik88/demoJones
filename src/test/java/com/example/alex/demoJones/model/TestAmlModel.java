package com.example.alex.demoJones.model;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class TestAmlModel {

    private static final String REQUEST_URL = "http://localhost:9001/itwGateWS/exec/FISPut";
    private static final String UTF_ENCODING = "UTF-8";

    @Test
    public void testUnmarshal() throws JAXBException {

        String value = "<FISRq>\n" +
                "    <Msg Scheme=\"CRB\" Version=\"1.0\">\n" +
                "        <Request RqId=\"CRB/FindSuspiciousRq\">\n" +
                "            <Body>\n" +
                "                <req-ROWSET>\n" +
                "                    <req-ROW>\n" +
                "                        <parameters>\n" +
                "        <p name=\"person_address\" type=\"A\">p o box 441645 miamiflorida united states</p>\n" +
                "                        </parameters>\n" +
                "\t\t\t\t\t\t<parameters>\n" +
                "\t\t\t\t\t\t\t<p name=\"person_name\" type=\"N\">Иванов иван иванович</p>\n" +
                "\t\t\t\t\t\t</parameters>\n" +
                "                    </req-ROW>\n" +
                "                </req-ROWSET>\n" +
                "            </Body>\n" +
                "        </Request>\n" +
                "    </Msg>\n" +
                "</FISRq>";

        FisRq fisRq = fromXML(value, FisRq.class);
        System.out.println(fisRq);
    }

    @Test
    public void testDeserialize() throws JAXBException, IOException {
        Parameter addressParameter = new Parameter("person_address", "A", "p o box 441645 miamiflorida united states");
        Parameter nameParameter = new Parameter("person_name", "N", "Иванов иван иванович");
        List<Parameters> parameters = Arrays.asList(new Parameters(addressParameter), new Parameters(nameParameter));

        FisRq fisRq = new FisRq(parameters);

        String result = toXML(fisRq);
        System.out.println(result);
    }


    @Test
    public void testSendRequest() throws IOException, JAXBException {
        Parameter addressParameter = new Parameter("person_address", "A", "p o box 441645 miamiflorida united states");
        Parameter nameParameter = new Parameter("person_name", "N", "Иванов иван иванович");
        List<Parameters> parameters = Arrays.asList(new Parameters(addressParameter), new Parameters(nameParameter));

        FisRq fisRq = new FisRq(parameters);

        String response = sendRequest(fisRq);

        System.out.println(response);
    }

    @Test
    public void testClearRequestParameters() {
        Parameter addressParameter = new Parameter("person_address", "A", "p o box 441645 miamiflorida united states");
        Parameter nameParameter = new Parameter("person_name", "N", "Иванов иван иванович");
        List<Parameters> parameters = Arrays.asList(new Parameters(addressParameter), new Parameters(nameParameter));

        FisRq fisRq = new FisRq(parameters);

        System.out.println(fisRq);

        List<Parameters> parameters1 = fisRq.getParameters();
        parameters1.clear();

        System.out.println(fisRq);
    }

    private String sendRequest(FisRq fisRq) throws IOException, JAXBException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost httpPost = new HttpPost(REQUEST_URL);
            httpPost.setEntity(new StringEntity(toXML(fisRq), UTF_ENCODING));
            httpPost.setHeader("Content-Type", "text/xml");

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        }
    }

    public static <T> String toXML(T object) throws JAXBException, IOException {
        try (StringWriter writer = new StringWriter()) {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "windows-1251");
            marshaller.marshal(object, writer);
            return writer.toString();
        }
    }

    public static  <T> T fromXML(String xml, Class<T> clazz) throws JAXBException {
        try (StringReader reader = new StringReader(xml)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "windows-1251");
            return (T) unmarshaller.unmarshal(reader);
        }
    }
}
