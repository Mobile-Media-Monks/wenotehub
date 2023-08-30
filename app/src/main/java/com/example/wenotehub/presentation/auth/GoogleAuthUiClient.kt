package com.example.wenotehub.presentation.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.example.wenotehub.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            Log.d("ERROR", "${e.message}")
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }


    fun signInTwo(): Intent? {
        val result = try {
            GoogleSignIn.getClient(context, builderSignInRequest())
        } catch (e: Exception) {
            Log.d("WENOTE", "${e.message}")
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }

        return result?.signInIntent
    }


    suspend fun signInWithIntent(intent: Intent): SignInResult {

        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        val account = task.getResult(ApiException::class.java)
        /*val credential = oneTapClient.getSignInCredentialFromIntent(intent)*/
        val googleCredentials = GoogleAuthProvider.getCredential(account.idToken, null)

       return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            Log.d("WENOTELOG", "USER ${user?.displayName} ${user?.uid}")

            SignInResult(
                data = user?.run {
                    UserD(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString(),
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            Log.d("WENOTELOG", "catch -> ${e} --- ${e.message}")
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserD? = auth.currentUser?.run {
        UserD(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.client_id_web))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }


    private fun builderSignInRequest(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestIdToken(context.getString(R.string.client_id_web))
            .requestEmail()
            .build()
    }
}