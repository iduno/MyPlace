package com.air.advantage.aaservice.data;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class JsonExporterExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        JsonExporter exporter = f.getAnnotation(JsonExporter.class);
        // Only skip if annotation is present and saveThis is false
        return exporter != null && !exporter.saveThis();
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}