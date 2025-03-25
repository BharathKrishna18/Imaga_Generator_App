package com.bcorp.imagageneratorapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
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
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(navController: NavController) {
    val ctx = LocalContext.current

    val auth = remember {
        FirebaseAuth.getInstance()
    }
    val firstName = remember {
        mutableStateOf("")
    }
    val lastName = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Image Generator", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(Color.Red)
            )
        }) {

        Column(modifier = Modifier.padding(vertical = 120.dp, horizontal = 60.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Enter your details")
            OutlinedTextField(value = firstName.value, onValueChange = {
                firstName.value = it
            }, label = {
                Text(text = "Name")
            })
            OutlinedTextField(value = lastName.value, onValueChange = {
                lastName.value = it
            }, label = {
                Text(text = "Last Name")
            })
            OutlinedTextField(value = email.value, onValueChange = {
                email.value = it
            }, label = {
                Text(text = "Email")
            },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                keyboardActions = KeyboardActions.Default)
            OutlinedTextField(value = password.value, onValueChange = {
                password.value = it
            },label = {
                Text(text = "Password")
            },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password))

            OutlinedButton(onClick = {
                auth.createUserWithEmailAndPassword(email.value,password.value).addOnCompleteListener{
                    task ->
                    if(task.isSuccessful){
                        navController.navigate("MainScreen")
                        Toast.makeText(ctx,"Sign Up Successful",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(ctx,"Sign Up Failed",Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{
                    Toast.makeText(ctx,it.localizedMessage,Toast.LENGTH_SHORT).show()
                    it.printStackTrace()
                    navController.navigate("Sign Up")
                }

            }) {
                Text(text = "Sign Up")
            }
            Button(onClick = {
                navController.navigate("Login")
            }) {
                Text(text = "Already a User? Sign In")
            }
        }
    }
}

