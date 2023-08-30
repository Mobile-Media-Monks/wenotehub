package com.example.wenotehub.ui.navigation

import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wenotehub.presentation.auth.GoogleAuthUiClient
import com.example.wenotehub.presentation.auth.SignInViewModel
import com.example.wenotehub.ui.screen.CreateScreen
import com.example.wenotehub.ui.screen.FinishedScreen
import com.example.wenotehub.ui.screen.HomeScreen
import com.example.wenotehub.ui.screen.SettingsScreen
import com.example.wenotehub.ui.screen.SignScreen
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch


@Composable
fun NavGraph(navController: NavHostController, onChangeState: () -> Unit) {

    val context = LocalContext.current

    val googleAuth by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }



    NavHost(navController = navController, startDestination = ScreenNavGraph.SignInScreen.route) {
        composable(route = ScreenNavGraph.SignInScreen.route) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val coroutine = rememberCoroutineScope()


            LaunchedEffect(key1 = Unit) {
                if(googleAuth.getSignedInUser() != null) {
                    Log.d("AQUII", "AQUII ${googleAuth.getSignedInUser()}")
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = { result ->
                    if(result.resultCode  == RESULT_OK){
                        coroutine.launch {
                            val signInResult = googleAuth.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
                    navController.navigate(ScreenNavGraph.HomeScreen.route)
                    viewModel.resetState()
                }
            }

            SignScreen(state = state, onSingClick = {
                coroutine.launch {
                    val signInIntentSender = googleAuth.signInTwo()
                    launcher.launch(signInIntentSender)
                }
            })

        }
        composable(route = ScreenNavGraph.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = ScreenNavGraph.FinishedScreen.route) {
            FinishedScreen(navController)
        }
        composable(route = ScreenNavGraph.SettingsScreen.route) {
            SettingsScreen(navController)
        }
        composable(route = ScreenNavGraph.CreateScreen.route) {
            LaunchedEffect(Unit) {
                onChangeState()
            }
            CreateScreen(navController)
        }
    }
}