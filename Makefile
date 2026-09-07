# BakalariGuard Android build Makefile
# Designed for compilation directly on Redmi using Termux

APP_NAME := BakalariGuard
GRADLE := ./gradlew

.PHONY: help build debug release clean install open

help:
	@echo "$(APP_NAME) build targets"
	@echo ""
	@echo "make debug    - Build debug APK"
	@echo "make release  - Build release APK"
	@echo "make install  - Install APK through adb"
	@echo "make open     - Open generated APK with Android package installer"
	@echo "make clean    - Remove build outputs"

# Build debug APK for development testing
build debug:
	$(GRADLE) assembleDebug

# Build signed release APK when keystore is configured locally
release:
	$(GRADLE) assembleRelease

# Install latest debug APK to connected Android device
install:
	adb install -r app/build/outputs/apk/debug/app-debug.apk

# Open generated APK file using Android intent
open:
	am start -a android.intent.action.VIEW \
		-d file://$(PWD)/app/build/outputs/apk/debug/app-debug.apk \
		-t application/vnd.android.package-archive

# Remove all generated Gradle build files
clean:
	$(GRADLE) clean
