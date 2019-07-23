package com.widget.quality.control.service.classification;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ClassifyMonoxideSensorTest {

    @Autowired
    ClassifyMonoxideSensor classifyMonoxideSensor;

    @Test
    public void testExecuteNormalRunKeep() {
        String expected = "keep";

        ArrayList<Float> readings = new ArrayList<>(Arrays.asList(5f, 7f, 9f));
        float reference = 6f;

        String actual = classifyMonoxideSensor.execute(readings, reference);
        assertEquals(expected, actual);
    }

    @Test
    public void testExecuteNormalRunDiscard() {
        String expected = "discard";

        ArrayList<Float> readings = new ArrayList<>(Arrays.asList(2f, 4f, 10f, 8f, 6f));
        float reference = 6f;

        String actual = classifyMonoxideSensor.execute(readings, reference);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteIllegalArgumentException() {
        ArrayList<Float> readings = new ArrayList<>();
        float reference = 6f;

        System.out.println(classifyMonoxideSensor.execute(readings, reference));
    }

    @Test(expected = NullPointerException.class)
    public void testExecuteNullPointerException1() {
        ArrayList<Float> readings = new ArrayList<>(Arrays.asList(2f, 4f, 10f, 8f, 6f));
        float reference = Float.parseFloat(null);

        classifyMonoxideSensor.execute(readings, reference);
    }

    @Test(expected = NullPointerException.class)
    public void testExecuteNullPointerException2() {
        ArrayList<Float> readings = new ArrayList<>(null);
        float reference = 6f;

        classifyMonoxideSensor.execute(readings, reference);
    }
}
