package com.weather.ui;

import com.weather.controller.ApplicationController;
import com.weather.model.Weather;
import com.weather.utils.DateTimeUtils;
import com.weather.utils.TemperatureConverter;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * Builder for the Welcome/Search Page
 */
public class WelcomePageBuilder {

    public static Parent buildPage(ApplicationController controller, Runnable onSearchClick) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa;");

        // Center content
        VBox centerContent = new VBox(20);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(60));
        centerContent.setStyle("-fx-background-color: linear-gradient(to bottom, #667eea, #764ba2);");

        // Title
        Label titleLabel = new Label("Système Météo Intelligent");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 48));
        titleLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        Label subtitleLabel = new Label("Obtenez des informations météo en temps réel pour n'importe quelle ville");
        subtitleLabel.setFont(Font.font("Segoe UI", 16));
        subtitleLabel.setTextFill(javafx.scene.paint.Color.web("#e0e0e0"));

        // Search Box
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(20));
        searchBox.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-padding: 15;");
        searchBox.setMaxWidth(500);

        TextField cityInput = new TextField();
        cityInput.setPromptText("Entrez le nom de la ville...");
        cityInput.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        cityInput.setPrefHeight(40);
        HBox.setHgrow(cityInput, Priority.ALWAYS);

        // Search button
        Button searchButton = new Button("Rechercher la météo");
        searchButton.setStyle(
                "-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #667eea; -fx-text-fill: white; -fx-background-radius: 5;");
        searchButton.setPrefHeight(40);
        searchButton.setCursor(javafx.scene.Cursor.HAND);

        // Button hover effect
        searchButton.setOnMouseEntered(e -> searchButton.setStyle(
                "-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #5568d3; -fx-text-fill: white; -fx-background-radius: 5;"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle(
                "-fx-font-size: 14; -fx-padding: 10 30; -fx-background-color: #667eea; -fx-text-fill: white; -fx-background-radius: 5;"));

        searchButton.setOnAction(e -> {
            String cityName = cityInput.getText().trim();
            if (cityName.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Erreur de saisie", "Veuillez entrer le nom d'une ville");
            } else {
                controller.searchWeather(cityName);
            }
        });

        // Tooltip
        Tooltip searchTooltip = new Tooltip(
                "Entrez le nom d'une ville et cliquez sur rechercher pour voir la météo actuelle");
        searchTooltip.setStyle("-fx-font-size: 12;");
        Tooltip.install(cityInput, searchTooltip);

        searchBox.getChildren().addAll(cityInput, searchButton);

        // Information cards
        HBox infoCardsBox = new HBox(15);
        infoCardsBox.setAlignment(Pos.CENTER);
        infoCardsBox.setPadding(new Insets(20));
        infoCardsBox.setMaxWidth(600);

        infoCardsBox.getChildren().addAll(
                createInfoCard("🌍", "Couverture mondiale", "Accédez à la météo de\nn'importe quelle ville"),
                createInfoCard("📊", "Données en temps réel", "Obtenez instantanément\nles informations météo"),
                createInfoCard("⭐", "Enregistrer les favoris", "Marquez vos\nvilles fréquentes"));

        centerContent.getChildren().addAll(titleLabel, subtitleLabel, searchBox, infoCardsBox);

        // Add fade-in animation
        FadeTransition fade = new FadeTransition(Duration.millis(1000), centerContent);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        root.setCenter(centerContent);
        return root;
    }

    /**
     * Create an information card
     */
    private static VBox createInfoCard(String icon, String title, String description) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-border-radius: 10; -fx-padding: 15;");
        card.setPrefWidth(150);
        card.setAlignment(Pos.TOP_CENTER);

        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font(32));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        titleLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Segoe UI", 11));
        descLabel.setTextFill(javafx.scene.paint.Color.web("#e0e0e0"));
        descLabel.setWrapText(true);

        card.getChildren().addAll(iconLabel, titleLabel, descLabel);
        return card;
    }

    /**
     * Afficher la boîte de dialogue d'alerte
     */
    private static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
