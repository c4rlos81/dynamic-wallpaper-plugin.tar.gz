#!/bin/bash

# Quick build script for Gitpod
# Run this if the automatic build didn't work

echo "🔧 Dynamic Wallpaper - Quick Build Script"
echo "=========================================="
echo ""

# Set Android environment
export ANDROID_HOME=$HOME/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# Check if SDK is installed
if [ ! -d "$ANDROID_HOME" ]; then
    echo "❌ Android SDK not found. Installing..."
    
    cd $HOME
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip
    unzip -q commandlinetools-linux-9477386_latest.zip
    mkdir -p android-sdk/cmdline-tools/latest
    mv cmdline-tools/* android-sdk/cmdline-tools/latest/
    rm commandlinetools-linux-9477386_latest.zip
    
    yes | sdkmanager --licenses > /dev/null 2>&1
    sdkmanager "platform-tools" "platforms;android-33" "build-tools;33.0.0"
    
    echo "✅ Android SDK installed"
fi

# Navigate to project
cd /workspace/dynamic-wallpaper-plugin || cd ~/dynamic-wallpaper-plugin || cd .

# Clean previous builds
echo "🧹 Cleaning previous builds..."
./gradlew clean

# Build the APK
echo "🔨 Building APK (this may take 5-10 minutes)..."
./gradlew assembleDebug

# Check if build succeeded
if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo ""
    echo "🎉 SUCCESS! APK built successfully!"
    echo ""
    echo "📱 Your APK is at:"
    echo "   app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    echo "📥 To download:"
    echo "   1. Look in the left sidebar (Files panel)"
    echo "   2. Navigate to: app/build/outputs/apk/debug/"
    echo "   3. Right-click 'app-debug.apk'"
    echo "   4. Select 'Download'"
    echo ""
    echo "📦 File size:"
    ls -lh app/build/outputs/apk/debug/app-debug.apk | awk '{print "   " $5}'
    echo ""
else
    echo ""
    echo "❌ Build failed. Check the errors above."
    echo ""
    echo "Try running with more details:"
    echo "   ./gradlew assembleDebug --stacktrace"
    echo ""
fi
