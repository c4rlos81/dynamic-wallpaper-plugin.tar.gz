# Dynamic Wallpaper - Projectivy Launcher Plugin

A plugin for Projectivy Launcher that automatically changes your Android TV wallpaper based on the app you're hovering over. Shows Netflix content when hovering over Netflix, YouTube videos when hovering over YouTube/SmartTube, and custom images/videos for other apps.

## Features

- **Netflix Content**: Displays movie/TV show backdrops from TMDB when hovering over Netflix
- **YouTube Content**: Shows video thumbnails from trending or subscriptions when hovering over YouTube/SmartTube
- **Stremio Support**: Integration with Stremio (API implementation ready)
- **Custom Wallpapers**: Set custom images or MP4 videos for any app
- **Smart Caching**: Efficient image caching to minimize API calls and loading times
- **TV-Optimized UI**: Designed specifically for Android TV with leanback interface

## Requirements

- Android TV device running Android 5.0+ (API level 21+)
- Projectivy Launcher installed
- TMDB API key (free) - for Netflix content
- YouTube Data API v3 key (free) - for YouTube content

## Getting API Keys

### TMDB API Key (for Netflix content)

1. Go to [https://www.themoviedb.org/](https://www.themoviedb.org/)
2. Create a free account
3. Go to Settings → API
4. Request an API key (choose "Developer" option)
5. Fill out the form (you can use "Personal" for application type)
6. Copy your API key

### YouTube API Key

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project
3. Enable "YouTube Data API v3"
4. Go to Credentials → Create Credentials → API Key
5. Copy your API key
6. (Optional) Restrict the key to YouTube Data API v3 for security

## Installation

### Option 1: Install Pre-built APK

1. Download the latest APK from the releases page
2. Transfer to your Android TV device
3. Install using a file manager or ADB:
   ```bash
   adb install DynamicWallpaper-v1.0.apk
   ```
4. Open the app from your launcher
5. Enter your API keys in settings

### Option 2: Build from Source

1. Clone this repository
2. Open in Android Studio
3. Connect your Android TV device or use an emulator
4. Build and run:
   ```bash
   ./gradlew assembleDebug
   ```
5. Or build APK:
   ```bash
   ./gradlew assembleRelease
   ```
6. The APK will be in `app/build/outputs/apk/`

## Configuration

### Initial Setup

1. **Launch the app** from your Android TV launcher
2. **Configure API Keys**:
   - Select "API Keys" from the main menu
   - Enter your TMDB API key
   - Enter your YouTube API key
   - Keys are saved automatically

3. **Configure Apps** (optional):
   - Select "App Configuration"
   - Choose an app to customize
   - Set content type or custom wallpaper

### Supported Apps (Automatic Detection)

The plugin automatically detects these apps:

- **Netflix** (`com.netflix.ninja`) → Uses TMDB API
- **YouTube** (`com.google.android.youtube.tv`) → Uses YouTube API
- **YouTube Kids** (`com.google.android.youtube.tvkids`) → Uses YouTube API
- **SmartTube** (`com.liskovsoft.smarttubetv`) → Uses YouTube API
- **SmartTube Beta** (`com.liskovsoft.smarttubetv.beta`) → Uses YouTube API
- **Stremio** (`com.stremio.one`, `com.stremio.two`) → Stremio API

### Custom Wallpapers

For apps not in the list above, you can set custom wallpapers:

1. Go to "App Configuration"
2. Select the app
3. Choose "Custom Image" or "Custom Video"
4. Browse and select your image (JPG/PNG) or video (MP4)

## How It Works

1. **App Focus Detection**: The plugin receives events from Projectivy Launcher when you hover over an app icon
2. **Content Fetching**: Based on the app, it fetches appropriate content:
   - Netflix: Discovers trending movies/TV shows from TMDB with Netflix availability
   - YouTube: Fetches trending videos or your subscriptions
   - Custom: Loads your predefined image/video
3. **Wallpaper Update**: The selected image is set as the system wallpaper
4. **Caching**: Images are cached to minimize API calls and improve performance

## Project Structure

```
dynamic-wallpaper-plugin/
├── src/main/java/com/dynamicwallpaper/projectivy/
│   ├── api/              # API interfaces (TMDB, YouTube)
│   ├── data/             # Database, repositories, caching
│   ├── model/            # Data models
│   ├── service/          # Main wallpaper service
│   ├── ui/               # Settings UI (TV-optimized)
│   └── util/             # Utilities and receivers
├── src/main/res/         # Resources (strings, layouts, etc.)
├── AndroidManifest.xml
├── build.gradle
└── README.md
```

## Key Components

### Services

- **DynamicWallpaperService**: Main service that handles app focus events and wallpaper changes

### API Clients

- **TMDBApi**: Interface for The Movie Database API
- **YouTubeApi**: Interface for YouTube Data API v3
- **ApiClient**: Retrofit client manager

### Data Layer

- **ContentRepository**: Manages content fetching from APIs
- **WallpaperDatabase**: Room database for caching and app configurations
- **AppConfigDao**: DAO for app configurations
- **CachedImageDao**: DAO for cached images

### Models

- **AppConfig**: App-specific configuration
- **ContentType**: Enum for different content sources
- **WallpaperContent**: Represents a wallpaper with metadata
- **TMDBModels**: Data models for TMDB responses
- **YouTubeModels**: Data models for YouTube API responses

## Customization

### Adding New Apps

Edit `KnownApps.kt` to add new app mappings:

```kotlin
AppMapping("com.example.app", "App Name", ContentType.NETFLIX)
```

### Adding New Content Sources

1. Create a new API interface in `api/`
2. Add models in `model/`
3. Add content type to `ContentType` enum
4. Implement fetching in `ContentRepository`

## Troubleshooting

### Wallpaper Not Changing

- Check that Projectivy Launcher is installed and set as default
- Verify API keys are entered correctly
- Check Android TV logs: `adb logcat | grep DynamicWallpaper`
- Ensure the app has wallpaper permission

### API Errors

- **TMDB**: Check your API key and daily request limit (1000 requests/day free)
- **YouTube**: Check your API quota (10,000 units/day free)
- Verify internet connection

### Performance Issues

- Clear cache: Settings → Refresh Content
- Reduce cache size in settings
- Check storage space on device

## Permissions

Required permissions:

- `INTERNET`: Fetch content from APIs
- `SET_WALLPAPER`: Change system wallpaper
- `ACCESS_NETWORK_STATE`: Check network connectivity
- `READ_EXTERNAL_STORAGE`: Read custom images/videos
- `WRITE_EXTERNAL_STORAGE`: Cache downloaded images

## API Rate Limits

- **TMDB**: 40 requests per 10 seconds, 1000 requests per day (free tier)
- **YouTube**: 10,000 quota units per day (free tier)

The plugin implements intelligent caching to stay well within these limits.

## Privacy

- API keys are stored locally on your device
- No data is collected or sent to third parties
- All API calls go directly to TMDB/YouTube/Stremio

## Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Credits

- Inspired by the "Movie Art" plugin for Projectivy
- Uses [TMDB API](https://www.themoviedb.org/documentation/api) for movie/TV data
- Uses [YouTube Data API](https://developers.google.com/youtube/v3) for video data
- Built for [Projectivy Launcher](https://play.google.com/store/apps/details?id=com.spocky.projengmenu)

## Support

For issues, questions, or feature requests, please open an issue on GitHub.

## Changelog

### v1.0.0 (Initial Release)
- Netflix content via TMDB API
- YouTube content via YouTube Data API
- SmartTube support
- Stremio API integration ready
- Custom image/video wallpapers
- TV-optimized settings interface
- Efficient image caching
- Room database for persistence
