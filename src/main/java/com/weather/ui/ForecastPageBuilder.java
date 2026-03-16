package com.weather.ui;

import com.weather.controller.ApplicationController;
import com.weather.model.ForecastDay;
import com.weather.service.OpenWeatherService;
import com.weather.utils.DateTimeUtils;
import com.weather.utils.TemperatureConverter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.List;

/**
 * Builder for the 5-Day Forecast Page
 */
public class ForecastPageBuilder {

    public static Parent buildPage(ApplicationController controller) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa;");

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title
        Label titleLabel = new Label("Prévisions Météo 5 jours");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));

        // Forecast cards
        HBox forecastBox = new HBox(15);
        forecastBox.setPadding(new Insets(15));
        forecastBox.setAlignment(Pos.TOP_LEFT);

        try {
            String units = controller.getSettings().getTemperatureUnit().toString().equals("CELSIUS") ? "metric"
                    : "imperial";

            // Get forecast - for demo, we'll fetch it
            new Thread(() -> {
                try {
                    List<ForecastDay> forecasts = controller.getWeatherService().get5DayForecast(
                            controller.getCurrentWeather().getCityName(),
                            units);

                    javafx.application.Platform.runLater(() -> {
                        boolean isCelsius = controller.getSettings().getTemperatureUnit().toString().equals("CELSIUS");
                        forecastBox.getChildren().clear();

                        for (ForecastDay forecast : forecasts) {
                            forecastBox.getChildren().add(
                                    createForecastCard(forecast, isCelsius, controller));
                        }
                    });
                } catch (OpenWeatherService.WeatherServiceException e) {
                    System.err.println("Forecast error: " + e.getMessage());
                }
            }).start();

        } catch (Exception e) {
            System.err.println("Error loading forecast: " + e.getMessage());
        }

        ScrollPane scrollPane = new ScrollPane(forecastBox);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: #f8f9fa;");

        mainContent.getChildren().addAll(titleLabel, scrollPane);

        root.setCenter(mainContent);
        return root;
    }

    /**
     * Créer une fiche de prévision pour chaque jour
     */
    private static VBox createForecastCard(ForecastDay forecast, boolean isCelsius, ApplicationController controller) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle(
                "-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        card.setPrefWidth(160);
        card.setAlignment(Pos.TOP_CENTER);

        // Date
        Label dateLabel = new Label(DateTimeUtils.formatUnixTimestampToDate(forecast.getForecastTime()));
        dateLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        dateLabel.setWrapText(true);

        // Temperature
        Label tempLabel = new Label(
                TemperatureConverter.formatTemperatureWholeNumber(
                        controller.getTemperatureInCurrentUnit(forecast.getTemperature()),
                        isCelsius));
        tempLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        tempLabel.setTextFill(Color.web("#667eea"));

        // Description
        Label descLabel = new Label(forecast.getDescription());
        descLabel.setFont(Font.font("Segoe UI", 11));
        descLabel.setWrapText(true);
        descLabel.setTextFill(Color.GRAY);

        // Humidity
        Label humidityLabel = new Label("Humidity: " + forecast.getHumidity() + "%");
        humidityLabel.setFont(Font.font("Segoe UI", 10));

        // Wind speed
        Label windLabel = new Label("Wind: " + String.format("%.1f m/s", forecast.getWindSpeed()));
        windLabel.setFont(Font.font("Segoe UI", 10));

        card.getChildren().addAll(dateLabel, tempLabel, descLabel, humidityLabel, windLabel);
        return card;
    }
}
