# 🚀 Building with Gitpod - Complete Guide

This guide shows you how to build the Dynamic Wallpaper APK using Gitpod (from your phone!).

## Why Gitpod?

✅ **Free** cloud development environment  
✅ **Works on phone browsers**  
✅ **Pre-configured** for Android builds  
✅ **Fast** - better resources than Replit  
✅ **Easy** - automatic setup included  

## Prerequisites

- GitHub account (free)
- Phone with modern browser (Chrome, Firefox, Safari)
- Internet connection

---

## Method 1: Direct Gitpod Link (Easiest!)

### Step 1: Upload to GitHub

1. **Go to [github.com](https://github.com)** on your phone
2. Click **"+"** → **"New repository"**
3. Name it: `dynamic-wallpaper-plugin`
4. Make it **Public** (required for free Gitpod)
5. Click **"Create repository"**

6. **Upload files:**
   - Click **"uploading an existing file"**
   - Extract the .tar.gz on your phone (use ZArchiver app)
   - Upload ALL files from the extracted folder
   - Click **"Commit changes"**

### Step 2: Open in Gitpod

Once uploaded to GitHub, use this URL format:

```
https://gitpod.io/#https://github.com/YOUR_USERNAME/dynamic-wallpaper-plugin
```

**Example:** If your GitHub username is "john123":
```
https://gitpod.io/#https://github.com/john123/dynamic-wallpaper-plugin
```

1. **Paste that URL** in your phone browser
2. **Sign in with GitHub** when prompted
3. Gitpod will automatically:
   - Install Android SDK
   - Install build tools
   - Build your APK
   - Takes 5-10 minutes ⏱️

### Step 3: Download the APK

When build completes, you'll see: "🎉 BUILD COMPLETE!"

1. Look at **left sidebar** (Files icon 📁)
2. Navigate to: `app` → `build` → `outputs` → `apk` → `debug`
3. Find **`app-debug.apk`**
4. **Right-click** → **Download**
5. APK downloads to your phone! 📱

---

## Method 2: Manual Setup (If automatic build fails)

If the automatic build doesn't work:

### In Gitpod Terminal:

```bash
# Make the build script executable
chmod +x build-apk.sh

# Run it
./build-apk.sh
```

This script will:
- Check if Android SDK is installed
- Install it if needed
- Build the APK
- Show you where to download it

---

## Method 3: Using Gitpod Button (Best for Future)

Add this button to your GitHub repo's README:

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/YOUR_USERNAME/dynamic-wallpaper-plugin)

Then just click it to start building!

---

## Step-by-Step: Complete Walkthrough

### 1️⃣ Prepare Files (On Phone)

1. Download the `dynamic-wallpaper-plugin.tar.gz` file
2. Install **ZArchiver** from Play Store (free)
3. Open ZArchiver, find the .tar.gz file
4. Tap it → **Extract here**
5. You now have a `dynamic-wallpaper-plugin` folder

### 2️⃣ Create GitHub Repository

1. Open **Chrome/Firefox** on your phone
2. Go to **github.com**
3. Sign in (or create free account)
4. Tap **"+"** (top right) → **"New repository"**
5. Repository name: `dynamic-wallpaper-plugin`
6. Select: **Public**
7. Tap **"Create repository"**

### 3️⃣ Upload Files to GitHub

**Option A: GitHub Mobile App (Recommended)**
1. Install **GitHub** app from Play Store
2. Open the app, go to your repository
3. Tap **"+"** → Upload files
4. Select ALL files from extracted folder
5. Commit

**Option B: Browser**
1. In your new repository, tap **"uploading an existing file"**
2. Tap **"choose your files"**
3. Navigate to extracted folder
4. Select ALL files (may need to do in batches)
5. Scroll down, tap **"Commit changes"**

**Important:** Make sure you upload:
- `.gitpod.yml` (enables automatic build!)
- `build-apk.sh` (backup build script)
- All source code files
- All gradle files

### 4️⃣ Open in Gitpod

1. Copy your repository URL:
   ```
   https://github.com/YOUR_USERNAME/dynamic-wallpaper-plugin
   ```

2. Add `gitpod.io/#` in front:
   ```
   https://gitpod.io/#https://github.com/YOUR_USERNAME/dynamic-wallpaper-plugin
   ```

3. **Paste in browser** and press Enter

4. **Sign in with GitHub** when asked

5. **Authorize Gitpod** to access your repository

### 5️⃣ Wait for Build

Gitpod will show a terminal window with progress:

```
📦 Downloading Android SDK...
✅ Accepting Android SDK licenses...
📥 Installing Android SDK components...
🔨 Building APK...
```

**This takes 5-10 minutes** - be patient! ⏱️

You'll see:
```
🎉 BUILD COMPLETE!
📱 Your APK is ready at:
   app/build/outputs/apk/debug/app-debug.apk
```

### 6️⃣ Download APK

1. In Gitpod, look at **left sidebar**
2. Click the **Files icon** (📁)
3. Navigate through folders:
   - Click `app`
   - Click `build`
   - Click `outputs`
   - Click `apk`
   - Click `debug`
4. You'll see **`app-debug.apk`**
5. **Right-click** (or long-press on phone)
6. Select **"Download"**
7. APK saves to your phone! 🎉

### 7️⃣ Transfer to Nvidia Shield

1. Open **TV Transfer** app (or similar) on phone
2. Select `app-debug.apk`
3. Send to your Nvidia Shield
4. On Shield, tap the notification to install
5. Allow installation from unknown sources if prompted
6. Done! 🎊

---

## Troubleshooting

### "Repository must be public"
- Free Gitpod only works with public repos
- Go to repo Settings → Danger Zone → Change visibility

### Build fails with "SDK not found"
Run in Gitpod terminal:
```bash
./build-apk.sh
```

### "Gitpod hours exceeded"
- Free tier: 50 hours/month
- Should be plenty for one build (uses ~10 minutes)
- Sign out and sign back in

### Can't download APK
- Try different browser (Chrome works best)
- Check if popup blocker is enabled
- Try: Files panel → Right-click → "Download"

### Build takes too long
- Normal build time: 5-10 minutes
- If >15 minutes, something is stuck
- Refresh page and try again

---

## Tips & Tricks

### Save Time on Future Builds

Once files are on GitHub, you can:
1. Just visit the Gitpod URL again
2. It rebuilds automatically
3. Download new APK

### Close Gitpod When Done

- Click your profile (top right)
- Stop workspace
- This saves your free hours

### Build Multiple Times

Each build uses ~10 minutes of free time.
Free tier = 50 hours = ~300 builds/month!

---

## What Happens During Build?

```
Step 1: Gitpod creates cloud computer
Step 2: Downloads Android SDK (~200MB)
Step 3: Installs build tools
Step 4: Compiles Kotlin code
Step 5: Packages into APK
Step 6: Signs APK
Step 7: APK ready!
```

---

## Alternative: Gitpod CLI (Advanced)

If you're comfortable with terminal:

```bash
# In Gitpod terminal
cd /workspace/dynamic-wallpaper-plugin

# Build
./gradlew assembleDebug

# Check result
ls -la app/build/outputs/apk/debug/
```

---

## Free Tier Limits

**Gitpod Free Plan:**
- ✅ 50 hours/month
- ✅ 4 parallel workspaces
- ✅ 30-minute timeout
- ✅ Public repos only

**This is MORE than enough** for building this APK!

---

## Success Checklist

After following this guide, you should have:

- ✅ GitHub repository created
- ✅ Files uploaded
- ✅ Gitpod workspace opened
- ✅ APK built successfully
- ✅ APK downloaded to phone
- ✅ Ready to transfer to Shield!

---

## Next Steps

Once you have the APK:

1. **Transfer to Shield** using TV Transfer app
2. **Install on Shield**
3. **Get API keys** (see API_SETUP.md)
4. **Configure the app**
5. **Enjoy dynamic wallpapers!**

---

## Need Help?

**Check logs:**
In Gitpod terminal:
```bash
./gradlew assembleDebug --info
```

**Common fixes:**
```bash
# Clean and rebuild
./gradlew clean
./gradlew assembleDebug

# Check Java version
java -version

# Check Gradle version  
./gradlew --version
```

---

## Video Tutorial (Coming Soon)

Would you like a video walkthrough? Let me know!

---

**That's it!** You can now build Android APKs entirely from your phone using Gitpod! 🎉📱

No computer needed! 🚀
