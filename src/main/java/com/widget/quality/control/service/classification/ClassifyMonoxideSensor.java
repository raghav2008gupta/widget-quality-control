package com.widget.quality.control.service.classification;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Predicate;

@Service
@Log4j2
public class ClassifyMonoxideSensor implements Classifier {

    @Override
    public String execute(ArrayList<Float> readings, float reference) {
        String sensorClassification = "keep";
        Range<Float> range = Range.between(reference - 3f, reference + 3f);
        if (readings.stream().anyMatch(((Predicate<Float>) range::contains).negate())) {
            sensorClassification = "discard";
        }
        log.debug("Sensor classification: " + sensorClassification);
        return sensorClassification;
    }
}
