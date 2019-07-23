package com.widget.quality.control.service.classification;

import java.util.ArrayList;

public interface Classifier {
    String execute(ArrayList<Float> readings, float referenceValues) throws IllegalArgumentException;
}
