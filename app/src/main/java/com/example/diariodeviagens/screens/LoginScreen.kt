package com.example.diariodeviagens.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.diariodeviagens.R
import com.example.diariodeviagens.model.Login
import com.example.diariodeviagens.service.RetrofitFactory
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController?) {

    val email = remember { mutableStateOf("") }
    val senha = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(Color(0xCCA86523), RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                fontSize = 12.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = {it ->
                    email.value = it},
                label = { Text("Email", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Email Icon",
                        tint = Color.White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White,

                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = senha.value ,
                onValueChange = { it ->
                    senha.value = it
                },
                label = { Text("Senha", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Senha Icon",
                        tint = Color.White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White,

                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Esqueceu sua senha?",
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    val login = Login(
                        email = email.value,
                        senha = senha.value
                    )

                    val call = RetrofitFactory()
                        .getUserService()
                        .inserir(login)

                    call.enqueue(object : Callback<Login> {
                        override fun onResponse(call: Call<Login>, response: Response<Login>) {
                            if (response.isSuccessful) {

                                android.util.Log.i("API", "Login realizado com sucesso!!: ${response.body()}")

                                navController?.navigate("TelaMyviagens")

                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Erro ao acessar login: ${response.code()}")
                                }
                                android.util.Log.e("API", "Erro ao acessar login: ${response.code()}")
                            }
                        }
                        override fun onFailure(call: Call<Login>, t: Throwable) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Erro de conexão: ${t.message}")
                            }
                            android.util.Log.e("API", "Falha na requisição: ${t.message}")
                        }
                    })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3E2723),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Login",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    navController?.navigate("CadastroScreen")
                }
            ) {
                Text(
                    text = "Cadastre-se",
                    fontSize = 14.sp,
                    color = Color(0xFF3E2723)
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TelaLoginPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}