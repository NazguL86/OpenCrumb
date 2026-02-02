// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "iosApp",
    platforms: [
        .iOS(.v15)
    ],
    products: [
        .executable(
            name: "iosApp",
            targets: ["iosApp"]
        )
    ],
    targets: [
        .executableTarget(
            name: "iosApp",
            dependencies: [],
            path: "iosApp",
            linkerSettings: [
                .unsafeFlags([
                    "-F", "../shared/build/bin/iosSimulatorArm64/debugFramework",
                    "-framework", "shared"
                ])
            ]
        )
    ]
)
