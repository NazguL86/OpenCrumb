# Open Crumb

"Open Crumb" is a modern, open-source recipe-keeping application for Android and iOS, built with Kotlin Multiplatform and Compose Multiplatform. The project aims to provide a clean, simple, and delightful user experience for browsing and managing personal recipes.

## Project Scope & Vision

The core goal of this project is to build a beautiful and functional recipe app that follows modern Android development best practices.

The development is planned in phases:

1.  **Foundation (Current Phase):** Build the core UI for browsing recipes. This includes a recipe list screen and a recipe detail screen, populated with local sample data. The focus is on creating a solid architectural base with Jetpack Compose and Navigation.
2.  **User-Created Content:** Implement features allowing users to add, edit, and delete their own recipes. This will involve creating forms and saving the data locally on the device.
3.  **Local Persistence:** Integrate a local database (like Room) to persist recipes, so users' data is saved between app sessions.
4.  **Enhanced Features:** Add more advanced functionality like recipe search, filtering by ingredients, and the ability to scale recipes.

## Technology Stack

This project uses Kotlin Multiplatform to share code between Android and iOS:

*   **UI:** 100% Compose Multiplatform for a declarative and reactive user interface.
*   **Architecture:** Single-activity (Android) / single-window (iOS) architecture with Navigation Compose.
*   **Language:** Kotlin, including coroutines for asynchronous operations.
*   **Design:** Following Material Design guidelines to ensure a consistent and intuitive UI.
*   **Platforms:** Android (native) and iOS (via Kotlin/Native framework).

## Getting Started

### Android
Open the project in Android Studio and click "Run". The app will compile and run on an emulator or connected device.

### iOS
See [iosApp/README.md](iosApp/README.md) for iOS setup instructions.