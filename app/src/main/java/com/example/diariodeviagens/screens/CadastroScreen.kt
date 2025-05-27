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
import androidx.compose.material3.OutlinedTextFieldDefaults
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
fun CadastroInputTransparente(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Um pouco mais alto que 48.dp para comportar o texto e o label
            .background(Color.Transparent),
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun CadastroScreen(navController: NavHostController?) {

    var nome_completo = remember { mutableStateOf("") }
    var username = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var senha = remember { mutableStateOf("") }
    var confirmarSenha = remember { mutableStateOf("") } // Campo adicional para confirmação
    var palavra_chave = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.telafundo1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x66000000))
        )

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cadastre-se no Abordo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = Color(0xFF3E2723),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                CadastroInputTransparente(
                    value = nome_completo.value,
                    onValueChange = { nome_completo.value = it },
                    label = "Nome Completo"
                )
                Spacer(modifier = Modifier.height(12.dp))

                CadastroInputTransparente(
                    value = username.value,
                    onValueChange = { username.value = it },
                    label = "Username"
                )
                Spacer(modifier = Modifier.height(12.dp))

                CadastroInputTransparente(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = "Email"
                )
                Spacer(modifier = Modifier.height(12.dp))

                CadastroInputTransparente(
                    value = senha.value,
                    onValueChange = { senha.value = it },
                    label = "Nova Senha"
                )
                Spacer(modifier = Modifier.height(12.dp))

                CadastroInputTransparente(
                    value = confirmarSenha.value,
                    onValueChange = { confirmarSenha.value = it },
                    label = "Confirme a Senha"
                )
                Spacer(modifier = Modifier.height(12.dp))

                CadastroInputTransparente(
                    value = palavra_chave.value,
                    onValueChange = { palavra_chave.value = it },
                    label = "Palavra-chave"
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val user = User(
                            nome = nome_completo.value,
                            username = username.value,
                            email = email.value,
                            senha = senha.value,
                            palavra_chave = palavra_chave.value
                        )
                        val call = RetrofitFactory().getUserService().insert(user)

                        call.enqueue(object : retrofit2.Callback<User> {
                            override fun onResponse(
                                call: retrofit2.Call<User>, response: retrofit2.Response<User>
                            ) {
                                if (response.isSuccessful) {
                                    Log.i("API", "Usuário cadastrado com sucesso: ${response.body()}")
                                } else {
                                    Log.e("API", "Erro ao cadastrar: ${response.code()}")
                                }
                            }

                            override fun onFailure(call: retrofit2.Call<User>, t: Throwable) {
                                Log.e("API", "Falha na requisição: ${t.message}")
                            }
                        })
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
                        text = "Sign Up", fontWeight = FontWeight.Bold, fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(
                    onClick = {
                        navController?.navigate("LoginScreen")
                    },
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Já tem login? Logar",
                        fontSize = 15.sp,
                        color = Color(0xFF3E2723),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}






@Preview(showSystemUi = true)
@Composable
private fun TelaCadastroPreview() {
    val navController = rememberNavController()
    CadastroScreen(navController = navController)
}