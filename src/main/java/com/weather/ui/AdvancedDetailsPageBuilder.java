package com.weather.ui;

import com.weather.controller.ApplicationController;
import com.weather.model.Weather;
import com.weather.utils.DateTimeUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Builder for the Advanced Details Page
 */
public class AdvancedDetailsPageBuilder {

    public static Parent buildPage(ApplicationController controller) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa;");

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title
        Label titleLabel = new Label("Détails météo avancés");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));

        Weather weather = controller.getCurrentWeather();

        if (weather == null) {
            VBox emptyBox = new VBox();
            emptyBox.setAlignment(Pos.CENTER);
            Label emptyLabel = new Label("Aucune donnée météo disponible.");
            emptyLabel.setFont(Font.font("Segoe UI", 16));
            emptyBox.getChildren().add(emptyLabel);
            root.setCenter(emptyBox);
            return root;
        }

        // Create grid of detail items
        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(20);
        detailsGrid.setVgap(15);
        detailsGrid.setPadding(new Insets(15));

        // UV Index
        detailsGrid.add(createDetailBox("☀️ UV Index",
                String.format("%.1f", weather.getUvIndex()),
                getUVIndexCategory(weather.getUvIndex())), 0, 0);

        // Visibility
        detailsGrid.add(createDetailBox("👁️ Visibility",
                String.format("%.1f km", weather.getVisibility() / 1000),
                "Visibility distance"), 1, 0);

        // Sunrise
        detailsGrid.add(createDetailBox("🌅 Sunrise",
                DateTimeUtils.formatUnixTimestampToTime(weather.getSunrise()),
                "Sunrise time"), 0, 1);

        // Sunset
        detailsGrid.add(createDetailBox("🌆 Sunset",
                DateTimeUtils.formatUnixTimestampToTime(weather.getSunset()),
                "Sunset time"), 1, 1);

        // Cloudiness
        detailsGrid.add(createDetailBox("☁️ Clouds",
                weather.getCloudiness() + "%",
                "Cloud coverage"), 0, 2);

        // Wind degree
        detailsGrid.add(createDetailBox("🧭 Wind Direction",
                String.format("%.0f°", weather.getWindDegree()),
                getWindDirection(weather.getWindDegree())), 1, 2);

        // Rain volume
        detailsGrid.add(createDetailBox("💧 Pluie",
                String.format("%.1f mm", weather.getRainVolume()),
                "Précipitations dernière heure"), 0, 3);

        // Snow volume
        detailsGrid.add(createDetailBox("❄️ Neige",
                String.format("%.1f mm", weather.getSnowVolume()),
                "Neige dernière heure"), 1, 3);

        ScrollPane scrollPane = new ScrollPane(detailsGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f8f9fa;");

        mainContent.getChildren().addAll(titleLabel, scrollPane);
        root.setCenter(mainContent);
        return root;
    }

    /**
     * Créer une boîte de détail
     */
    private static VBox createDetailBox(String title, String value, String description) {
        VBox box = new VBox(8);
        box.setPadding(new Insets(15));
        box.setStyle(
                "-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        box.setPrefWidth(200);
        box.setPrefHeight(120);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        valueLabel.setTextFill(Color.web("#667eea"));

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Segoe UI", 11));
        descLabel.setTextFill(Color.GRAY);

        box.getChildren().addAll(titleLabel, valueLabel, descLabel);
        return box;
    }

    /**
     * Obtenir la catégorie d'indice UV
     */
    private static String getUVIndexCategory(double uvIndex) {
        if (uvIndex < 3)
            return "Faible";
        if (uvIndex < 6)
            return "Modéré";
        if (uvIndex < 8)
            return "Elevé";
        if (uvIndex < 11)
            return "Très élevé";
        return "Extrême";
    }

    /**
     * Obtenir la direction du vent
     */
    private static String getWindDirection(double degree) {
        if (degree >= 337.5 || degree < 22.5)
            return "N (Nord)";
        if (degree >= 22.5 && degree < 67.5)
            return "NE (Nord-Est)";
        if (degree >= 67.5 && degree < 112.5)
            return "E (Est)";
        if (degree >= 112.5 && degree < 157.5)
            return "SE (Sud-Est)";
        if (degree >= 157.5 && degree < 202.5)
            return "S (Sud)";
        if (degree >= 202.5 && degree < 247.5)
            return "SO (Sud-Ouest)";
        if (degree >= 247.5 && degree < 292.5)
            return "O (Ouest)";
        return "NO (Nord-Ouest)";
    }
}
