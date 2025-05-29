package com.example.abordoteste.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.abordoapp.R

@Composable
fun TelaMyViagens() {
    val searchText = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F0F0)) // Fundo com cor f2f0f0
    ) {
        // TOPO (fundo marrom com logo, texto e campo de busca)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFFA86523))
        ) {
            // Logo e menu
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .height(75.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_app),
                    contentDescription = null,
                    modifier = Modifier.size(75.dp)
                )
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )
            }

            Text(
                text = "Olá, Usuário! Pronto para registrar sua próxima aventura?",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 90.dp, end = 16.dp)
            )

            // Campo de busca
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-10).dp)
                    .height(48.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color(0xFFF2F0F0), shape = RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                if (searchText.value.isEmpty()) {
                    Text(
                        text = "Selecionar destino",
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                BasicTextField(
                    value = searchText.value,
                    onValueChange = { searchText.value = it },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }

        // CONTEÚDO
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 220.dp, bottom = 70.dp)
        ) {
            Text(
                text = "MINHAS VIAGENS",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)
            )

            // Linha do tempo + cards
            Column(modifier = Modifier.fillMaxWidth()) {
                listOf(
                    "Ilha Bela" to R.drawable.ilhabela,
                    "Campos do Jordão" to R.drawable.ilhabela,
                ).forEach { (titulo, imagem) ->
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .padding(start = 0.dp, bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Place,
                            contentDescription = "Localização",
                            modifier = Modifier.size(28.dp),
                            tint = Color(color = (0xFFA86523))
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier.widthIn(max = 320.dp)

                        ) {
                            Text(
                                text = titulo,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.align(Alignment.Start)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column {
                                    Image(
                                        painter = painterResource(id = imagem),
                                        contentDescription = titulo,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(120.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Text(
                                            text = "Viagem para $titulo - Brasil",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Itens adicionais aqui, se desejar
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Rodapé
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 8.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home",
                modifier = Modifier.size(28.dp),
                tint = Color(0xFFA86523)
            )
            Icon(
                imageVector = Icons.Filled.Place,
                contentDescription = "Explorar",
                modifier = Modifier.size(28.dp),
                tint = Color(0xFFA86523)
            )
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "Adicionar",
                modifier = Modifier.size(34.dp),
                tint = Color(0xFFA86523)
            )
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Viagens",
                modifier = Modifier.size(28.dp),
                tint = Color(0xFFA86523)
            )
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Configurações",
                modifier = Modifier.size(28.dp),
                tint = Color(0xFFA86523)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewTelaMyViagens() {
    TelaMyViagens()
}
