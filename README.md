# Smart Weather System - Professional JavaFX Desktop Application

## 📱 Overview

**Smart Weather System** is a professional, feature-rich JavaFX desktop application for displaying real-time weather information. Built with Java 21 and modern UI/UX principles, it provides a complete weather dashboard with multiple pages, real-time data updates, and advanced features.

### Key Features
- 🌍 **Real-time weather data** from OpenWeatherMap API
- 📊 **Interactive dashboard** with weather cards
- 📈 **5-day forecast** with detailed predictions
- 📉 **Weather charts** for temperature, humidity, and pressure
- ⭐ **Favorite cities** management
- 🔬 **Advanced details** (UV Index, visibility, sunrise/sunset, wind direction)
- ⚙️ **Customizable settings** (temperature unit, refresh interval, alerts)
- 🌡️ **Temperature converter** (Celsius ↔ Fahrenheit)
- 📱 **Professional modern UI** with sidebar navigation
- 💾 **Data persistence** with local storage

---

## 🏗️ Project Architecture

### MVC Architecture Pattern
The application follows a clean **Model-View-Controller** (MVC) architecture:

```
├── Model Layer (com.weather.model)
│   ├── Weather.java          - Current weather data
│   ├── ForecastDay.java      - 5-day forecast data
│   ├── FavoriteCity.java     - Favorite cities storage
│   └── AppSettings.java      - Application settings
│
├── Service Layer (com.weather.service)
│   └── OpenWeatherService.java  - API integration & parsing
│
├── Controller Layer (com.weather.controller)
│   └── ApplicationController.java - Business logic & state management
│
├── UI/View Layer (com.weather.ui)
│   ├── PageType.java              - Page enumeration
│   ├── WelcomePageBuilder.java    - Welcome/Search page
│   ├── DashboardPageBuilder.java  - Dashboard page
│   ├── ForecastPageBuilder.java   - 5-day forecast page
│   ├── ChartsPageBuilder.java     - Charts page
│   ├── FavoritesPageBuilder.java  - Favorites management
│   ├── AdvancedDetailsPageBuilder.java - Advanced details
│   └── SettingsPageBuilder.java   - Settings page
│
├── Utilities (com.weather.utils)
│   ├── TemperatureConverter.java  - Temperature conversion
│   ├── DateTimeUtils.java         - Date/time operations
│   └── StorageManager.java        - Data persistence
│
└── Main Application (com.weather.app)
    └── WeatherApp.java  - JavaFX Application entry point
```

---

## 🗂️ Project Structure

```
metadata/
├── pom.xml                          - Maven configuration
├── README.md                        - Project documentation
└── SETUP_INSTRUCTIONS.md           - Setup guide

src/main/java/com/weather/
├── app/
│   └── WeatherApp.java             - Main application class
├── controller/
│   └── ApplicationController.java   - Application controller
├── service/
│   └── OpenWeatherService.java     - API service
├── model/
│   ├── Weather.java
│   ├── ForecastDay.java
│   ├── FavoriteCity.java
│   └── AppSettings.java
├── ui/
│   ├── PageType.java
│   ├── WelcomePageBuilder.java
│   ├── DashboardPageBuilder.java
│   ├── ForecastPageBuilder.java
│   ├── ChartsPageBuilder.java
│   ├── FavoritesPageBuilder.java
│   ├── AdvancedDetailsPageBuilder.java
│   └── SettingsPageBuilder.java
└── utils/
    ├── TemperatureConverter.java
    ├── DateTimeUtils.java
    └── StorageManager.java

src/main/resources/
└── styles/
    └── style.css                   - CSS styling
```

---

## 🚀 Quick Start Guide

### Prerequisites
- **Java 21** or higher
- **Maven 3.8+** (for building)
- **OpenWeatherMap API Key** (free account at https://openweathermap.org/api)

### Installation Steps

#### 1. Clone or Download Project
```bash
cd c:\IHM\météo
```

#### 2. Get OpenWeatherMap API Key
1. Visit https://openweathermap.org/api
2. Sign up for a free account
3. Generate an API key from your account dashboard
4. Copy the API key

#### 3. Configure API Key
Open `src/main/java/com/weather/service/OpenWeatherService.java` and replace:
```java
private static final String API_KEY = "YOUR_API_KEY_HERE";
```

With your actual API key:
```java
private static final String API_KEY = "your_actual_api_key_here";
```

#### 4. Build the Project
```bash
mvn clean package
```

#### 5. Run the Application

**Using Maven:**
```bash
mvn javafx:run
```

**Or run the JAR directly:**
```bash
java -jar target/weather-app.jar
```

---

## 📖 User Guide

### Main Pages

#### 1. Welcome / Search Page
- **Description**: Initial page where users search for weather information
- **Features**:
  - City name input field
  - Search button with input validation
  - Information cards about features
  - Smooth transition animation
- **Usage**: Enter a city name and click "Search Weather"

#### 2. Weather Dashboard
- **Description**: Main weather display page
- **Features**:
  - Current temperature with "feels like" indicator
  - Humidity percentage
  - Wind speed and direction
  - Atmospheric pressure
  - Cloud coverage
  - Visibility distance
  - Min/Max temperature range
  - Add to favorites button
- **Displays**: Beautiful card-based layout with color-coded information

#### 3. 5-Day Forecast
- **Description**: Extended weather predictions
- **Features**:
  - 5 days of weather forecasts
  - Temperature, humidity, wind speed for each day
  - Weather description and icon
  - Rain probability (if available)
- **Layout**: Horizontal scrollable cards

#### 4. Weather Charts
- **Description**: Visual data representation
- **Features**:
  - Temperature variation chart
  - Humidity levels chart
  - Atmospheric pressure chart
  - Tabbed interface for easy switching
- **Technology**: JavaFX LineChart

#### 5. Favorite Cities
- **Description**: Manage favorite locations
- **Features**:
  - View all saved favorite cities
  - Load weather for any favorite
  - Remove favorites
  - List-based display with action buttons
- **Auto-save**: Favorites are automatically persisted to disk

#### 6. Advanced Details
- **Description**: Detailed meteorological information
- **Features**:
  - UV Index with category (Low, Moderate, High, Very High, Extreme)
  - Visibility distance
  - Sunrise and sunset times
  - Wind direction (N, NE, E, SE, S, SW, W, NW)
  - Last hour precipitation (rain volume)
  - Last hour snow volume
  - Cloud coverage percentage
- **Layout**: Card-based grid display

#### 7. Settings
- **Description**: Application configuration
- **Features**:
  - **Temperature Unit**: Toggle between Celsius and Fahrenheit
  - **Auto-Refresh Interval**: Set update frequency (5-120 minutes)
  - **Notifications**: Enable/disable notifications
  - **Weather Alerts**: Configure temperature alerts
  - **Auto-Geolocation**: Enable location detection
  - **Temperature Converter**: Interactive Celsius ↔ Fahrenheit converter
- **Persistence**: All settings are saved automatically

### Navigation
- Use the **left sidebar** to navigate between pages
- Each page button is labeled with an icon and page name
- Current interactions are reflected in the footer status bar

### Footer Information
- **Status Indicator**: Green dot shows API connection status
- **Status Message**: Last operation status (e.g., "Weather updated successfully")
- **Last Update Time**: Timestamp of the last API call
- **Version**: Application version and API provider

---

## 🔧 Advanced Features

### Temperature Converter
Located in Settings page - real-time conversion:
- Enter temperature in Celsius to see Fahrenheit
- Or enter Fahrenheit to see Celsius
- Automatic bidirectional conversion

### Weather Alerts System
Configure alerts based on temperature thresholds:
- Enable/disable alerts
- Set temperature threshold
- Receives notifications when threshold is exceeded

### Auto-Geolocation
- When enabled, automatically detects user location
- Loads weather for current location on startup
- Requires location permissions

### Data Persistence
All user data is saved automatically:
- **Favorite cities**: Stored in `~/.weather_app/favorites.json`
- **Settings**: Stored in `~/.weather_app/settings.json`
- Data persists between application restarts

---

## 💻 Development Guide

### Key Classes & Their Responsibilities

#### `com.weather.app.WeatherApp`
- JavaFX Application entry point
- Manages main window layout
- Handles sidebar navigation
- Controls page switching
- Updates footer status

#### `com.weather.controller.ApplicationController`
- Central controller for business logic
- Manages state (current weather, favorites, settings)
- Coordinates between service layer and UI
- Implements observer pattern for UI updates

#### `com.weather.service.OpenWeatherService`
- Handles all API communication
- Parses JSON responses
- Provides error handling
- Manages connection status

#### `com.weather.model.*`
- Data classes representing domain objects
- Serializable for persistence
- Clean separation of concerns

#### Page Builders
- Each page has dedicated builder class
- Follows builder pattern for complex UI construction
- Contains all UI logic for specific page
- Independently testable

### Adding a New Feature

1. **Create model class** in `com.weather.model`
2. **Add service methods** in `OpenWeatherService` if API calls needed
3. **Update controller** with business logic
4. **Create UI builder** in `com.weather.ui`
5. **Add navigation button** to WeatherApp sidebar
6. **Register page type** in PageType enum

### Error Handling

Application includes comprehensive error handling:
- **API errors**: Connection failures, invalid responses
- **Input validation**: Empty fields, invalid data
- **Null checks**: Safe null handling throughout
- **Exception propagation**: Custom WeatherServiceException
- **Alert dialogs**: User-friendly error messages

### Testing Considerations

Example test scenario:
```java
// Test temperature conversion
double celsius = 20.0;
double fahrenheit = TemperatureConverter.celsiusToFahrenheit(celsius);
assert fahrenheit == 68.0;
```

---

## 🎨 UI/UX Design Details

### Color Scheme
- **Primary**: #667eea (Purple-Blue)
- **Secondary**: #764ba2 (Purple)
- **Background**: #f8f9fa (Light Gray)
- **Sidebar**: #2c3e50 (Dark Blue-Gray)
- **Success**: #27ae60 (Green)
- **Accent**: #fa709a (Pink)

### Layout Components
- **BorderPane**: Main application layout
- **VBox/HBox**: Flexible spacing and alignment
- **GridPane**: Card-based layouts
- **StackPane**: Page container for switching
- **ScrollPane**: Overflow handling

### Modern UI Features
- Smooth hover effects on buttons
- Drop shadows on cards
- Rounded corners (border-radius)
- Icon usage (emojis for quick identification)
- Responsive layout with resizable windows
- Color-coded information cards

---

## 🔐 Security Considerations

1. **API Key**: Store API key securely (environment variables recommended for production)
2. **Data Storage**: User data stored locally in home directory with standard file permissions
3. **Input Validation**: All user inputs validated before processing
4. **Exception Handling**: Sensitive information not exposed in error messages
5. **Network Security**: Uses HTTPS for API calls

---

## 📊 Performance Tips

1. **Lazy Loading**: Pages are built on demand
2. **Thread Usage**: API calls run on background threads
3. **UI Updates**: Platform.runLater() used for thread-safe UI updates
4. **Memory Management**: Observer pattern prevents memory leaks
5. **Caching**: Recent weather data cached in memory

---

## 🐛 Troubleshooting

### Application won't start
- Ensure Java 21 is installed: `java -version`
- Check Maven installation: `mvn -version`
- Verify all files are in correct locations

### "Invalid API key" error
- Verify API key is correctly set in OpenWeatherService.java
- Check API key validity on OpenWeatherMap website
- Ensure API key has not expired

### No weather data displays
- Verify internet connection
- Check that API key is active
- Use a common city name (e.g., "London", "Paris")
- Check OpenWeatherMap API status

### Application crashes
- Check console output for error messages
- Verify all dependencies installed: `mvn clean install`
- Check Java version compatibility (requires Java 21+)

---

## 📚 Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| JavaFX Controls | 21.0.1 | UI Components |
| JavaFX FXML | 21.0.1 | Layout |
| JavaFX Graphics | 21.0.1 | Rendering |
| Gson | 2.10.1 | JSON Parsing |
| JUnit | 4.13.2 | Testing |

---

## 📝 Code Standards

- **Naming**: camelCase for methods/variables, PascalCase for classes
- **Documentation**: JavaDoc comments for public methods
- **Code Style**: Google Java Style Guide compliance
- **Error Handling**: Try-catch with meaningful messages
- **Comments**: Educational comments for complex logic

---

## 🎓 Educational Value

This project demonstrates:
- **MVC Architecture**: Clean separation of concerns
- **JavaFX Mastery**: Modern UI development
- **REST API Integration**: HTTP client usage
- **JSON Parsing**: Using Gson library
- **Multithreading**: Background tasks with Platform.runLater()
- **Data Persistence**: File-based storage
- **Observer Pattern**: Event-driven architecture
- **Error Handling**: Comprehensive exception management

---

## 📄 License

This is a professional educational Java project created for a university IHM (Human Machine Interface) course.

---

## 🤝 Support & Contributions

For issues, improvements, or questions:
1. Review this documentation
2. Check the code comments
3. Verify API key configuration
4. Test with different cities

---

## 🎉 Conclusion

Smart Weather System is a complete, production-quality JavaFX application that showcases professional development practices. It combines beautiful UI, robust architecture, and practical features to create an educational and functional weather application.

**Happy Weather Forecasting!** 🌤️
