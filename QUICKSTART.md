# Quick Start Guide

Get your Dynamic Wallpaper plugin up and running in 5 minutes!

## Prerequisites

- Android TV device with Projectivy Launcher installed
- Computer with ADB installed (for installation)
- TMDB API key (free)
- YouTube API key (free)

## Step 1: Get API Keys (5 minutes)

### TMDB API Key (for Netflix)
1. Visit [themoviedb.org/signup](https://www.themoviedb.org/signup)
2. Create account → Settings → API → Request Key
3. Choose "Developer" → Fill form → Copy API key

### YouTube API Key
1. Visit [console.cloud.google.com](https://console.cloud.google.com)
2. Create Project → Enable "YouTube Data API v3"
3. Credentials → Create API Key → Copy key

## Step 2: Build & Install

### Option A: Pre-built APK (Easiest)
```bash
# Download APK (from releases)
# Then install:
adb connect YOUR_TV_IP:5555
adb install DynamicWallpaper-v1.0.apk
```

### Option B: Build from Source
```bash
# Clone repository
git clone https://github.com/yourusername/dynamic-wallpaper-plugin.git
cd dynamic-wallpaper-plugin

# Build
./gradlew assembleDebug

# Install
adb connect YOUR_TV_IP:5555
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Step 3: Configure (2 minutes)

1. **Launch app** on Android TV
2. **Select "API Keys"**
3. **Enter TMDB key** → Save
4. **Enter YouTube key** → Save
5. **Back** to main menu

## Step 4: Test!

1. Open Projectivy Launcher
2. **Hover over Netflix** → Should show movie/TV backdrops
3. **Hover over YouTube** → Should show video thumbnails
4. **Hover over SmartTube** → Should show video thumbnails

## Troubleshooting

### Wallpaper not changing?
- Make sure Projectivy Launcher is your default launcher
- Check that API keys are entered correctly
- Restart the app or Android TV

### API errors?
- Verify API keys are correct (no extra spaces)
- Check internet connection
- TMDB: Make sure you requested API v3 key
- YouTube: Make sure API is enabled in Google Cloud

### Still not working?
```bash
# Check logs
adb logcat | grep DynamicWallpaper
```

## Customization

### Add custom wallpaper for any app:
1. Settings → App Configuration
2. Select app
3. Choose "Custom Image" or "Custom Video"
4. Browse to your file

### Supported apps (auto-detected):
- Netflix
- YouTube / YouTube Kids
- SmartTube / SmartTube Beta
- Stremio
- Any app (with custom wallpaper)

## Tips

- **Clear cache**: Settings → Refresh Content
- **Performance**: Plugin caches images to minimize API calls
- **Privacy**: All data stays on your device

## Next Steps

- Read full [README.md](README.md) for advanced features
- Check [INSTALLATION.md](INSTALLATION.md) for detailed build instructions
- Report issues on GitHub

## Getting Help

- Check logs: `adb logcat | grep DynamicWallpaper`
- Open GitHub issue with logs and Android TV model
- Include steps to reproduce the problem

---

**That's it! Enjoy your dynamic wallpapers! 🎉**
