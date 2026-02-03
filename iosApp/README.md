# iOS App

Xcode project for OpenCrumb iOS app using Kotlin Multiplatform shared code.

## Running the App

1. **Open the project:**
   ```bash
   open iosApp/OpenCrumb/OpenCrumb.xcodeproj
   ```

2. **Run in Xcode:**
   - Select iPhone simulator
   - Product → Run (⌘R)

## After Clean Builds

If you do a clean build (⇧⌘K) or the resources are missing, run:
```bash
./iosApp/copy-resources.sh
```

This copies Compose Resources (images) and JSON files to the app bundle.

## Rebuilding the Framework

After changing shared Kotlin code:
```bash
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

Then clean and rebuild in Xcode (⇧⌘K, ⌘R).

## Project Structure

```
iosApp/
├── OpenCrumb/
│   ├── OpenCrumb.xcodeproj/     ← Xcode project
│   └── OpenCrumb/               ← Swift source files
│       ├── iOSApp.swift         ← App entry point
│       ├── ContentView.swift    ← Compose wrapper
│       ├── Info.plist
│       └── Assets.xcassets/
├── iOSApp.swift                 ← Template (not used)
├── ContentView.swift            ← Template (not used)
├── build-ios.sh                 ← Framework build script
└── copy-resources.sh            ← Resource copy script
```

## Troubleshooting

**App crashes with MissingResourceException:**
Run `./iosApp/copy-resources.sh` and relaunch the app without rebuilding.

**Framework not found:**
Check Build Settings → Framework Search Paths should be:
`$(PROJECT_DIR)/../../shared/build/bin/iosSimulatorArm64/debugFramework`
