package com.ojha.bootmotion;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class MotionControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void putCommandReturnsOk() throws Exception {
        HttpResponse response = Request.Post("http://localhost:" + port +"/command")
                .body(new StringEntity("{\"commandName\" : \"echo_cat\"}", ContentType.APPLICATION_JSON))
                .execute()
                .returnResponse();
        assertEquals("", EntityUtils.toString(response.getEntity()));
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void putCommandThrowsException() throws Exception {
        HttpResponse response = Request.Post("http://localhost:" + port +"/command")
                .body(new StringEntity("{\"commandName\" : \"fake command\"}", ContentType.APPLICATION_JSON))
                .execute()
                .returnResponse();
        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
        assertEquals("{\"reason\":\"command not found\"}", EntityUtils.toString(response.getEntity()));
    }

}
