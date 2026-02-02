# iOS App Setup Instructions

## Framework Location
The iOS framework has been built and is located at:
```
../shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework
```

## Manual Setup in Xcode

### 1. Create New iOS App Project
1. Open Xcode
2. File → New → Project
3. Choose "iOS" → "App"
4. Product Name: `iosApp`
5. Interface: SwiftUI
6. Language: Swift
7. Save in: `/Users/ceratia/AndroidStudioProjects/OpenCrumb/iosApp`

### 2. Add Framework
1. In Xcode, select the project in the navigator
2. Select the `iosApp` target
3. Go to "General" tab
4. Scroll to "Frameworks, Libraries, and Embedded Content"
5. Click "+" button
6. Click "Add Other..." → "Add Files..."
7. Navigate to: `../shared/build/bin/iosSimulatorArm64/debugFramework/`
8. Select `shared.framework`
9. Make sure "Embed & Sign" is selected

### 3. Add Framework Search Path
1. Go to "Build Settings" tab
2. Search for "Framework Search Paths"
3. Add: `$(PROJECT_DIR)/../shared/build/bin/iosSimulatorArm64/debugFramework`

### 4. Replace App Files
Replace the generated files with the ones in `iosApp/iosApp/`:
- `iOSApp.swift` - App entry point
- `ContentView.swift` - Main view with Compose integration

### 5. Run
1. Select iPhone simulator (must be arm64, like iPhone 15)
2. Click Run (⌘R)

## Rebuild Framework
If you make changes to the shared module:
```bash
cd /Users/ceratia/AndroidStudioProjects/OpenCrumb
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

## What Works
✅ All recipes with images
✅ Recipe details with portions control
✅ All guides with inline images
✅ Guide details with step-by-step photos
✅ Calculator (Neapolitan & Focaccia)
✅ Bottom navigation
✅ External URL handling (Amazon link)
✅ All 68 images via Compose Resources
✅ 6 language translations

## Architecture
- **shared module**: 100% Kotlin Multiplatform with Compose Multiplatform UI
- **Android app**: Thin wrapper using Navigation Compose
- **iOS app**: Thin wrapper using SwiftUI + ComposeUIViewController
- **Business logic**: Fully shared (RecipeRepository, ViewModels)
- **UI**: Fully shared (all screens in commonMain)
