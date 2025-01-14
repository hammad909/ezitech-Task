package com.oneasad.googleloginjetpackcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.oneasad.googleloginjetpackcompose.myviewmodels.FireBaseViewModel
import com.oneasad.googleloginjetpackcompose.myviewmodels.GoogleSignInViewModel
import com.oneasad.googleloginjetpackcompose.screens.AdminPanel
import com.oneasad.googleloginjetpackcompose.screens.Dashboard
import com.oneasad.googleloginjetpackcompose.screens.LoginScreen
import com.oneasad.googleloginjetpackcompose.ui.theme.GoogleLoginJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoogleLoginJetpackComposeTheme {
                GoogleLoginApp()
            }
        }
    }
}

enum class ProfileEnum{
    Home, Profile, Admin
}


@Composable
fun GoogleLoginApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val context = LocalContext.current
    val googleSignInViewModel = GoogleSignInViewModel()
    val admin by googleSignInViewModel.user.observeAsState()

    NavHost(navController = navController, startDestination = ProfileEnum.Home.name) {

        composable(route = ProfileEnum.Home.name) {
            LoginScreen(
                onGoogleSignInClick =
                { googleSignInViewModel.handleGoogleSignIn(context,navController) },
                modifier = modifier
            )
        }

        composable(route = ProfileEnum.Profile.name) {
                Dashboard(
                    googleSignInViewModel = googleSignInViewModel,
                    fireBaseViewModel = FireBaseViewModel(),
                    navController = navController
                )
        }

        composable(route = ProfileEnum.Admin.name) {
                AdminPanel(googleSignInViewModel = googleSignInViewModel,
                    fireBaseViewModel = FireBaseViewModel(),
                    navController = rememberNavController())
        }

    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoogleLoginJetpackComposeTheme  {
        GoogleLoginApp()
    }
}