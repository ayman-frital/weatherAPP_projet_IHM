# Smart Weather System - Setup & Installation Guide

## 🎯 Complete Installation Instructions

### System Requirements

**Minimum:**
- Windows 10, Linux, or macOS
- Java Development Kit (JDK) 21 or higher
- Maven 3.8.0 or higher
- 200 MB disk space
- Internet connection (for API calls)

**Recommended:**
- Java 21 LTS
- Maven 3.9+
- 4GB RAM
- SSD for faster project loading

---

## 📦 Step 1: Install Java 21

### Windows
1. Download Java 21 from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java21)
   - Select "Windows Installer" (64-bit or 32-bit based on your system)
2. Run the installer and follow the installation wizard
3. Accept the license agreement
4. Choose installation directory (default is fine)
5. Click "Install"

**Verify Installation:**
```cmd
java -version
javac -version
```

You should see:
```
java version "21.x.x" ...
javac 21.x.x
```

### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-21-jdk

# Verify
java -version
```

### macOS
```bash
# Using Homebrew (recommended)
brew install openjdk@21

# Verify
java -version
```

---

## 📦 Step 2: Install Maven

### Windows
1. Download Maven from [Apache Maven](https://maven.apache.org/download.cgi)
   - Download `apache-maven-3.9.x-bin.zip`
2. Extract to a folder (e.g., `C:\Maven`)
3. Add to System Environment Variables:
   - Open "Environment Variables" (System Settings)
   - New variable: `MAVEN_HOME` = `C:\Maven\apache-maven-3.9.x`
   - Edit `PATH`, add: `%MAVEN_HOME%\bin`
4. Restart Command Prompt

**Verify Installation:**
```cmd
mvn -version
```

### Linux/macOS
```bash
# Ubuntu/Debian
sudo apt install maven

# macOS
brew install maven

# Verify
mvn -version
```

---

## 🔑 Step 3: Get OpenWeatherMap API Key

1. **Visit OpenWeatherMap**
   - Go to https://openweathermap.org/

2. **Create Account**
   - Click "Sign Up"
   - Enter email and password
   - Verify your email

3. **Generate API Key**
   - Log in to your account
   - Go to "API keys" section
   - You'll see a default API key
   - Copy this key (starts with alphanumeric characters)

4. **Test API Key (Optional)**
   ```
   https://api.openweathermap.org/data/2.5/weather?q=London&appid=YOUR_API_KEY
   
   If it works, you'll see JSON weather data.
   ```

---

## 📂 Step 4: Project Setup

### Clone/Download Project
Navigate to project directory:
```bash
cd c:\IHM\météo
```

### Project Structure Check
Verify you have:
```
c:\IHM\météo\
├── pom.xml
├── README.md
├── src\
│   └── main\
│       ├── java\com\weather\...
│       └── resources\styles\
```

---

## ⚙️ Step 5: Configure API Key

### Edit OpenWeatherService.java
1. Open file: `src/main/java/com/weather/service/OpenWeatherService.java`
2. Find line (around line 17):
   ```java
   private static final String API_KEY = "YOUR_API_KEY_HERE";
   ```
3. Replace with your API key:
   ```java
   private static final String API_KEY = "abc123def456ghi789jkl012";
   ```
4. Save the file

---

## 🏗️ Step 6: Build the Project

### Clean and Build
```bash
mvn clean package
```

**Expected Output:**
```
[INFO] Building jar: target/weather-app.jar
[INFO] BUILD SUCCESS
```

**Common Issues:**
- If `mvn` command not found: Restart terminal/IDE
- If Java version error: Ensure Java 21 is installed
- If dependency download fails: Check internet connection, retry with `mvn clean package -U`

---

## 🚀 Step 7: Run the Application

### Option A: Using Maven (Recommended)
```bash
mvn javafx:run
```

### Option B: Run JAR File
```bash
java -jar target/weather-app.jar
```

### Option C: From IDE
**IntelliJ IDEA:**
1. Open project in IntelliJ
2. Right-click on `WeatherApp.java`
3. Select "Run 'WeatherApp.main()'"

**Eclipse:**
1. Import project as Maven project
2. Right-click project → Run As → Java Application
3. Select `WeatherApp`

**VS Code:**
1. Open terminal in project directory
2. Run: `mvn javafx:run`

---

## ✅ Verification Checklist

After installation, verify everything works:

### 1. Application Starts
- [ ] No errors in console
- [ ] Window appears with sidebar
- [ ] All navigation buttons visible

### 2. API Connection
- [ ] Enter a city name (e.g., "Paris")
- [ ] Click "Search Weather"
- [ ] Dashboard shows weather data
- [ ] Footer shows "Weather updated successfully"

### 3. Features Work
- [ ] Dashboard displays weather cards
- [ ] Can navigate to all pages
- [ ] 5-Day Forecast shows data
- [ ] Charts display correctly
- [ ] Settings page opens
- [ ] Can add/remove favorites
- [ ] Temperature converter works

### 4. Data Persistence
- [ ] Add a favorite city
- [ ] Close application
- [ ] Reopen application
- [ ] Favorite city still exists

---

## 📁 Data Storage Location

Application stores data in:
- **Windows**: `C:\Users\YourUsername\.weather_app\`
- **Linux**: `/home/username/.weather_app/`
- **macOS**: `/Users/username/.weather_app/`

Files created:
- `favorites.json` - Favorite cities list
- `settings.json` - User settings

---

## 🔧 Troubleshooting

### Issue: "Invalid API key" Error

**Solution:**
1. Verify API key in OpenWeatherService.java
2. Test API key in browser:
   ```
   https://api.openweathermap.org/data/2.5/weather?q=London&appid=YOUR_KEY
   ```
3. Check API key is active on OpenWeatherMap website
4. Rebuild project: `mvn clean package`

### Issue: "City not found"

**Solution:**
- Use official city names (e.g., "London", not "London City")
- Try major cities first
- Check internet connection
- Use English city names

### Issue: "No main manifest attribute"

**Solution:**
- Rebuild project: `mvn clean package`
- Use: `mvn javafx:run` instead of JAR

### Issue: JavaFX Module Error

**Solution:**
1. Ensure Java 21 installed
2. Check JDK path in Maven compiler configuration
3. Run: `mvn clean install`

### Issue: Maven Command Not Found

**Solution:**
1. **Windows:**
   - Check MAVEN_HOME environment variable
   - Verify `bin` folder in PATH
   - Restart Command Prompt

2. **Linux/macOS:**
   ```bash
   which mvn
   # If empty, reinstall Maven
   sudo apt install maven  # Ubuntu
   brew install maven      # macOS
   ```

### Issue: Application Won't Start

**Solution:**
1. Check Java version: `java -version`
2. Check Maven version: `mvn -version`
3. Rebuild: `mvn clean package`
4. Look at error messages in console
5. Verify API key is set

---

## 🎓 Running with IDE

### IntelliJ IDEA
1. **Open Project:**
   - File → Open → Select `pom.xml`
   - Choose "Open as Project"

2. **Configure Java:**
   - File → Project Structure → Project
   - Set Project SDK to Java 21

3. **Run Application:**
   - Find `WeatherApp.java` in `com.weather.app`
   - Right-click → Run 'WeatherApp.main()'
   - Or press Shift+F10

### Eclipse
1. **Import Project:**
   - File → Import → Existing Maven Projects
   - Select project folder
   - Click Finish

2. **Configure Java:**
   - Right-click Project → Properties
   - Set Compiler Compliance Level to 21

3. **Run Application:**
   - Right-click `WeatherApp.java`
   - Run As → Java Application

### VS Code
1. **Install Extensions:**
   - Extension Pack for Java
   - Maven for Java
   - Project Manager for Java

2. **Open Project:**
   - File → Open Folder → Select project folder

3. **Run Project:**
   - Open Terminal (Ctrl + `)
   - Type: `mvn javafx:run`

---

## 🌐 Offline Testing

To test without internet:
1. Search for a city once (builds cache)
2. Disconnect internet
3. Navigate between pages (cached data shows)

---

## 📊 Application Startup Flow

```
1. JVM starts
   ↓
2. WeatherApp.main() called
   ↓
3. JavaFX Stage created
   ↓
4. MainLayout with Sidebar created
   ↓
5. Welcome page loaded
   ↓
6. Application window displays
   ↓
7. User searches weather
   ↓
8. API call made (background thread)
   ↓
9. Dashboard page loads
   ↓
10. User can navigate to other pages
```

---

## 🎯 Next Steps

After successful installation:

1. **Explore Features:**
   - Try different cities
   - Test all navigation pages
   - Use advanced features

2. **Customize:**
   - Change colors in `style.css`
   - Modify landing page text
   - Add new weather metrics

3. **Extend:**
   - Add export to PDF/CSV
   - Implement weather maps
   - Add multiple location comparison

4. **Deploy:**
   - Create executable JAR for distribution
   - Package as installer (Windows, Linux)
   - Deploy to production servers

---

## 📚 Additional Resources

- **JavaFX Tutorial**: https://docs.oracle.com/javase/21/
- **OpenWeatherMap Docs**: https://openweathermap.org/api
- **Maven Guide**: https://maven.apache.org/guides/
- **Gson Documentation**: https://github.com/google/gson/

---

## ✨ Tips for Success

1. **First Run**: May take longer due to dependency downloads
2. **Build Caching**: Subsequent builds are faster
3. **API Limits**: Free tier allows 60 calls/minute
4. **Performance**: Run on SSD for best performance
5. **Updates**: Keep Java and Maven updated

---

## 🎉 You're Ready!

If you've completed all steps successfully, your Smart Weather System is ready to use!

**Enjoy exploring weather data!** 🌤️

---

## 📞 Support

If you encounter issues:
1. Review this guide completely
2. Check troubleshooting section
3. Review main README.md
4. Check code comments for details
5. Verify all prerequisites installed

---

**Happy coding!** 👨‍💻👩‍💻
