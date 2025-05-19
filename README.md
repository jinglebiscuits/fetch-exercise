# Fetch Exercise
A native Android application that displays a list of fetched items from the provided URL.

## Features
- Fetches JSON list data from the provided URL
- Groups items by `listId`
- Sorts items first by `listId` then by `name`
- Filters out items with blank or null names
- Displays groups in a Vertical list with a scrollable Horizontal list that contains the items of each group
- Handles network errors

## Technical Details
- Built using Kotlin and Jetpack Compose
- Tries to follow MVI (Model View Intent) architecture pattern, though honestly might be a hybrid between MVVM and MVI
- Uses the Repository pattern for data management
- Material 3 for UI

## Setup
1. Close the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the application on an emulator or physical device

## Testing
The project includes tests for data processing and filtering. Run tests using:
```bash
./gradlew test
```

## Future improvements
Improvements I would like to add:
- Dependency Injection using Hilt
- Offline caching with Room
- Pull to refresh
- "See more" button for each group to display that group's items in a vertical list
- Selection animation for each item. Items should flip to an inversed color scheme and retain a "selected" attribute.
