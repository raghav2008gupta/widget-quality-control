package com.widget.quality.control.service;

import com.widget.quality.control.model.Classifier;
import com.widget.quality.control.model.WidgetType;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@Log4j2
public class ClassifierService {

    String classify(WidgetType widgetType, ArrayList<Float> readings, HashMap<WidgetType, Float> referenceValues) {
        String classification = "";
        if (widgetType != null && !referenceValues.isEmpty() && !readings.isEmpty()) {
            String className = "Classify" + StringUtils.capitalize(widgetType.name().toLowerCase()) + "Sensor";
            log.debug("Calling " + className + " to process readings for " + widgetType.name());
            try {
                Object classifier =
                        Class.forName(this.getClass().getPackage().getName() + "." + className).newInstance();
                classification = ((Classifier) classifier).execute(readings, referenceValues);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classification;
    }
}
