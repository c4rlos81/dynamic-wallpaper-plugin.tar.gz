# Installation & Build Guide

## Quick Start

### For Users (Install APK)

1. **Download the APK** (once built)
2. **Transfer to Android TV**:
   ```bash
   adb connect YOUR_TV_IP
   adb install DynamicWallpaper-v1.0.apk
   ```
3. **Launch the app** and configure API keys
4. **Enjoy dynamic wallpapers!**

## Building from Source

### Prerequisites

- **Java Development Kit (JDK) 11 or higher**
  - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
  - Verify: `java -version`

- **Android Studio** (recommended) or Android SDK
  - Download from [developer.android.com](https://developer.android.com/studio)

- **Android SDK Tools**
  - API Level 33 (Android 13)
  - Build Tools 33.0.0+
  
- **Git** (to clone repository)
  - Download from [git-scm.com](https://git-scm.com/)

### Step-by-Step Build Instructions

#### Method 1: Using Android Studio (Recommended)

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/dynamic-wallpaper-plugin.git
   cd dynamic-wallpaper-plugin
   ```

2. **Open in Android Studio**:
   - Launch Android Studio
   - Click "Open an Existing Project"
   - Select the `dynamic-wallpaper-plugin` folder
   - Wait for Gradle sync to complete

3. **Configure Android SDK**:
   - Go to File → Project Structure
   - Under SDK Location, verify Android SDK path
   - Ensure SDK 33 is installed

4. **Build the Project**:
   - Click Build → Make Project (Ctrl+F9 / Cmd+F9)
   - Or Build → Build Bundle(s) / APK(s) → Build APK(s)

5. **Install on Device**:
   - Connect Android TV via ADB or use emulator
   - Click Run → Run 'app' (Shift+F10 / Ctrl+R)
   - Or manually install the APK from `app/build/outputs/apk/debug/`

#### Method 2: Using Command Line

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/dynamic-wallpaper-plugin.git
   cd dynamic-wallpaper-plugin
   ```

2. **Set ANDROID_HOME** (if not already set):
   
   **Linux/Mac**:
   ```bash
   export ANDROID_HOME=$HOME/Android/Sdk
   export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
   ```
   
   **Windows** (PowerShell):
   ```powershell
   $env:ANDROID_HOME = "C:\Users\YourName\AppData\Local\Android\Sdk"
   $env:PATH += ";$env:ANDROID_HOME\tools;$env:ANDROID_HOME\platform-tools"
   ```

3. **Build Debug APK**:
   ```bash
   ./gradlew assembleDebug
   ```
   
   Or on Windows:
   ```cmd
   gradlew.bat assembleDebug
   ```

4. **Build Release APK** (unsigned):
   ```bash
   ./gradlew assembleRelease
   ```

5. **Locate the APK**:
   - Debug: `app/build/outputs/apk/debug/app-debug.apk`
   - Release: `app/build/outputs/apk/release/app-release-unsigned.apk`

### Signing the APK (for Production)

1. **Generate Keystore** (first time only):
   ```bash
   keytool -genkey -v -keystore my-release-key.keystore \
           -alias my-key-alias -keyalg RSA -keysize 2048 -validity 10000
   ```

2. **Create/Edit `keystore.properties`**:
   ```properties
   storeFile=/path/to/my-release-key.keystore
   storePassword=yourStorePassword
   keyAlias=my-key-alias
   keyPassword=yourKeyPassword
   ```

3. **Add to `app/build.gradle`**:
   ```gradle
   def keystorePropertiesFile = rootProject.file("keystore.properties")
   def keystoreProperties = new Properties()
   keystoreProperties.load(new FileInputStream(keystorePropertiesFile))
   
   android {
       signingConfigs {
           release {
               keyAlias keystoreProperties['keyAlias']
               keyPassword keystoreProperties['keyPassword']
               storeFile file(keystoreProperties['storeFile'])
               storePassword keystoreProperties['storePassword']
           }
       }
       buildTypes {
           release {
               signingConfig signingConfigs.release
               // ... other settings
           }
       }
   }
   ```

4. **Build Signed APK**:
   ```bash
   ./gradlew assembleRelease
   ```

### Installing on Android TV

#### Via ADB (Recommended)

1. **Enable ADB on Android TV**:
   - Settings → Device Preferences → About
   - Click "Build" 7 times to enable Developer Options
   - Settings → Device Preferences → Developer Options
   - Enable "USB debugging" and "Network debugging"
   - Note the IP address shown

2. **Connect via ADB**:
   ```bash
   adb connect YOUR_TV_IP:5555
   ```
   
   Example:
   ```bash
   adb connect 192.168.1.100:5555
   ```

3. **Install APK**:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```
   
   Or force reinstall:
   ```bash
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```

#### Via USB Drive

1. Copy the APK to a USB drive
2. Plug USB drive into Android TV
3. Use a file manager app to navigate to the APK
4. Click to install

#### Via File Manager Apps

Popular file managers for Android TV:
- X-plore File Manager
- FX File Explorer
- Solid Explorer

### Verifying Installation

1. **Check if installed**:
   ```bash
   adb shell pm list packages | grep dynamicwallpaper
   ```

2. **View logs**:
   ```bash
   adb logcat | grep DynamicWallpaper
   ```

3. **Launch app**:
   ```bash
   adb shell am start -n com.dynamicwallpaper.projectivy/.ui.SettingsActivity
   ```

## Project Structure Setup

After building, your project structure should look like this:

```
dynamic-wallpaper-plugin/
├── app/
│   ├── build/
│   │   └── outputs/
│   │       └── apk/
│   │           ├── debug/
│   │           │   └── app-debug.apk
│   │           └── release/
│   │               └── app-release.apk
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/dynamicwallpaper/projectivy/
│   │       ├── res/
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── gradle/
│   └── wrapper/
├── build.gradle (project-level)
├── settings.gradle
├── gradle.properties
└── README.md
```

## Troubleshooting Build Issues

### Gradle Sync Failed

**Issue**: Gradle sync fails with dependency errors

**Solution**:
1. Check internet connection
2. Clear Gradle cache:
   ```bash
   ./gradlew clean
   rm -rf ~/.gradle/caches/
   ```
3. Re-sync in Android Studio: File → Sync Project with Gradle Files

### SDK Not Found

**Issue**: `Android SDK not found` error

**Solution**:
1. Install Android SDK via Android Studio SDK Manager
2. Set ANDROID_HOME environment variable
3. Verify: `echo $ANDROID_HOME` (should show SDK path)

### ADB Connection Issues

**Issue**: Can't connect to Android TV via ADB

**Solution**:
1. Ensure TV and computer are on same network
2. Restart ADB:
   ```bash
   adb kill-server
   adb start-server
   ```
3. Check firewall settings
4. Try USB debugging if available

### Out of Memory During Build

**Issue**: `java.lang.OutOfMemoryError` during build

**Solution**:
Edit `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=1024m
```

### Kotlin Compilation Errors

**Issue**: Kotlin compilation fails

**Solution**:
1. Update Kotlin version in `build.gradle`:
   ```gradle
   ext.kotlin_version = '1.8.22' // Latest stable
   ```
2. Invalidate caches: File → Invalidate Caches / Restart

## Development Tips

### Testing on Emulator

1. **Create Android TV Emulator**:
   - Tools → AVD Manager → Create Virtual Device
   - Select "TV" category
   - Choose "Android TV (1080p)"
   - Select system image (API 33)
   - Finish

2. **Run on Emulator**:
   ```bash
   ./gradlew installDebug
   ```

### Live Debugging

1. **Enable logging**:
   ```kotlin
   private val TAG = "DynamicWallpaper"
   Log.d(TAG, "Message here")
   ```

2. **View logs in real-time**:
   ```bash
   adb logcat -s DynamicWallpaper:D *:S
   ```

### Hot Reload (Android Studio)

- Use Apply Changes (Ctrl+F10 / Cmd+Shift+R) for quick code updates without full reinstall

## Next Steps

After successful installation:

1. **Configure API Keys** (see README.md)
2. **Test with Netflix/YouTube apps**
3. **Customize app configurations**
4. **Report any issues on GitHub**

## Getting Help

- Check existing GitHub issues
- Create new issue with:
  - Android TV model and OS version
  - Build logs (`./gradlew build --stacktrace`)
  - ADB logcat output
  - Steps to reproduce

## Additional Resources

- [Android Developer Documentation](https://developer.android.com/docs)
- [Projectivy Launcher](https://play.google.com/store/apps/details?id=com.spocky.projengmenu)
- [TMDB API Documentation](https://developers.themoviedb.org/3)
- [YouTube Data API Documentation](https://developers.google.com/youtube/v3)
