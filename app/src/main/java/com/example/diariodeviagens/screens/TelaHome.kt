package com.example.diariodeviagens.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.diariodeviagens.R
import com.example.diariodeviagens.model.Viagens // Importe o data class Viagens
import com.example.diariodeviagens.screens.components.CardViagem // Importe o CardViagem
import com.example.diariodeviagens.service.RetrofitFactory
import kotlinx.coroutines.launch // Para usar coroutines


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHome(navController1: NavHostController?) { // Renomeado de TelaMyViagens para TelaHome
    var searchText by remember { mutableStateOf("") }
    var isHintVisible by remember { mutableStateOf(searchText.isEmpty()) }

    // Estado para armazenar a lista de viagens da API
    var viagensApi by remember { mutableStateOf<List<Viagens>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Usar rememberCoroutineScope para lançar coroutines
    val coroutineScope = rememberCoroutineScope()

    // Efeito colateral para buscar dados da API quando o componente for composto
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                isLoading = true
                errorMessage = null // Limpa erros anteriores
                val service = RetrofitFactory().getViagemService()
                // Aqui, assumimos que `listarViagem()` retorna um objeto que tem `status` e `viagem`
                // similar ao `ViagensResponse` que você parece estar usando.
                val apiResponse = service.listarViagem()

                if (apiResponse.status) { // Verifica o status dentro do ViagensResponse
                    viagensApi = apiResponse.viagem ?: emptyList() // Acessa a lista 'viagem'
                } else {
                    viagensApi = emptyList()
                    errorMessage = "Erro na API: ${apiResponse.status_code} - ${apiResponse.status}"
                }
            } catch (e: Exception) {
                errorMessage = "Erro ao carregar viagens: ${e.message}"
                Log.e("TelaHome", "Erro ao carregar viagens: ${e.message}", e) // Alterado o tag do Log para TelaHome
            } finally {
                isLoading = false
            }
        }
    }


    val topBarBaseHeight = 200.dp
    val searchBarOffsetFromBase = 10.dp
    val searchBarHeight = 48.dp
    val spaceAfterSearchBar = 16.dp

    val totalTopAreaHeight = topBarBaseHeight + searchBarOffsetFromBase + searchBarHeight + spaceAfterSearchBar


    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(totalTopAreaHeight)
                    .background(Color(0xFFF2F0F0))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(topBarBaseHeight)
                        .background(Color(0xFFA86523))
                        .align(Alignment.TopCenter)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .height(60.dp)
                            .align(Alignment.TopCenter),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo do Aplicativo",
                            modifier = Modifier.size(60.dp)
                        )
                        IconButton(onClick = { /* TODO: Ação do menu */ }) {
                            Icon(Icons.Filled.Menu, "Menu", tint = Color.White, modifier = Modifier.size(36.dp))
                        }
                    }
                    Text(
                        text = "Olá, Usuário! Pronto para registrar sua próxima aventura?",
                        fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White,
                        modifier = Modifier.align(Alignment.TopStart).padding(start = 16.dp, top = 80.dp, end = 16.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(searchBarHeight)
                        .align(Alignment.TopStart)
                        .offset(y = topBarBaseHeight + searchBarOffsetFromBase)
                        .background(Color.White, shape = RoundedCornerShape(24.dp))
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = searchText,
                        onValueChange = { searchText = it; isHintVisible = it.isEmpty() },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (isHintVisible) {
                        Text("Selecionar destino", fontSize = 16.sp, color = Color.DarkGray)
                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentColor = Color(0xFFA86523),
                modifier = Modifier.height(56.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* TODO: Ação Home */ }) {
                        Icon(Icons.Filled.Home, "Home", modifier = Modifier.size(28.dp))
                    }
                    IconButton(onClick = { /* TODO: Ação Explorar */ }) {
                        Icon(Icons.Filled.Place, "Explorar", modifier = Modifier.size(28.dp))
                    }

                    // Botão de adicionar Viagem - Agora faria uma POST request (se fosse o caso)
                    // Para este exemplo, não vamos integrar o POST aqui para manter o foco no GET e no LazyColumn
                    IconButton(
                        onClick = {
                            // TODO: Implementar a lógica para adicionar uma nova viagem via POST para a API
                            // Isso envolveria um novo Composable para o formulário de criação de viagem
                            // e uma chamada a `RetrofitFactory().getViagemService().postarViagem()`
                        }
                    ) {
                        Icon(Icons.Filled.AddCircle, "Adicionar Viagem", modifier = Modifier.size(34.dp))
                    }

                    IconButton(onClick = { /* TODO: Ação Viagens Salvas */ }) {
                        Icon(Icons.Filled.Star, "Viagens Salvas", modifier = Modifier.size(28.dp))
                    }
                    IconButton(onClick = { /* TODO: Ação Configurações/Perfil */ }) {
                        Icon(Icons.Filled.AccountCircle, "Perfil", modifier = Modifier.size(28.dp))
                    }
                }
            }
        },
        containerColor = Color(0xFFF2F0F0)
    ) { innerPadding ->
        Column(
            // Aplica o innerPadding a todo o Column
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "HOME", // Mantido o texto original para ser uma cópia exata
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )

            // Indicadores de estado da API
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 32.dp)
                )
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 32.dp)
                )
            } else if (viagensApi.isEmpty()) {
                Text(
                    text = "Nenhuma viagem encontrada. Comece a planejar sua próxima aventura!",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 32.dp)
                )
            } else {
                // LazyColumn para exibir as viagens da API
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items = viagensApi, key = { viagem -> viagem.id }) { viagem -> // Usando o ID da viagem como chave
                        // Presume que CardViagem tem um construtor que aceita um objeto Viagens diretamente
                        CardViagem(viagem = viagem)
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewTelaHome() { // Renomeado de PreviewTelaMyViagens para PreviewTelaHome
    TelaHome(navController1 = rememberNavController())
}