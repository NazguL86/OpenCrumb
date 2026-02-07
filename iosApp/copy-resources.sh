#!/bin/bash
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
FRAMEWORK_DIR="$SCRIPT_DIR/../shared/build/bin/iosSimulatorArm64/debugFramework"
RESOURCES_SRC="$FRAMEWORK_DIR/compose-resources"

# Find app bundle
APP_BUNDLE=$(find ~/Library/Developer/Xcode/DerivedData/OpenCrumb-*/Build/Products/Debug-iphonesimulator/OpenCrumb.app -type d 2>/dev/null | head -1)

if [ -z "$APP_BUNDLE" ]; then
    echo "❌ App bundle not found. Build the app first."
    exit 1
fi

if [ ! -d "$RESOURCES_SRC" ]; then
    echo "❌ Resources not found at $RESOURCES_SRC"
    echo "Run: ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64"
    exit 1
fi

# Copy compose-resources with correct nested structure
rm -rf "$APP_BUNDLE/compose-resources"
mkdir -p "$APP_BUNDLE/compose-resources/composeResources/opencrumb.shared.generated.resources"
cp -R "$RESOURCES_SRC/"* "$APP_BUNDLE/compose-resources/composeResources/opencrumb.shared.generated.resources/"

echo "✅ Resources copied to $APP_BUNDLE/compose-resources"
