package com.widget.quality.control.controller;

import com.widget.quality.control.service.ProcessDataSetService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

@RestController
public class CheckQuality {

    private final ProcessDataSetService processDataSetService;

    public CheckQuality(ProcessDataSetService processDataSetService) {
        this.processDataSetService = processDataSetService;
    }

    @PostMapping(
            path = "/check-quality",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> checkQuality(@RequestBody String logsLocation) {
        JSONObject classification = new JSONObject();
        try {
            classification = processDataSetService.processDataSet(new URL(logsLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(classification.toString(4), HttpStatus.OK);
    }
}
