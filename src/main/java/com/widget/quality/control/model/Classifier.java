package com.widget.quality.control.model;

import java.util.ArrayList;
import java.util.HashMap;

public interface Classifier {
    String execute(ArrayList<Float> readings, HashMap<WidgetType, Float> referenceValues);
}
