# API Setup Guide

This guide will walk you through getting free API keys for TMDB and YouTube.

## TMDB API Key (for Netflix Content)

TMDB (The Movie Database) provides free API access to movie and TV show data.

### Step-by-Step Instructions

1. **Create a TMDB Account**
   - Go to [https://www.themoviedb.org/signup](https://www.themoviedb.org/signup)
   - Fill in your details:
     - Username
     - Email
     - Password
   - Verify your email address

2. **Request an API Key**
   - Log in to your account
   - Click on your profile icon (top right)
   - Select **Settings**
   - In the left sidebar, click **API**
   - Click on **"Request an API Key"**

3. **Fill Out the API Application**
   - Choose **"Developer"** (not Commercial)
   - Accept the Terms of Use
   - Fill in the application form:
     - **Application Name**: "Dynamic Wallpaper Plugin"
     - **Application URL**: Your GitHub repo or "Personal Use"
     - **Application Summary**: "Projectivy Launcher plugin for dynamic wallpapers"
   - Submit the form

4. **Copy Your API Key**
   - Once approved (usually instant), you'll see your API key
   - Copy the **API Key (v3 auth)** value
   - Example format: `1234567890abcdef1234567890abcdef`

5. **Test Your Key** (Optional)
   ```bash
   curl "https://api.themoviedb.org/3/movie/popular?api_key=YOUR_API_KEY"
   ```
   - Should return JSON with popular movies

### TMDB API Limits

- **Free Tier**:
  - 40 requests per 10 seconds
  - 1,000 requests per day
  - Unlimited for non-commercial use

The plugin caches images, so you'll typically use < 50 requests per day.

---

## YouTube Data API v3 Key

YouTube provides free API access through Google Cloud Platform.

### Step-by-Step Instructions

1. **Create a Google Cloud Account**
   - Go to [https://console.cloud.google.com](https://console.cloud.google.com)
   - Sign in with your Google account
   - Accept the Terms of Service if prompted

2. **Create a New Project**
   - Click on the project dropdown (top left, next to "Google Cloud")
   - Click **"NEW PROJECT"**
   - Enter project details:
     - **Project Name**: "Dynamic Wallpaper"
     - **Location**: Leave as default or select your organization
   - Click **"CREATE"**
   - Wait for the project to be created (a few seconds)

3. **Enable YouTube Data API v3**
   - Make sure your new project is selected (check top bar)
   - Click on **"≡" menu** → **"APIs & Services"** → **"Library"**
   - Search for **"YouTube Data API v3"**
   - Click on it
   - Click the **"ENABLE"** button
   - Wait for it to enable (a few seconds)

4. **Create API Credentials**
   - Click on **"≡" menu** → **"APIs & Services"** → **"Credentials"**
   - Click **"+ CREATE CREDENTIALS"** at the top
   - Select **"API key"**
   - Your API key will be generated and displayed
   - Copy the API key
   - Example format: `AIzaSyABC123DEF456GHI789JKL012MNO345PQR`

5. **Restrict Your API Key** (Recommended for Security)
   - Click on your newly created API key to edit it
   - Under **"API restrictions"**:
     - Select **"Restrict key"**
     - Check only **"YouTube Data API v3"**
   - Under **"Application restrictions"** (optional):
     - Select **"Android apps"**
     - Add package name: `com.dynamicwallpaper.projectivy`
     - Add SHA-1 certificate fingerprint (from your keystore)
   - Click **"SAVE"**

6. **Test Your Key** (Optional)
   ```bash
   curl "https://www.googleapis.com/youtube/v3/search?part=snippet&q=test&key=YOUR_API_KEY"
   ```
   - Should return JSON with search results

### YouTube API Quotas

- **Free Tier**:
  - 10,000 quota units per day
  - Search queries cost 100 units each
  - Enough for ~100 searches per day

The plugin caches video thumbnails, using approximately 10-20 units per day.

### Understanding YouTube API Costs

Different API calls have different costs:
- **Search**: 100 units
- **Videos list**: 1 unit
- **Subscriptions list**: 1 unit

The plugin primarily uses search and videos.list, optimized to stay within free limits.

---

## Entering API Keys in the Plugin

1. **Launch the Dynamic Wallpaper app** on your Android TV
2. Navigate to **"API Keys"**
3. **TMDB API Key**:
   - Select the TMDB field
   - Enter your TMDB API key
   - The key is saved automatically
4. **YouTube API Key**:
   - Select the YouTube field
   - Enter your YouTube API key
   - The key is saved automatically
5. Press **Back** to return to main menu

## Security Best Practices

### Protecting Your API Keys

1. **Never share API keys publicly**
   - Don't commit them to GitHub
   - Don't post them in forums or Discord

2. **Restrict API keys**
   - TMDB: API keys are tied to your account
   - YouTube: Use application restrictions (see step 5 above)

3. **Monitor usage**
   - TMDB: Check usage at themoviedb.org → Settings → API
   - YouTube: Check quotas at console.cloud.google.com → APIs & Services → Dashboard

4. **Regenerate if compromised**
   - TMDB: Delete and create a new key
   - YouTube: Delete key and create a new one

### What the Plugin Does with Your Keys

- Keys are stored **locally** on your Android TV device
- Stored in Android's SharedPreferences (encrypted on Android 6+)
- **Never** sent to any server except TMDB/YouTube APIs
- Used only to fetch wallpaper content

---

## Troubleshooting API Issues

### TMDB Issues

**"Invalid API key"**
- Double-check you copied the entire key
- Make sure there are no extra spaces
- Verify you're using API Key v3 (not v4)

**"Rate limit exceeded"**
- You've made too many requests
- Wait 10 seconds or try the next day
- Clear app cache to reduce requests

**"Resource not found"**
- API is working correctly
- No content available for the query

### YouTube Issues

**"API key not valid"**
- Check you copied the entire key
- Ensure YouTube Data API v3 is enabled
- Check API restrictions aren't too strict

**"Quota exceeded"**
- You've used your daily 10,000 units
- Resets at midnight Pacific Time
- Consider upgrading to paid tier if needed

**"API key restrictions violated"**
- If you set Android app restrictions:
  - Verify package name is correct
  - Add SHA-1 fingerprint of your signing key

### General Issues

**"Network error"**
- Check internet connection
- Try again in a few moments
- Check if API service is down:
  - TMDB: [status.themoviedb.org](https://status.themoviedb.org)
  - YouTube: [status.cloud.google.com](https://status.cloud.google.com)

---

## Getting More Help

### TMDB Resources
- [TMDB API Documentation](https://developers.themoviedb.org/3)
- [TMDB Forums](https://www.themoviedb.org/talk)
- [TMDB Status Page](https://status.themoviedb.org)

### YouTube Resources
- [YouTube Data API Documentation](https://developers.google.com/youtube/v3)
- [Google Cloud Console](https://console.cloud.google.com)
- [YouTube API Forum](https://support.google.com/youtube/community)

### Plugin Support
- Check logs: `adb logcat | grep DynamicWallpaper`
- Open GitHub issue
- Include API error messages (without the actual key!)

---

## Cost Summary

Both APIs are **completely free** for personal use:

| API | Free Tier | Sufficient For Plugin? |
|-----|-----------|----------------------|
| TMDB | 1,000 requests/day | ✅ Yes (uses ~50/day) |
| YouTube | 10,000 units/day | ✅ Yes (uses ~20/day) |

You won't need to pay anything to use this plugin! 🎉
