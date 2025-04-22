🌿 Habitude - Your Personal Habit Tracker
  Habitude is an intuitive Android app designed to help users track their daily Do's and Don'ts — positive habits to build and negative habits to avoid. Built using Jetpack Compose, it features a sleek dual-navigation system that enhances usability and organization.

🚀 Features
    🔐 Login Flow – Secure user login landing on a smart dashboard.
    
    🧭 Dual Navigation Structure:

Bottom Navigation:

    ✅ Do’s – Track good habits to build.
    
    🚫 Don’ts – Monitor habits you want to avoid.

Drawer Navigation:

    🏠 Home
    
    🔔 Notifications
    
    🙍 Profile
    
    🚪 Logout

🎯 Navigation Logic:

    After login, the user lands on the Do’s screen with Home selected in the drawer.
    
    Navigating between Do’s and Don’ts keeps the drawer on Home.
    
    Selecting Notifications or Profile switches to dedicated screens without bottom navigation.

📅 Track Progress:

  View tracked days, skipped days, and completion status.

  ✍️ Edit Habits with ease.


📁 Project Structure
    📁 app
     ┣ 📁 ui
     ┃ ┣ MainActivity.kt
     ┃ ┣ DrawerContent.kt
     ┃ ┣ BottomNavigationBar.kt
     ┃ ┣ screens/
     ┃ ┃ ┣ HomeScreen.kt
     ┃ ┃ ┣ DosScreen.kt
     ┃ ┃ ┣ DontsScreen.kt
     ┃ ┃ ┣ NotificationsScreen.kt
     ┃ ┃ ┗ ProfileScreen.kt
     ┣ 📁 models
     ┃ ┗ Habit.kt, Habits.kt, Habit_Tracker.kt
     ┣ 📁 navigation
     ┃ ┗ Screen.kt (Sealed class for defining navigation routes)
🛠️ Tech Stack
    🧩 Jetpack Compose – Modern declarative UI toolkit.
    
    🚀 Navigation Component – Smooth in-app navigation.
    
    🏷️ Material3 – Sleek, modern UI components.

📦 Getting Started
    ✅ Prerequisites
        Android Studio Flamingo or newer
        
        Kotlin 1.9+
        
        Jetpack Compose libraries
        
        Gradle 8+

    🔧 Installation

        git clone https://github.com/sherinberny/habitude.git
        cd habitude
        
    Open the project in Android Studio, sync Gradle, and run it on an emulator or physical device.

👩‍💻 Developed By
Sherin
📍 Vancouver, BC
💡 Android & Full Stack Developer
📫 https://www.linkedin.com/in/sherin-babu-2907/
