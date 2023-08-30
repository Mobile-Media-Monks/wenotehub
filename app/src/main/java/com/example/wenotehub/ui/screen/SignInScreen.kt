package com.example.wenotehub.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.*
import com.example.wenotehub.R
import com.example.wenotehub.presentation.auth.SignInState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignScreen(
    state: SignInState,
    onSingClick: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Let’s Login",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "And notes your idea",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextInputView(
            labelText = "Email Address",
            placeholder = "Example: johndoe@gmail.com",
            value = text,
            onChangeText = {
                text = it
            }
        )
        Spacer(modifier = Modifier.padding(vertical = 12.dp))
        TextInputView(
            labelText = "Password",
            placeholder = "********",
            value = text,
            onChangeText = {
                text = it
            }
        )
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        Text(
            text = "Forgot Password", style = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = TextDecoration.Underline
            ),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
        ) {
            Row() {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Login", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        LoginDivider()
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.outlineVariant)

        ) {
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Login with Google",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputView(
    labelText: String,
    placeholder: String,
    value: String,
    onChangeText: (value: String) -> Unit
) {
    Text(
        text = labelText,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(bottom = 12.dp)
    )
    TextField(
        value = value,
        onValueChange = { onChangeText(it) },
        placeholder = { Text(text = placeholder) },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = MaterialTheme.colorScheme.outlineVariant,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = MaterialTheme.shapes.medium
            )
            .fillMaxWidth()
    )
}

@Composable
fun LoginDivider() {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(
            modifier = Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .weight(1f)
        )
        Text(
            text = "Or",
            modifier = Modifier.padding(horizontal = 18.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB5B5B5)
        )
        Divider(
            modifier = Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .weight(1f)
        )
    }
}