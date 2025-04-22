package com.example.habitude.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.habitude.R
import com.example.habitude.ui.viewmodels.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.habitude.ui.models.User
import com.google.firebase.firestore.core.UserData
import org.checkerframework.common.subtyping.qual.Bottom


@Composable
fun ProfileScreen(navHostController: NavHostController) {
    val profileViewModel: ProfileViewModel = viewModel()
    val userData = profileViewModel.userData.value

    // Top-Level Column for the layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header Section
        HeaderSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Section (User Data)
        if (userData == null) {
            LoadingState()
        } else {
            UserProfile(userData)
        }

        Spacer(modifier = Modifier.weight(1f)) // Takes the remaining space to push the Button to the bottom

        // Logout Button Section
        LogoutButton {
            // Handle logout logic here (e.g., clear session, navigate to login screen)
          //  Toast.makeText(LocalContext.current, "Logged out", Toast.LENGTH_SHORT).show()
        }
    }

    // Floating Action Button for Edit Profile action
    EditProfileFAB()
}
//MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
@Composable
fun UserProfile(userData: User) {
    // Displaying the user profile data in a beautifully styled card
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = MaterialTheme.shapes.medium,
          //  elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
            colors = CardDefaults.cardColors( // Set the background color
                containerColor = Color(0xFF570C3E).copy(0.4f) // Light gray, replace with your desired color
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileDetail(label = "Name", value = userData.name)
                ProfileDetail(label = "Age", value = userData.age.toString())
                ProfileDetail(label = "Gender", value = userData.gender)
                ProfileDetail(label = "Height", value = userData.height)
                ProfileDetail(label = "Weight", value = userData.weight)
                ProfileDetail(label = "Email", value = userData.email)
            }
        }
    }
}

@Composable
fun ProfileDetail(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            ),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun HeaderSection() {
    // Profile screen header section with a title and profile picture
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Profile picture (circle shaped)
        ProfileImage()

        Spacer(modifier = Modifier.height(16.dp))

        // Profile title
        Text(
            text = "My Profile",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
fun ProfileImage() {
    val image: Painter = painterResource(id = R.drawable.ic_profile) // Replace with actual profile image resource
    Image(
        painter = image,
        contentDescription = "Profile Image",
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .shadow(8.dp, CircleShape, clip = true)
    )
}

@Composable
fun LoadingState() {
    // A simple loading spinner while user data is being fetched
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun LogoutButton(onLogout: () -> Unit) {
    Button(
        onClick = onLogout,  // Use the onLogout callback passed in the parameter
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "Logout",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
        )
    }
}


@Composable
fun EditProfileFAB() {
    Box(modifier = Modifier.fillMaxSize()) { // Add Box for alignment
        FloatingActionButton(
            onClick = {
                // Handle edit profile navigation or action here
            },
            modifier = Modifier
                .padding(bottom = 100.dp, end = 16.dp)
                .align(Alignment.BottomEnd), // Align to bottom-end
            containerColor = MaterialTheme.colorScheme.primary

        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Profile",
                tint = Color.White // Ensure the icon contrasts with the background
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navHostController = rememberNavController())
}
