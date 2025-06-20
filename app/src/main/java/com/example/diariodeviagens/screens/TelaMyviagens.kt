package com.example.abordoteste.screen

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
import com.example.diariodeviagens.model.Viagens
import com.example.diariodeviagens.screens.components.CardViagem
import com.example.diariodeviagens.service.RetrofitFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaMyViagens(navController: NavHostController?) {
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
                errorMessage = null
                val service = RetrofitFactory().getViagemService()
                val apiResponse = service.listarViagem()

                if (apiResponse.status) {
                    viagensApi = apiResponse.viagem ?: emptyList()
                } else {
                    viagensApi = emptyList()
                    errorMessage = "Erro na API: ${apiResponse.status_code} - ${apiResponse.status}"
                }
            } catch (e: Exception) {
                errorMessage = "Erro ao carregar viagens: ${e.message}"
                Log.e("erro:", "Erro ao carregar viagens: ${e.message}", e)
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
                        text = "Olá, Lara! Pronto para registrar sua próxima aventura?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 16.dp, top = 80.dp, end = 16.dp)
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
                        onValueChange = {
                            searchText = it
                            isHintVisible = it.isEmpty()
                        },
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
                    IconButton(onClick = { navController?.navigate("home") }) {
                        Icon(Icons.Filled.Home, "Home", modifier = Modifier.size(28.dp))
                    }
                    IconButton(onClick = { navController?.navigate("explorar") }) {
                        Icon(Icons.Filled.AirplanemodeActive, "Explorar", modifier = Modifier.size(28.dp))
                    }
                    IconButton(onClick = { navController?.navigate("novaviagem") }) {
                        Icon(
                            Icons.Filled.AddCircle,
                            "Adicionar Viagem",
                            modifier = Modifier.size(34.dp)
                        )
                    }
                    IconButton(onClick = { navController?.navigate("viagensSalvas") }) {
                        Icon(Icons.Filled.Star, "Viagens Salvas", modifier = Modifier.size(28.dp))
                    }
                    IconButton(onClick = { navController?.navigate("perfil") }) {
                        Icon(Icons.Filled.AccountCircle, "Perfil", modifier = Modifier.size(28.dp))
                    }
                }
            }
        },
        containerColor = Color(0xFFF2F0F0)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "MINHAS VIAGENS",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color(0xFFA86523), // Brown color from your palette
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )

            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 32.dp)
                    )
                }
                errorMessage != null -> {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 32.dp)
                    )
                }
                viagensApi.isEmpty() -> {
                    Text(
                        text = "Nenhuma viagem encontrada. Comece a planejar sua próxima aventura!",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 32.dp)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(items = viagensApi, key = { viagem -> viagem.id }) { viagem ->
                            CardViagem(viagem = viagem)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewTelaMyViagens() {
    TelaMyViagens(navController = rememberNavController())
}