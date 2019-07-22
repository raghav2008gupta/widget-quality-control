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

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ClassifyHumiditySensorTest {

    @Autowired
    ClassifyHumiditySensor classifyHumiditySensor;

    @Test
    public void testExecuteNormalRun() {
        String expected = "keep";

        ArrayList<Float> readings = new ArrayList<>(Arrays.asList(45.2f, 45.3f, 45.1f));
        HashMap<WidgetType, Float> referenceValues = new HashMap<>();
        referenceValues.put(WidgetType.HUMIDITY, 45.0f);

        String actual = classifyHumiditySensor.execute(readings, referenceValues);
        assertEquals(expected, actual);
    }
}
