package com.widget.quality.control.service;

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
class ProcessDataSetService {

    private final ClassifierService classifierService;

    public ProcessDataSetService(ClassifierService classifierService) {
        this.classifierService = classifierService;
    }

    JSONObject process(URL url) throws IOException {
        log.info("Processing log data from " + url);
        HashMap<String, String> classification = new HashMap<>();

        try (
                Scanner scanner = new Scanner(url.openStream())
        ) {
            List<String> splittedLine;
            List<String> references = new ArrayList<>();
            int numSensors = 1;
            String newWidgetType;
            HashMap<String, Float> referenceValues = new HashMap<>();
            String widgetType = null;
            String widgetName = "";
            ArrayList<Float> readings = new ArrayList<>();

            Predicate<String> dateTime = Pattern.compile(
                    "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))T(0[0-9]|1[0-9]|2[0-3]|[0-9]):[0-5][0-9]$"
            ).asPredicate();

            while (scanner.hasNextLine()) {
                splittedLine = Arrays.asList(scanner.nextLine().split(" "));
                if (splittedLine.stream().anyMatch("reference"::equalsIgnoreCase)) {
                    references = splittedLine;
                } else if (splittedLine.stream().noneMatch(dateTime)) {
                    newWidgetType = splittedLine.get(0).toLowerCase();

                    if (!referenceValues.containsKey(splittedLine.get(0).toLowerCase())) {
                        referenceValues.put(
                                newWidgetType,
                                Float.parseFloat(references.get(numSensors++))
                        );
                    }

                    if (!widgetName.isEmpty()) {
                        classification.put(widgetName, classifierService.classify(widgetType, readings,
                                referenceValues.get(widgetType)));
                    }
                    widgetType = newWidgetType;
                    widgetName = splittedLine.get(1);
                    readings = new ArrayList<>();
                    log.info("Found " + widgetType + ": " + widgetName);
                } else if (splittedLine.stream().anyMatch(dateTime)) {
                    readings.add(Float.parseFloat(splittedLine.get(1)));
                }
                // note that Scanner suppresses exceptions
                if (scanner.ioException() != null) {
                    throw scanner.ioException();
                }
            }
            if (!widgetName.isEmpty()) {
                classification.put(widgetName, classifierService.classify(widgetType, readings,
                        referenceValues.get(widgetType)));
            }

        }

        log.info(classification.toString());
        return (new JSONObject(classification));
    }
}
