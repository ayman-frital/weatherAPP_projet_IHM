/**
 * Smart Weather System - JavaFX Application Module
 * 
 * Requires Java 21+ with JavaFX modules
 */
module com.weather.system {
    // Required JavaFX modules
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    // Required external libraries
    requires com.google.gson;

    // Java built-in modules
    requires java.base;
    requires java.net.http;

    // Exports for module access
    exports com.weather.app;
    exports com.weather.controller;
    exports com.weather.model;
    exports com.weather.service;
    exports com.weather.ui;
    exports com.weather.utils;

    // Opens packages for reflection (required by Gson)
    opens com.weather.model to com.google.gson;
    opens com.weather.utils to com.google.gson;
}
