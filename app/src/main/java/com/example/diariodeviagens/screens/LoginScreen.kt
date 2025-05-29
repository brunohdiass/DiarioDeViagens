package com.example.diariodeviagens.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.diariodeviagens.R
import com.example.diariodeviagens.model.User
import com.example.diariodeviagens.service.RetrofitFactory

@Composable
fun LoginInput(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        textStyle = LocalTextStyle.current.copy(color = Color.White)

    )
}

@Composable
fun LoginScreen(navController: NavHostController?) {

    var email = remember { mutableStateOf("") }
    var senha = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
// Fundo com imagem
        Image(
            painter = painterResource(id = R.drawable.telafundo1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

// Overlay escuro
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x66000000))
        )

// Logo no canto superior esquerdo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .align(Alignment.TopStart)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center)
                .background(Color(0xCCA86523), RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Bem-Vindo ao Abordo!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFF3E2723),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Prepare-se para registrar suas aventuras, explorar novos destinos e reviver momentos inesquecíveis.",
                    fontSize = 10.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                LoginInput(
                    value = email.value, onValueChange = { email.value = it }, label = "Email"
                )

                Spacer(modifier = Modifier.height(16.dp))

                LoginInput(
                    value = senha.value, onValueChange = { senha.value = it }, label = "Senha"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Esqueceu sua senha?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 4.dp),
                    textAlign = TextAlign.End,
                    color = Color.White,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        navController?.navigate("TelaMyViagens")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3E2723), contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Login", fontWeight = FontWeight.Bold, fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = {
                        navController?.navigate("CadastroScreen")
                    }, modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Cadastre-se", fontSize = 14.sp, color = Color(0xFF3E2723)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TelaLoginPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}