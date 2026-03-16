package com.weather.ui;

import com.weather.controller.ApplicationController;
import com.weather.model.Weather;
import com.weather.utils.DateTimeUtils;
import com.weather.utils.TemperatureConverter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Builder for the Weather Dashboard Page
 */
public class DashboardPageBuilder {

        public static Parent buildPage(ApplicationController controller) {
                BorderPane root = new BorderPane();
                root.setStyle("-fx-background-color: #f8f9fa;");

                Weather weather = controller.getCurrentWeather();

                if (weather == null) {
                        VBox emptyBox = new VBox();
                        emptyBox.setAlignment(Pos.CENTER);
                        Label emptyLabel = new Label("Aucune donnée météo disponible. Veuillez rechercher une ville.");
                        emptyLabel.setFont(Font.font("Segoe UI", 16));
                        emptyBox.getChildren().add(emptyLabel);
                        root.setCenter(emptyBox);
                        return root;
                }

                // Main content
                VBox mainContent = new VBox(20);
                mainContent.setPadding(new Insets(20));
                mainContent.setStyle("-fx-background-color: #f8f9fa;");

                // Header with city name
                HBox headerBox = new HBox();
                headerBox.setAlignment(Pos.CENTER_LEFT);
                HBox.setHgrow(headerBox, Priority.ALWAYS);
                headerBox.setPadding(new Insets(10));

                Label cityLabel = new Label(weather.getCityName() +
                                (weather.getCountryCode() != null ? ", " + weather.getCountryCode() : ""));
                cityLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));

                Label lastUpdateLabel = new Label(
                                "Dernière mise à jour : " + DateTimeUtils.getTimeAgo(weather.getLastUpdated()));
                lastUpdateLabel.setFont(Font.font("Segoe UI", 12));
                lastUpdateLabel.setTextFill(Color.GRAY);

                headerBox.getChildren().addAll(cityLabel, lastUpdateLabel);

                // Weather cards grid
                GridPane cardsGrid = new GridPane();
                cardsGrid.setHgap(15);
                cardsGrid.setVgap(15);
                cardsGrid.setPadding(new Insets(15));

                boolean isCelsius = controller.getSettings().getTemperatureUnit().toString().equals("CELSIUS");

                // Main weather card
                VBox mainWeatherCard = createWeatherCard(
                                "Température",
                                TemperatureConverter.formatTemperatureWholeNumber(
                                                controller.getTemperatureInCurrentUnit(weather.getTemperature()),
                                                isCelsius),
                                weather.getDescription(),
                                "#667eea");
                GridPane.setColumnSpan(mainWeatherCard, 2);

                // Feels like card
                VBox feelsLikeCard = createWeatherCard(
                                "Ressenti",
                                TemperatureConverter.formatTemperatureWholeNumber(
                                                controller.getTemperatureInCurrentUnit(weather.getFeelsLike()),
                                                isCelsius),
                                "Température ressentie",
                                "#764ba2");

                // Humidity card
                VBox humidityCard = createWeatherCard(
                                "Humidité",
                                weather.getHumidity() + "%",
                                "Humidité de l'air",
                                "#f093fb");

                // Wind speed card
                VBox windCard = createWeatherCard(
                                "Vitesse du vent",
                                String.format("%.1f m/s", weather.getWindSpeed()),
                                "Vélocité du vent",
                                "#4facfe");

                // Pressure card
                VBox pressureCard = createWeatherCard(
                                "Pression",
                                weather.getPressure() + " hPa",
                                "Pression atmosphérique",
                                "#43e97b");

                // Cloudiness card
                VBox cloudCard = createWeatherCard(
                                "Nuagosité",
                                weather.getCloudiness() + "%",
                                "Couverture nuageuse",
                                "#90a4ae");

                // Visibility card
                VBox visibilityCard = createWeatherCard(
                                "Visibilité",
                                String.format("%.1f km", weather.getVisibility() / 1000),
                                "Distance de visibilité",
                                "#fa709a");

                // Min/Max Temp card
                VBox tempRangeCard = createWeatherCard(
                                "Plage de temp.",
                                TemperatureConverter.formatTemperatureWholeNumber(
                                                controller.getTemperatureInCurrentUnit(weather.getTempMin()),
                                                isCelsius) + " - " +
                                                TemperatureConverter.formatTemperatureWholeNumber(
                                                                controller.getTemperatureInCurrentUnit(
                                                                                weather.getTempMax()),
                                                                isCelsius),
                                "Min - Max aujourd'hui",
                                "#fa709a");

                cardsGrid.add(mainWeatherCard, 0, 0);
                cardsGrid.add(feelsLikeCard, 2, 0);
                cardsGrid.add(humidityCard, 0, 1);
                cardsGrid.add(windCard, 1, 1);
                cardsGrid.add(pressureCard, 2, 1);
                cardsGrid.add(cloudCard, 0, 2);
                cardsGrid.add(visibilityCard, 1, 2);
                cardsGrid.add(tempRangeCard, 2, 2);

                // Add to favorite button bottom
                HBox bottomBox = new HBox();
                bottomBox.setAlignment(Pos.CENTER);
                bottomBox.setPadding(new Insets(20));

                Button addFavoriteBtn = new Button("⭐ Ajouter aux favoris");
                addFavoriteBtn.setStyle(
                                "-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #667eea; -fx-text-fill: white; -fx-background-radius: 5;");
                addFavoriteBtn.setPrefHeight(40);
                addFavoriteBtn.setCursor(javafx.scene.Cursor.HAND);

                addFavoriteBtn.setOnAction(e -> {
                        controller.addToFavorites(weather.getCityName(), weather.getCountryCode(), 0, 0);
                        addFavoriteBtn.setText("✓ Ajouté aux favoris");
                        addFavoriteBtn.setDisable(true);
                });

                bottomBox.getChildren().add(addFavoriteBtn);

                mainContent.getChildren().addAll(headerBox, cardsGrid, bottomBox);

                // Scroll for overflow
                ScrollPane scrollPane = new ScrollPane(mainContent);
                scrollPane.setFitToWidth(true);
                scrollPane.setStyle("-fx-background-color: #f8f9fa;");

                root.setCenter(scrollPane);
                return root;
        }

        /**
         * Créer une fiche d'informations météo
         */
        private static VBox createWeatherCard(String title, String value, String description, String color) {
                VBox card = new VBox(8);
                card.setPadding(new Insets(20));
                card.setStyle(
                                "-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
                card.setPrefHeight(150);

                Label titleLabel = new Label(title);
                titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
                titleLabel.setTextFill(Color.web(color));

                Label valueLabel = new Label(value);
                valueLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
                valueLabel.setTextFill(Color.web("#333"));

                Label descLabel = new Label(description);
                descLabel.setFont(Font.font("Segoe UI", 12));
                descLabel.setTextFill(Color.GRAY);

                card.getChildren().addAll(titleLabel, valueLabel, descLabel);
                return card;
        }
}
