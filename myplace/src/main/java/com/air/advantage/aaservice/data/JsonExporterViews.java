package com.air.advantage.aaservice.data;

public class JsonExporterViews {
    public interface Export {}
    public interface SaveThis {}
    public interface None {}
    // Combined view for fields included in both
    public interface All extends Export, SaveThis {}
}
