package com.widget.quality.control.service.classification;

import com.widget.quality.control.model.Classifier;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Log4j2
public class ClassificationFactory {

    public Classifier getClassifier(String sensorType) {
        Classifier classifier = null;
        String className = "Classify" + StringUtils.capitalize(sensorType.toLowerCase()) + "Sensor";
        log.debug("Calling " + className + " to process readings for " + sensorType);
        try {
            classifier =
                    (Classifier) Class.forName(this.getClass().getPackage().getName() + "." + className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return classifier;
    }
}
