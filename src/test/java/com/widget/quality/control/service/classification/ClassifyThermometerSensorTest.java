package com.widget.quality.control.service.classification;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;

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
        float reference = 70.0f;

        String actual = classifyThermometerSensor.execute(readings, reference);
        assertEquals(expected, actual);
    }
}
