#!/bin/bash

# OpenCrumb iOS Build Script

set -e

echo "🔨 Building iOS Framework..."
cd "$(dirname "$0")/.."
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

echo "✅ Framework built successfully!"
echo ""
echo "📍 Framework location:"
echo "   $(pwd)/shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework"
echo ""
echo "📱 Next steps:"
echo "   1. Open Xcode"
echo "   2. Create new iOS App project in iosApp/ directory"
echo "   3. Add the framework (see iosApp/README.md for details)"
echo "   4. Copy Swift files from iosApp/iosApp/"
echo "   5. Run on iOS Simulator"
echo ""
echo "📖 Full instructions: iosApp/README.md"
