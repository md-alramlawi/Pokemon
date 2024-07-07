# Pokemon KMP Demo App

## Overview

The Pokemon KMP Demo App is a Kotlin Multiplatform (KMP) project designed to showcase the capabilities of modern cross-platform development. This app fetches data from an API, saves it locally, provides search functionality, and allows users to view detailed information about selected Pokémon.

## Features

- **Fetch Data from API**: Retrieve Pokémon data from a remote API.
- **Local Storage**: Save fetched data locally for offline access.
- **Search Functionality**: Easily search for Pokémon by name.
- **View Details**: Detailed information about each Pokémon, including stats and images.

## Screenshots

### Home Screen
![Home Screen](https://github.com/md-alramlawi/Pokemon/assets/60019872/8b5cf915-849a-4720-a74f-b111b7c8b091)

### Details Screen
![Details Screen](https://github.com/md-alramlawi/Pokemon/assets/60019872/6c014ef4-8842-4dbe-9747-32300dcb6539)

## Technologies and Libraries

- **Kotlin Multiplatform**: Share code between Android and iOS.
- **Compose Multiplatform**: Build native UI for multiple platforms.
- **Modularization**: Organized codebase into modules for better scalability and maintainability.
- **Animation**: Smooth and interactive UI animations.
- **Navigation**: Seamless navigation between different screens.
- **Adaptive Layout**: Responsive design for different screen sizes.
- **Ktor**: Networking library for API calls.
- **Room**: Local database for storing data.
- **Koin DI**: Dependency injection for managing dependencies.
- **Paging**: Efficiently load large data sets.
- **Horizontal Pager**: Swipe through images horizontally.
- **Shimmer Effect**: Beautiful loading animations to enhance user experience.

## Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio)
- [Xcode](https://developer.apple.com/xcode/) (for iOS development)
- [Kotlin Multiplatform Mobile Plugin](https://kotlinlang.org/docs/multiplatform-mobile-getting-started.html#install-the-kotlin-multiplatform-mobile-plugin)

### Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/md-alramlawi/pokemon-kmp-demo.git
    cd pokemon-kmp-demo
    ```

2. Open the project in Android Studio.

3. Build and run the project on your preferred platform (Android or iOS).