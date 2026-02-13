# Changelog

All notable changes to the Dynamic Wallpaper Plugin will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Planned Features
- Multiple wallpaper rotation modes (sequential, random, favorites)
- User-customizable refresh intervals
- Wallpaper preview before applying
- Support for more streaming services
- Live wallpaper support for MP4 videos
- Animated transitions between wallpapers
- Wallpaper history and favorites
- Manual wallpaper selection per app

## [1.0.0] - 2024-01-XX

### Added
- Initial release of Dynamic Wallpaper Plugin
- Netflix content integration via TMDB API
  - Automatic discovery of Netflix movies and TV shows
  - High-quality backdrop images
  - Trending and popular content
- YouTube content integration via YouTube Data API v3
  - Trending video thumbnails
  - High-resolution images (maxres when available)
- SmartTube support (uses YouTube API)
- Stremio integration via Cinemeta addon
  - Trending movies
  - Trending TV shows
  - Public catalog support
- Custom wallpaper support
  - User-defined images (JPG, PNG)
  - User-defined videos (MP4) - framework ready
- Android TV optimized UI
  - Leanback interface for TV navigation
  - Settings activity with guided steps
  - API key configuration
  - App-specific configuration
- Intelligent caching system
  - Room database for persistent storage
  - Image caching to reduce API calls
  - Automatic cache cleanup
- Projectivy Launcher integration
  - Broadcast receiver for app focus events
  - Seamless launcher integration
  - Service-based architecture
- Performance optimizations
  - Coroutine-based asynchronous operations
  - Efficient image loading with Glide
  - Background processing with WorkManager
- Comprehensive documentation
  - README with full feature list
  - Installation guide
  - API setup guide
  - Quick start guide
  - In-app help and instructions

### Technical Details
- Minimum SDK: API 21 (Android 5.0)
- Target SDK: API 33 (Android 13)
- Language: Kotlin
- Architecture: Service-based with Repository pattern
- Database: Room with SQLite
- Networking: Retrofit with OkHttp
- Image Loading: Glide
- Video Support: ExoPlayer (framework ready)

### Known Issues
- MP4 video wallpapers require live wallpaper service (not yet implemented)
- Stremio local library access requires server running
- Some high-resolution images may take longer to load on slower connections

### Dependencies
- AndroidX Core, AppCompat, ConstraintLayout
- AndroidX Leanback (TV UI)
- Lifecycle components
- Kotlin Coroutines
- Retrofit 2.9.0
- Glide 4.14.2
- ExoPlayer 2.18.2
- Room 2.5.0
- WorkManager 2.8.0

### API Support
- TMDB API v3
- YouTube Data API v3
- Stremio Cinemeta Addon API

### Acknowledgments
- Inspired by "Movie Art" plugin for Projectivy
- Thanks to the Projectivy Launcher team
- TMDB for their excellent API
- YouTube for their data API
- The Android TV developer community

---

## Version History

### Version Numbering
- **Major.Minor.Patch** (e.g., 1.0.0)
- **Major**: Breaking changes or major feature additions
- **Minor**: New features, backward compatible
- **Patch**: Bug fixes and minor improvements

### Future Versions (Roadmap)

#### v1.1.0 (Planned)
- Live wallpaper support for MP4 videos
- Animated wallpaper transitions
- Wallpaper preview feature
- Multiple rotation modes
- Performance improvements

#### v1.2.0 (Planned)
- More streaming service integrations (Plex, Jellyfin, etc.)
- Advanced caching options
- Wallpaper history
- Favorites system
- Custom refresh intervals

#### v2.0.0 (Future)
- Complete UI redesign
- Cloud sync for settings
- AI-powered wallpaper recommendations
- Plugin marketplace integration
- Advanced customization options

---

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for how to contribute to this project.

## Support

- Report bugs via GitHub Issues
- Feature requests via GitHub Discussions
- Check documentation in the repo

## License

This project is licensed under the MIT License - see [LICENSE](LICENSE) file for details.
