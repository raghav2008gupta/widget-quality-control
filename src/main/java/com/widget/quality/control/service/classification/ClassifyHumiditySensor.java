package com.widget.quality.control.service.classification;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Predicate;

@Service
@Log4j2
public class ClassifyHumiditySensor implements Classifier {

    @Override
    public String execute(ArrayList<Float> readings, float reference) throws IllegalArgumentException {
        if (readings.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String sensorClassification = "keep";
        Range<Float> range = Range.between(reference - (0.01f * reference), reference + (0.01f * reference));
        if (readings.stream().anyMatch(((Predicate<Float>) range::contains).negate())) {
            sensorClassification = "discard";
        }
        log.debug("Sensor classification: " + sensorClassification);
        return sensorClassification;
    }
}
