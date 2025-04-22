Habitude is a Jetpack Compose-based Android application featuring both Drawer Navigation and Bottom Navigation, allowing users to seamlessly navigate between different parts of the app.

✨ Features
🔐 Login Flow: Authenticated users are directed to a dynamic home screen.

🧭 Dual Navigation:

Bottom Navigation: Includes "Do's" and "Don'ts".

Drawer Navigation: Includes "Home", "Notifications", "Profile", and "Logout".

🎯 Smart Navigation Logic:

On login, user lands on the "Do's" screen with "Home" selected in the drawer.

Switching between "Do's" and "Don'ts" keeps "Home" selected in the drawer.

Navigating to "Notifications" or "Profile" hides the bottom navigation, focusing on drawer-based screens only.

⚙️ Built entirely with Jetpack Compose, State Management, and Navigation Components.

🚀 Getting Started
Prerequisites
Android Studio Flamingo or later

Kotlin 1.9+

Gradle 8+

Jetpack Compose + Navigation libraries

Clone the Repository

git clone https://github.com/sherinberny/habitude.git
cd habitude


