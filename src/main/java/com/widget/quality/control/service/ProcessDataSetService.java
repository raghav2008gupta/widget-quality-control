package com.widget.quality.control.service;

import com.widget.quality.control.model.WidgetType;
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
                Scanner sc = new Scanner(url.openStream())
        ) {
            ArrayList<Float> readings = new ArrayList<>();

            HashMap<WidgetType, Float> referenceValues = new HashMap<>();

            String widgetName = "";
            WidgetType widgetType = null;

            Predicate<String> dateTime = Pattern.compile(
                    "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))T(0[0-9]|1[0-9]|2[0-3]|[0-9]):[0-5][0-9]$"
            ).asPredicate();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                List<String> splittedLine = Arrays.asList(line.split(" "));
                if (splittedLine.stream().anyMatch("reference"::equalsIgnoreCase)) {
                    for (int i = 1; i < splittedLine.size(); i++) {
                        referenceValues.put(WidgetType.values()[i - 1], Float.parseFloat(splittedLine.get(i)));
                    }
                    log.info(referenceValues.keySet());
                } else if (splittedLine.stream().anyMatch(Arrays.toString(WidgetType.values()).toLowerCase()::contains)) {
                    if (!widgetName.isEmpty()) {
                        classification.put(widgetName, classifierService.classify(widgetType, readings,
                                referenceValues));
                    }
                    widgetType = WidgetType.valueOf(splittedLine.get(0).toUpperCase());
                    widgetName = splittedLine.get(1);
                    readings = new ArrayList<>();
                    log.info("Found " + widgetType + ": " + widgetName);
                } else if (splittedLine.stream().anyMatch(dateTime)) {
                    readings.add(Float.parseFloat(splittedLine.get(1)));
                }
                // note that Scanner suppresses exceptions
                if (sc.ioException() != null) {
                    throw sc.ioException();
                }
            }
            if (!widgetName.isEmpty()) {
                classification.put(widgetName, classifierService.classify(widgetType, readings, referenceValues));
            }

        }

        log.info(classification.toString());
        return (new JSONObject(classification));
    }
}
