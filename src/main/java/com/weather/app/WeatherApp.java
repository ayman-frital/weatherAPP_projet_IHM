package com.weather.app;

import com.weather.controller.ApplicationController;
import com.weather.ui.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Main application window class that manages the UI layout and page navigation.
 * This is the entry point for the JavaFX application.
 */
public class WeatherApp extends Application {

    private ApplicationController controller;
    private BorderPane mainLayout;
    private Map<PageType, Parent> pages;
    private StackPane pageContainer;
    private Label statusLabel;
    private Label lastUpdateLabel;
    private Label versionLabel;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.controller = new ApplicationController();
        this.pages = new HashMap<>();

        // Set up main layout
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #f8f9fa;");

        // Create sidebar
        VBox sidebar = createSidebar();

        // Create page container
        pageContainer = new StackPane();
        pageContainer.setStyle("-fx-background-color: white;");

        // Load initial page (Welcome)
        loadPage(PageType.WELCOME);
        controller.setPageChangeListener(this::handlePageChange);

        // Set up main layout
        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(pageContainer);
        mainLayout.setBottom(createFooter());

        // Create scene
        Scene scene = new Scene(mainLayout, 1400, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        primaryStage.setTitle("Système Météo Intelligent v1.0");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        // Set up status message updates
        controller.setWeatherUpdateListener(this::updateStatus);
        controller.setErrorListener(this::showError);
    }

    /**
     * Create the sidebar navigation menu
     */
    private VBox createSidebar() {
        VBox sidebar = new VBox(0);
        sidebar.setStyle("-fx-background-color: #2c3e50; -fx-padding: 0;");
        sidebar.setPrefWidth(200);

        // App logo/title
        VBox logoBox = new VBox();
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(20));
        logoBox.setStyle("-fx-background-color: #34495e;");

        Label logoLabel = new Label("🌤️");
        logoLabel.setFont(Font.font(40));

        Label appLabel = new Label("Application Météo");
        appLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        appLabel.setTextFill(Color.WHITE);

        logoBox.getChildren().addAll(logoLabel, appLabel);

        // Navigation buttons
        VBox navBox = new VBox(5);
        navBox.setPadding(new Insets(10));
        navBox.setStyle("-fx-background-color: #2c3e50;");

        // Create navigation buttons for each page
        navBox.getChildren().addAll(
                createNavButton("🔍 Recherche", PageType.WELCOME),
                createNavButton("📊 Tableau de bord", PageType.DASHBOARD),
                createNavButton("📈 Prévisions 5 jours", PageType.FORECAST),
                createNavButton("📉 Graphiques", PageType.CHARTS),
                createNavButton("⭐ Favoris", PageType.FAVORITES),
                createNavButton("🔬 Détails avancés", PageType.ADVANCED),
                createNavButton("⚙️ Paramètres", PageType.SETTINGS));

        // Spacer
        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Help section at bottom
        VBox helpBox = new VBox(10);
        helpBox.setPadding(new Insets(15));
        helpBox.setStyle("-fx-background-color: #34495e;");

        Label helpTitle = new Label("Aide et Info");
        helpTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        helpTitle.setTextFill(Color.WHITE);

        Hyperlink apiLink = new Hyperlink("État de l'API");
        apiLink.setStyle("-fx-font-size: 11; -fx-text-fill: #3498db;");
        apiLink.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informations sur l'API");
            alert.setHeaderText("API OpenWeatherMap");
            alert.setContentText("Connecté : " + controller.getWeatherService().isConnected() +
                    "\n\nRemarque : Remplacez API_KEY dans OpenWeatherService.java par votre clé API OpenWeatherMap.");
            alert.showAndWait();
        });

        helpBox.getChildren().addAll(helpTitle, apiLink);

        sidebar.getChildren().addAll(logoBox, new Separator(), navBox, spacer, helpBox);
        return sidebar;
    }

    /**
     * Create a navigation button
     */
    private Button createNavButton(String text, PageType pageType) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-size: 13; -fx-padding: 12;");
        button.setPrefWidth(180);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setCursor(javafx.scene.Cursor.HAND);

        button.setOnMouseEntered(e -> button
                .setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 13; -fx-padding: 12;"));
        button.setOnMouseExited(e -> button
                .setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-size: 13; -fx-padding: 12;"));

        button.setOnAction(e -> {
            controller.changePage(pageType);
        });

        return button;
    }

    /**
     * Create footer with status information
     */
    private HBox createFooter() {
        HBox footer = new HBox(20);
        footer.setPadding(new Insets(10));
        footer.setStyle("-fx-background-color: #ecf0f1; -fx-border-top: 1 solid #bdc3c7;");
        footer.setAlignment(Pos.CENTER_LEFT);

        // Status indicator
        Circle statusDot = new Circle(6);
        statusDot.setFill(Color.web("#27ae60"));

        statusLabel = new Label("Prêt");
        statusLabel.setFont(Font.font("Segoe UI", 11));
        statusLabel.setTextFill(Color.GRAY);

        HBox statusBox = new HBox(8);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        statusBox.getChildren().addAll(statusDot, statusLabel);

        // Last update label
        lastUpdateLabel = new Label("Dernière mise à jour : Jamais");
        lastUpdateLabel.setFont(Font.font("Segoe UI", 11));
        lastUpdateLabel.setTextFill(Color.GRAY);

        // Version label
        versionLabel = new Label("v1.0.0 | API OpenWeatherMap");
        versionLabel.setFont(Font.font("Segoe UI", 10));
        versionLabel.setTextFill(Color.web("#7f8c8d"));

        // Spacer
        VBox spacer = new VBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        footer.getChildren().addAll(statusBox, new Separator(), lastUpdateLabel, spacer, versionLabel);
        return footer;
    }

    /**
     * Load a page dynamically
     */
    private void loadPage(PageType pageType) {
        // Check if page is already loaded
        if (!pages.containsKey(pageType)) {
            Parent page = null;

            switch (pageType) {
                case WELCOME:
                    page = WelcomePageBuilder.buildPage(controller, () -> {
                        loadPage(PageType.DASHBOARD);
                        controller.changePage(PageType.DASHBOARD);
                    });
                    break;
                case DASHBOARD:
                    page = DashboardPageBuilder.buildPage(controller);
                    break;
                case FORECAST:
                    page = ForecastPageBuilder.buildPage(controller);
                    break;
                case CHARTS:
                    page = ChartsPageBuilder.buildPage(controller);
                    break;
                case FAVORITES:
                    page = FavoritesPageBuilder.buildPage(controller);
                    break;
                case ADVANCED:
                    page = AdvancedDetailsPageBuilder.buildPage(controller);
                    break;
                case SETTINGS:
                    page = SettingsPageBuilder.buildPage(controller);
                    break;
            }

            if (page != null) {
                pages.put(pageType, page);
            }
        }

        // Display the page
        if (pages.containsKey(pageType)) {
            pageContainer.getChildren().clear();
            pageContainer.getChildren().add(pages.get(pageType));
        }
    }

    /**
     * Handle page change requests
     */
    private void handlePageChange() {
        loadPage(controller.getCurrentPage());
    }

    /**
     * Update status information
     */
    private void updateStatus() {
        if (controller.getCurrentWeather() != null) {
            // Remove cached dashboard page to force rebuild with new data
            pages.remove(PageType.DASHBOARD);
            // Navigate to the dashboard with updated weather data
            controller.changePage(PageType.DASHBOARD);
            statusLabel.setText("Données météo chargées avec succès");
        } else {
            statusLabel.setText("Erreur lors du chargement des données");
        }
        lastUpdateLabel.setText("Dernière mise à jour : " + java.time.LocalTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    /**
     * Show error message to user
     */
    private void showError(String errorMessage) {
        statusLabel.setText("Erreur : " + errorMessage);
        statusLabel.setTextFill(javafx.scene.paint.Color.web("#e74c3c"));

        // Show error alert without blocking
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de recherche météo");
        alert.setHeaderText("Impossible de récupérer les données météo");
        alert.setContentText(errorMessage);
        alert.show(); // Use show() instead of showAndWait() to avoid blocking
    }
}
