package com.widget.quality.control.service;

import com.widget.quality.control.model.Classifier;
import com.widget.quality.control.model.WidgetType;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;

@Service
@Log4j2
public class ClassifyThermometerSensor implements Classifier {

    @Override
    public String execute(ArrayList<Float> readings, HashMap<WidgetType, Float> referenceValues) {
        String sensorClassification = "precise";
        DoubleSummaryStatistics summaryStatistics = readings.stream().mapToDouble(a -> a).summaryStatistics();
        float mean = (float) summaryStatistics.getAverage();
        StandardDeviation standardDeviation = new StandardDeviation();
        float stdDeviation = (float) standardDeviation.evaluate(readings.stream().mapToDouble(a -> a).toArray());
        if (Math.abs(referenceValues.get(WidgetType.THERMOMETER) - mean) <= 0.5) {
            if (stdDeviation < 3) {
                sensorClassification = "ultra precise";
            } else if (stdDeviation < 5) {
                sensorClassification = "very precise";
            }
        }
        log.debug("Sensor classification: " + sensorClassification);
        return sensorClassification;
    }
}
