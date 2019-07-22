package com.widget.quality.control.service.classification;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Log4j2
public class ClassificationService {

    private final ClassificationFactory classificationFactory;

    public ClassificationService(ClassificationFactory classificationFactory) {
        this.classificationFactory = classificationFactory;
    }

    public String classify(String sensorType, ArrayList<Float> readings, Float reference) {
        String classification = "";
        if (sensorType != null && reference != null && !readings.isEmpty()) {
            classification = classificationFactory.getClassifier(sensorType).execute(readings, reference);
        }
        return classification;
    }
}
