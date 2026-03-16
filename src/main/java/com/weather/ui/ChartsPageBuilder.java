package com.weather.ui;

import com.weather.controller.ApplicationController;
import com.weather.model.Weather;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Builder for the Weather Charts Page
 */
public class ChartsPageBuilder {

    public static Parent buildPage(ApplicationController controller) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa;");

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title
        Label titleLabel = new Label("Graphiques et analyses météo");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));

        // Chart tabs
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Weather weather = controller.getCurrentWeather();

        // Temperature chart
        Tab tempTab = new Tab("Température", createTemperatureChart(weather));
        tempTab.setStyle("-fx-font-size: 12;");

        // Humidity chart
        Tab humidityTab = new Tab("Humidité", createHumidityChart(weather));
        humidityTab.setStyle("-fx-font-size: 12;");

        // Pressure chart
        Tab pressureTab = new Tab("Pression", createPressureChart(weather));
        pressureTab.setStyle("-fx-font-size: 12;");

        tabPane.getTabs().addAll(tempTab, humidityTab, pressureTab);

        mainContent.getChildren().addAll(titleLabel, tabPane);

        root.setCenter(mainContent);
        return root;
    }

    /**
     * Créer le graphique de température
     */
    private static LineChart<Number, Number> createTemperatureChart(Weather weather) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Heure");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Température (°C)");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Variation de température");
        lineChart.setPrefHeight(400);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Température");

        // Sample data
        series.getData().add(new XYChart.Data<>(0, weather.getTempMin()));
        series.getData().add(new XYChart.Data<>(6, (weather.getTempMin() + weather.getTemperature()) / 2));
        series.getData().add(new XYChart.Data<>(12, weather.getTemperature()));
        series.getData().add(new XYChart.Data<>(18, (weather.getTemperature() + weather.getTempMax()) / 2));
        series.getData().add(new XYChart.Data<>(24, weather.getTempMax()));

        lineChart.getData().add(series);
        return lineChart;
    }

    /**
     * Créer le graphique d'humidité
     */
    private static LineChart<Number, Number> createHumidityChart(Weather weather) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Heure");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Humidité (%)");
        yAxis.setUpperBound(100);

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Variation d'humidité");
        lineChart.setPrefHeight(400);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Humidité");

        // Sample data
        int humidity = weather.getHumidity();
        series.getData().add(new XYChart.Data<>(0, humidity - 5));
        series.getData().add(new XYChart.Data<>(6, humidity - 3));
        series.getData().add(new XYChart.Data<>(12, humidity));
        series.getData().add(new XYChart.Data<>(18, humidity + 2));
        series.getData().add(new XYChart.Data<>(24, humidity + 5));

        lineChart.getData().add(series);
        return lineChart;
    }

    /**
     * Créer le graphique de pression
     */
    private static LineChart<Number, Number> createPressureChart(Weather weather) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Heure");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Pression (hPa)");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Variation de la pression atmosphérique");
        lineChart.setPrefHeight(400);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Pression");

        // Sample data
        int pressure = weather.getPressure();
        series.getData().add(new XYChart.Data<>(0, pressure - 2));
        series.getData().add(new XYChart.Data<>(6, pressure - 1));
        series.getData().add(new XYChart.Data<>(12, pressure));
        series.getData().add(new XYChart.Data<>(18, pressure + 1));
        series.getData().add(new XYChart.Data<>(24, pressure + 2));

        lineChart.getData().add(series);
        return lineChart;
    }
}
