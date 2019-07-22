package com.widget.quality.control.service;

import com.widget.quality.control.model.WidgetType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ClassifyThermometerSensorTest {

    @Autowired
    ClassifyThermometerSensor classifyThermometerSensor;

    @Test
    public void testExecuteNormalRun() {
        String expected = "ultra precise";

        ArrayList<Float> readings = new ArrayList<>(Arrays.asList(69.5f, 70.1f, 71.3f, 71.5f, 69.8f));
        HashMap<WidgetType, Float> referenceValues = new HashMap<>();
        referenceValues.put(WidgetType.THERMOMETER, 70.0f);

        String actual = classifyThermometerSensor.execute(readings, referenceValues);
        assertEquals(expected, actual);
    }
}