package com.widget.quality.control.service;

import com.widget.quality.control.model.Classifier;
import com.widget.quality.control.model.WidgetType;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

@Service
@Log4j2
public class ClassifyMonoxideSensor implements Classifier {

    @Override
    public String execute(ArrayList<Float> readings, HashMap<WidgetType, Float> referenceValues) {
        String sensorClassification = "keep";
        float reference = referenceValues.get(WidgetType.MONOXIDE);
        Range<Float> range = Range.between(reference - 3f, reference + 3f);
        if (readings.stream().anyMatch(((Predicate<Float>) range::contains).negate())) {
            sensorClassification = "discard";
        }
        log.debug("Sensor classification: " + sensorClassification);
        return sensorClassification;
    }
}
