#!/bin/bash
APP_BUNDLE=$(find ~/Library/Developer/Xcode/DerivedData/OpenCrumb-*/Build/Products/Debug-iphonesimulator/OpenCrumb.app -type d 2>/dev/null | head -1)
RESOURCES_DIR="$(dirname "$0")/../shared/src/commonMain/composeResources"

if [ -z "$APP_BUNDLE" ]; then
    echo "❌ App bundle not found. Build the app first."
    exit 1
fi

# Copy compose-resources with correct structure
rm -rf "$APP_BUNDLE/compose-resources"
mkdir -p "$APP_BUNDLE/compose-resources/composeResources/opencrumb.shared.generated.resources"
cp -R "$RESOURCES_DIR/"* "$APP_BUNDLE/compose-resources/composeResources/opencrumb.shared.generated.resources/"

# Copy JSON files
cp "$(dirname "$0")/../shared/src/commonMain/resources/"*.json "$APP_BUNDLE/"

echo "✅ Resources copied to app bundle"
