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
public class ClassifyHumiditySensorTest {

    @Autowired
    ClassifyHumiditySensor classifyHumiditySensor;

    @Test
    public void testExecuteNormalRun() {
        String expected = "keep";

        ArrayList<Float> readings = new ArrayList<>(Arrays.asList(45.2f, 45.3f, 45.1f));
        float reference = 45.0f;

        String actual = classifyHumiditySensor.execute(readings, reference);
        assertEquals(expected, actual);
    }
}
