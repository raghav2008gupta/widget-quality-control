package com.widget.quality.control.service;

import com.widget.quality.control.model.Classifier;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Service
@Log4j2
public class ClassifierService {

    String classify(String widgetType, ArrayList<Float> readings, Float reference) {
        String classification = "";
        if (widgetType != null && reference != null && !readings.isEmpty()) {
            String className = "Classify" + StringUtils.capitalize(widgetType.toLowerCase()) + "Sensor";
            log.debug("Calling " + className + " to process readings for " + widgetType);
            try {
                Object classifier =
                        Class.forName(this.getClass().getPackage().getName() + "." + className).newInstance();
                classification = ((Classifier) classifier).execute(readings, reference);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classification;
    }
}
