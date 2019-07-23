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
    public void testExecuteNormalRunUltraPrecise() {
        String expected = "ultra precise";

        ArrayList<Float> readings = new ArrayList<>(Arrays.asList(69.5f, 70.1f, 71.3f, 71.5f, 69.8f));
        float reference = 70.0f;

        String actual = classifyThermometerSensor.execute(readings, reference);
        assertEquals(expected, actual);
    }

    @Test
    public void testExecuteNormalRunVeryPrecise() {
        String expected = "very precise";

        ArrayList<Float> readings = new ArrayList<>(Arrays.asList(66.2f, 72.5f, 66.4f, 74.2f, 67.4f, 69.0f, 71.1f,
                73.9f, 67.0f, 73.6f, 68.5f, 73.1f));
        float reference = 70.0f;

        String actual = classifyThermometerSensor.execute(readings, reference);
        assertEquals(expected, actual);
    }

    @Test
    public void testExecuteNormalRunPrecise() {
        String expected = "precise";

        ArrayList<Float> readings = new ArrayList<>(Arrays.asList(72.4f, 76.0f, 79.1f, 75.6f, 71.2f, 71.4f, 69.2f,
                65.2f, 62.8f, 61.4f, 64.0f, 67.5f, 69.4f));
        float reference = 70.0f;

        String actual = classifyThermometerSensor.execute(readings, reference);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteIllegalArgumentException() {
        ArrayList<Float> readings = new ArrayList<>();
        float reference = 70.0f;

        System.out.println(classifyThermometerSensor.execute(readings, reference));
    }

    @Test(expected = NullPointerException.class)
    public void testExecuteNullPointerException1() {
        ArrayList<Float> readings = new ArrayList<>(Arrays.asList(72.4f, 76.0f, 79.1f, 75.6f, 71.2f, 71.4f, 69.2f,
                65.2f, 62.8f, 61.4f, 64.0f, 67.5f, 69.4f));
        float reference = Float.parseFloat(null);

        classifyThermometerSensor.execute(readings, reference);
    }

    @Test(expected = NullPointerException.class)
    public void testExecuteNullPointerException2() {
        ArrayList<Float> readings = new ArrayList<>(null);
        float reference = 70.0f;

        classifyThermometerSensor.execute(readings, reference);
    }
}
