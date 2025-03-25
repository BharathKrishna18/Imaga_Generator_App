package com.bcorp.imagageneratorapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val ctx = LocalContext.current
    val auth = remember {
        FirebaseAuth.getInstance()
    }
    LaunchedEffect(Unit) {
        val current_user = auth.currentUser
        if(current_user!=null){
            delay(10)
            navController.navigate("MainScreen"){
                popUpTo("Login"){inclusive = true}
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Image Generator", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(Color.Red)
            )
        }){
        val email = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }


        Column(modifier = Modifier.padding(vertical = 120.dp, horizontal = 60.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Login")
            OutlinedTextField(value = email.value, onValueChange = {
                email.value = it
            }, label = {
                Text(text = "Email")
            },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email))
            OutlinedTextField(value = password.value, onValueChange = {
                password.value = it
            }, label = {
                Text(text = "Password")
            },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password))

            OutlinedButton(onClick = {
                auth.signInWithEmailAndPassword(email.value,password.value).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(ctx,"Login Successful!",Toast.LENGTH_SHORT).show()
                        navController.navigate("MainScreen")
                    }else{
                        Toast.makeText(ctx,"Login Failed!",Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{
                    Toast.makeText(ctx,"Login Failed!",Toast.LENGTH_SHORT).show()
                    navController.navigate("Login")
                }
            }) {
                Text(text = "Sign In")
            }
            Button(onClick = {
                navController.navigate("SignUp")
            }) {
                Text(text = "Not a User? Sign Up")
            }
        }
    }
}
@Composable
@Preview
fun MyPreview() {
    SignUpScreen(navController = rememberNavController())
}