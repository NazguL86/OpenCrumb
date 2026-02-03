#!/bin/bash
cd "$(dirname "$0")/.."
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
echo "✓ iOS framework built at: shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework"
