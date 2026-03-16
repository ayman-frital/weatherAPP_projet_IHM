package com.weather.ui;

import com.weather.controller.ApplicationController;
import com.weather.model.FavoriteCity;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Builder for the Favorite Cities Page
 */
public class FavoritesPageBuilder {

    public static Parent buildPage(ApplicationController controller) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa;");

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title
        Label titleLabel = new Label("⭐ Villes favorites");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));

        // List view for favorites
        ListView<FavoriteCity> favoritesList = new ListView<>(controller.getFavorites());
        favoritesList.setPrefHeight(400);
        favoritesList.setStyle("-fx-control-inner-background: white; -fx-border-color: #ddd;");

        // Custom cell factory for better display
        favoritesList.setCellFactory(param -> new ListCell<FavoriteCity>() {
            @Override
            protected void updateItem(FavoriteCity city, boolean empty) {
                super.updateItem(city, empty);

                if (empty || city == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(15);
                    hBox.setPadding(new Insets(10));
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    // City label
                    Label cityLabel = new Label(city.toString());
                    cityLabel.setFont(Font.font("Segoe UI", 14));
                    HBox.setHgrow(cityLabel, javafx.scene.layout.Priority.ALWAYS);

                    // Load button
                    Button loadBtn = new Button("Charger");
                    loadBtn.setStyle(
                            "-fx-font-size: 12; -fx-padding: 5 15; -fx-background-color: #667eea; -fx-text-fill: white; -fx-background-radius: 3;");
                    loadBtn.setOnAction(e -> {
                        controller.searchWeather(city.getCityName());
                        // Trigger page change
                        controller.changePage(PageType.DASHBOARD);
                    });

                    // Delete button
                    Button deleteBtn = new Button("Supprimer");
                    deleteBtn.setStyle(
                            "-fx-font-size: 12; -fx-padding: 5 15; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-background-radius: 3;");
                    deleteBtn.setOnAction(e -> {
                        controller.removeFromFavorites(city);
                    });

                    hBox.getChildren().addAll(cityLabel, loadBtn, deleteBtn);
                    setGraphic(hBox);
                }
            }
        });

        // Empty state
        if (controller.getFavorites().isEmpty()) {
            VBox emptyBox = new VBox();
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPrefHeight(300);

            Label emptyLabel = new Label("Aucune ville favorite pour le moment.");
            emptyLabel.setFont(Font.font("Segoe UI", 16));
            emptyLabel.setTextFill(Color.GRAY);

            Label helpLabel = new Label("Recherchez une ville et ajoutez-la aux favoris !");
            helpLabel.setFont(Font.font("Segoe UI", 14));
            helpLabel.setTextFill(Color.GRAY);

            emptyBox.getChildren().addAll(emptyLabel, helpLabel);
            mainContent.getChildren().addAll(titleLabel, emptyBox);
        } else {
            mainContent.getChildren().addAll(titleLabel, favoritesList);
        }

        root.setCenter(mainContent);
        return root;
    }
}
