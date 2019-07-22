package com.widget.quality.control.model;

import java.util.ArrayList;

public interface Classifier {
    String execute(ArrayList<Float> readings, float referenceValues);
}
