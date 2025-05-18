- I can choose Java or Kotlin. I choose Kotlin because I like the language better, and that's the way modern Android apps are developed.
- buildable on the latest (non-pre release) tools and supporting the current release mobile OS.

Fetch data from https://hiring.fetch.com/hiring.json
Display the items:
- Display all the items grouped by "listId"
- Sort the results first by "listId" then by "name" when displaying.
- Filter out any items where "name" is blank or null

Final result should be an easy to read list.

- I'll use Kotlin and Compose.
- MVI architecture because I've never gone full in on that before.
- Use Hilt for dependency injection
- Get some tests setup
  - test that null/blank names are filtered out
- follow https://developer.android.com/topic/architecture as closely as possible

## Stretch Goals
- support dark mode
- account for no network
