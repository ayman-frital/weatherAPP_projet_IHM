package com.weather.ui;

import com.weather.controller.ApplicationController;
import com.weather.model.AppSettings;
import com.weather.utils.TemperatureConverter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Builder for the Settings Page
 */
public class SettingsPageBuilder {

    public static Parent buildPage(ApplicationController controller) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa;");

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title
        Label titleLabel = new Label("⚙️ Paramètres");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));

        AppSettings settings = controller.getSettings();

        // Settings sections
        VBox settingsBox = new VBox(15);

        // Temperature unit setting
        VBox tempUnitBox = new VBox(8);
        tempUnitBox.setPadding(new Insets(15));
        tempUnitBox.setStyle(
                "-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label tempLabel = new Label("Unité de température");
        tempLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

        HBox tempUnitHBox = new HBox(15);
        tempUnitHBox.setAlignment(Pos.CENTER_LEFT);

        ToggleGroup tempGroup = new ToggleGroup();
        RadioButton celsiusBtn = new RadioButton("°C (Celsius)");
        celsiusBtn.setToggleGroup(tempGroup);
        celsiusBtn.setSelected(settings.getTemperatureUnit() == AppSettings.TemperatureUnit.CELSIUS);
        celsiusBtn.setStyle("-fx-font-size: 12;");

        RadioButton fahrenheitBtn = new RadioButton("°F (Fahrenheit)");
        fahrenheitBtn.setToggleGroup(tempGroup);
        fahrenheitBtn.setSelected(settings.getTemperatureUnit() == AppSettings.TemperatureUnit.FAHRENHEIT);
        fahrenheitBtn.setStyle("-fx-font-size: 12;");

        celsiusBtn.setOnAction(e -> {
            settings.setTemperatureUnit(AppSettings.TemperatureUnit.CELSIUS);
            controller.saveSettings();
        });

        fahrenheitBtn.setOnAction(e -> {
            settings.setTemperatureUnit(AppSettings.TemperatureUnit.FAHRENHEIT);
            controller.saveSettings();
        });

        tempUnitHBox.getChildren().addAll(celsiusBtn, fahrenheitBtn);
        tempUnitBox.getChildren().addAll(tempLabel, tempUnitHBox);

        // Auto-refresh interval
        VBox refreshBox = new VBox(8);
        refreshBox.setPadding(new Insets(15));
        refreshBox.setStyle(
                "-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label refreshLabel = new Label("Intervalle d'auto-actualisation (minutes)");
        refreshLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

        HBox refreshHBox = new HBox(10);
        refreshHBox.setAlignment(Pos.CENTER_LEFT);

        Spinner<Integer> refreshSpinner = new Spinner<>(5, 120, settings.getAutoRefreshInterval(), 5);
        refreshSpinner.setPrefWidth(100);
        refreshSpinner.setStyle("-fx-font-size: 12;");
        refreshSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            settings.setAutoRefreshInterval(newVal);
            controller.saveSettings();
        });

        Label refreshHelpLabel = new Label("(minimum 5 minutes)");
        refreshHelpLabel.setFont(Font.font("Segoe UI", 11));
        refreshHelpLabel.setTextFill(Color.GRAY);

        refreshHBox.getChildren().addAll(refreshSpinner, refreshHelpLabel);
        refreshBox.getChildren().addAll(refreshLabel, refreshHBox);

        // Notifications
        VBox notificationsBox = new VBox(8);
        notificationsBox.setPadding(new Insets(15));
        notificationsBox.setStyle(
                "-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label notifyLabel = new Label("Notifications");
        notifyLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

        CheckBox notifyCheckBox = new CheckBox("Activer les notifications");
        notifyCheckBox.setSelected(settings.isEnableNotifications());
        notifyCheckBox.setStyle("-fx-font-size: 12;");
        notifyCheckBox.setOnAction(e -> {
            settings.setEnableNotifications(notifyCheckBox.isSelected());
            controller.saveSettings();
        });

        notificationsBox.getChildren().addAll(notifyLabel, notifyCheckBox);

        // Weather alerts
        VBox alertsBox = new VBox(8);
        alertsBox.setPadding(new Insets(15));
        alertsBox.setStyle(
                "-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label alertLabel = new Label("Alertes météo");
        alertLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

        CheckBox alertCheckBox = new CheckBox("Activer les alertes de température");
        alertCheckBox.setSelected(settings.isEnableWeatherAlerts());
        alertCheckBox.setStyle("-fx-font-size: 12;");

        HBox thresholdBox = new HBox(10);
        thresholdBox.setPadding(new Insets(10, 0, 0, 0));
        Label thresholdLabel = new Label("Alerte si la température dépasse :");
        thresholdLabel.setFont(Font.font("Segoe UI", 11));

        Spinner<Integer> thresholdSpinner = new Spinner<>(0, 60, (int) settings.getAlertTemperatureThreshold());
        thresholdSpinner.setPrefWidth(80);
        thresholdSpinner.setStyle("-fx-font-size: 12;");
        thresholdSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            settings.setAlertTemperatureThreshold(newVal);
            controller.saveSettings();
        });

        Label degreeLabel = new Label(settings.getTemperatureUnit().getSymbol());
        degreeLabel.setFont(Font.font("Segoe UI", 12));

        thresholdBox.getChildren().addAll(thresholdLabel, thresholdSpinner, degreeLabel);

        alertCheckBox.setOnAction(e -> {
            settings.setEnableWeatherAlerts(alertCheckBox.isSelected());
            thresholdSpinner.setDisable(!alertCheckBox.isSelected());
            controller.saveSettings();
        });

        thresholdSpinner.setDisable(!alertCheckBox.isSelected());

        alertsBox.getChildren().addAll(alertLabel, alertCheckBox, thresholdBox);

        // Auto-geolocation
        VBox geoBox = new VBox(8);
        geoBox.setPadding(new Insets(15));
        geoBox.setStyle(
                "-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label geoLabel = new Label("Localisation");
        geoLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

        CheckBox geoCheckBox = new CheckBox("Activer la géolocalisation automatique");
        geoCheckBox.setSelected(settings.isEnableAutoGeolocation());
        geoCheckBox.setStyle("-fx-font-size: 12;");
        geoCheckBox.setOnAction(e -> {
            settings.setEnableAutoGeolocation(geoCheckBox.isSelected());
            controller.saveSettings();
        });

        geoBox.getChildren().addAll(geoLabel, geoCheckBox);

        // Temperature converter
        VBox converterBox = createTemperatureConverterBox();

        settingsBox.getChildren().addAll(
                tempUnitBox,
                refreshBox,
                notificationsBox,
                alertsBox,
                geoBox,
                converterBox);

        ScrollPane scrollPane = new ScrollPane(settingsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f8f9fa;");

        mainContent.getChildren().addAll(titleLabel, scrollPane);
        root.setCenter(mainContent);
        return root;
    }

    /**
     * Créer la section convertisseur de température
     */
    private static VBox createTemperatureConverterBox() {
        VBox converterBox = new VBox(8);
        converterBox.setPadding(new Insets(15));
        converterBox.setStyle(
                "-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label converterLabel = new Label("🌡️ Convertisseur de température");
        converterLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        inputBox.setPadding(new Insets(10, 0, 10, 0));

        TextField celsiusInput = new TextField();
        celsiusInput.setPromptText("°C");
        celsiusInput.setPrefWidth(80);
        celsiusInput.setStyle("-fx-font-size: 12;");

        Label converterArrow = new Label("⇄");
        converterArrow.setFont(Font.font(16));

        TextField fahrenheitInput = new TextField();
        fahrenheitInput.setPromptText("°F");
        fahrenheitInput.setPrefWidth(80);
        fahrenheitInput.setStyle("-fx-font-size: 12;");

        celsiusInput.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (!newVal.isEmpty()) {
                    double celsius = Double.parseDouble(newVal);
                    double fahrenheit = TemperatureConverter.celsiusToFahrenheit(celsius);
                    fahrenheitInput.setText(String.format("%.2f", fahrenheit));
                }
            } catch (NumberFormatException e) {
                fahrenheitInput.clear();
            }
        });

        fahrenheitInput.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (!newVal.isEmpty()) {
                    double fahrenheit = Double.parseDouble(newVal);
                    double celsius = TemperatureConverter.fahrenheitToCelsius(fahrenheit);
                    celsiusInput.setText(String.format("%.2f", celsius));
                }
            } catch (NumberFormatException e) {
                celsiusInput.clear();
            }
        });

        inputBox.getChildren().addAll(celsiusInput, converterArrow, fahrenheitInput);
        converterBox.getChildren().addAll(converterLabel, inputBox);

        return converterBox;
    }
}
