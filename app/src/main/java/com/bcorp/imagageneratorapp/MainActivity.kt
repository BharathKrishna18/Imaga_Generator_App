package com.bcorp.imagageneratorapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bcorp.imagageneratorapp.config.ImageViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val config = LocalConfiguration.current
    val viewModel = remember { ImageViewModel(config) } // ViewModel should be remembered
    val isLoading = remember { mutableStateOf(false) }
    val prompt = remember { mutableStateOf("") }

    // Observe LiveData from ViewModel
    val imageBitmap by viewModel.imageBit.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Image Generator", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(Color.Red)
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(vertical = 150.dp, horizontal = 50.dp)
        ) {
            OutlinedTextField(
                value = prompt.value,
                onValueChange = { prompt.value = it },
                keyboardActions = KeyboardActions.Default,
                keyboardOptions = KeyboardOptions.Default,
                label = { Text(text = "Enter your prompt here") }
            )

            OutlinedButton(
                onClick = {
                    isLoading.value = true
                    Log.d("Prompt", prompt.value)
                    viewModel.generateImage(prompt = prompt.value) {
                        isLoading.value = false
                    }
                }
            ) {
                Text(text = "Generate")
            }

            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.size(100.dp))
            } else {
                imageBitmap?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Generated Image",
                        modifier = Modifier.size(500.dp) // Adjust size as needed
                    )
                } ?: run {
                    errorMessage?.let { msg ->
                        Text(text = "Error: $msg", color = Color.Red)
                    }
                }
            }
        }
    }
}

