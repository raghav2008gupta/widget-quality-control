package com.widget.quality.control.service;

import com.widget.quality.control.service.classification.ClassificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.json.JSONObject;

@Service
@Log4j2
public class ProcessDataSetService {

    private final ClassificationService classificationService;

    public ProcessDataSetService(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    public JSONObject processDataSet(URL url) throws IOException {
        log.info("Processing log data from " + url);
        HashMap<String, String> classification = new HashMap<>();

        try (
                Scanner scanner = new Scanner(url.openStream())
        ) {
            List<String> splittedLine;
            List<String> references = new ArrayList<>();
            int numSensors = 1;
            String newSensorType;
            HashMap<String, Float> referenceValues = new HashMap<>();
            String sensorType = null;
            String sensorName = "";
            ArrayList<Float> readings = new ArrayList<>();

            Predicate<String> dateTime = Pattern.compile(
                    "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))T(0[0-9]|1[0-9]|2[0-3]|[0-9]):[0-5][0-9]$"
            ).asPredicate();

            while (scanner.hasNextLine()) {
                splittedLine = Arrays.asList(scanner.nextLine().split(" "));
                if (splittedLine.stream().anyMatch("reference"::equalsIgnoreCase)) {
                    references = splittedLine;
                } else if (splittedLine.stream().noneMatch(dateTime)) {
                    newSensorType = splittedLine.get(0).toLowerCase();

                    if (!referenceValues.containsKey(splittedLine.get(0).toLowerCase())) {
                        referenceValues.put(
                                newSensorType,
                                Float.parseFloat(references.get(numSensors++))
                        );
                    }

                    if (!sensorName.isEmpty()) {
                        classification.put(sensorName, classificationService.classify(sensorType, readings,
                                referenceValues.get(sensorType)));
                    }
                    sensorType = newSensorType;
                    sensorName = splittedLine.get(1);
                    readings = new ArrayList<>();
                    log.info("Found " + sensorType + ": " + sensorName);
                } else if (splittedLine.stream().anyMatch(dateTime)) {
                    readings.add(Float.parseFloat(splittedLine.get(1)));
                }
                // note that Scanner suppresses exceptions
                if (scanner.ioException() != null) {
                    throw scanner.ioException();
                }
            }
            if (!sensorName.isEmpty()) {
                classification.put(sensorName, classificationService.classify(sensorType, readings,
                        referenceValues.get(sensorType)));
            }

        }

        log.info(classification.toString());
        return (new JSONObject(classification));
    }
}
