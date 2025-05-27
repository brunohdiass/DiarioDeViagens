package com.example.diariodeviagens.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diariodeviagens.R

@Composable
fun CadastroScreen() {
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

                CadastroInputTransparente("Nome Completo:")
                Spacer(modifier = Modifier.height(12.dp))

                CadastroInputTransparente("Username:")
                Spacer(modifier = Modifier.height(12.dp))

                CadastroInputTransparente("Email")
                Spacer(modifier = Modifier.height(12.dp))

                CadastroInputTransparente("Nova Senha:")
                Spacer(modifier = Modifier.height(12.dp))

                CadastroInputTransparente("Confirme a senha:")
                Spacer(modifier = Modifier.height(12.dp))

                CadastroInputTransparente("Palavra-chave")
                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Color(0xFF3E2723), RoundedCornerShape(30.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign Up",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    buildAnnotatedString {
                        append("Já tem login? ")
                        withStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
                            append("Logar")
                        }
                    },
                    fontSize = 16.sp,
                    color = Color(0xFF3E2723),
                )
            }
        }
    }
}

@Composable
fun CadastroInputTransparente(label: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color(0x33FFFFFF), RoundedCornerShape(8.dp)) // Branco translúcido
            .padding(start = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = label, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun CadastroScreenPreview() {
    CadastroScreen()
}
