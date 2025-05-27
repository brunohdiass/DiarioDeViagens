package com.example.diariodeviagens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diariodeviagens.screens.CadastroScreen
import com.example.diariodeviagens.screens.LoginScreen
import com.example.diariodeviagens.ui.theme.DiarioDeViagensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiarioDeViagensTheme {
                var navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "LoginScreen"
                ) {
                    composable("LoginScreen") {
                        LoginScreen(navController)
                    }

                    composable("CadastroScreen"){
                        CadastroScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DiarioDeViagensTheme {
        Greeting("Android")
    }
}