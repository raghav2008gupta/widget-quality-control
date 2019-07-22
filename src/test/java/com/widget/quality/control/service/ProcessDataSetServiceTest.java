package com.widget.quality.control.service;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.net.URL;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProcessDataSetServiceTest {

    @Autowired
    private ProcessDataSetService processDataSetService;

    @Test
    public void testProcessNormal() {
        String expected = "{\n" +
                "    \"temp-1\": \"precise\",\n" +
                "    \"temp-2\": \"ultra precise\",\n" +
                "    \"hum-1\": \"keep\",\n" +
                "    \"hum-2\": \"discard\",\n" +
                "    \"mon-1\": \"keep\",\n" +
                "    \"mon-2\": \"discard\"\n" +
                "}";
        JSONObject classification = new JSONObject();
        URL res = getClass().getClassLoader().getResource("com.widget.quality.control/log1");
        try {
            classification = processDataSetService.process(res);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(classification.toString(4));
        JSONAssert.assertEquals(expected, classification, true);
    }

}