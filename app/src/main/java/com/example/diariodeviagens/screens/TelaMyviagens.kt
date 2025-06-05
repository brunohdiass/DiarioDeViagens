package com.example.abordoteste.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import com.example.diariodeviagens.screens.components.CardViagem
import java.util.UUID // Para gerar IDs únicos

data class ViagemCardInfo(
    val id: String, // ID único para a chave do LazyColumn
    val imagemResId: Int,
    val titulo: String,
    val descricao: String,
    val localPrincipal: String,
    val categoriaPrincipal: String,
    val dataInicio: String,
    val nomeUsuario: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaMyViagens(navController1: NavHostController?) {
    var searchText by remember { mutableStateOf("") }
    var isHintVisible by remember { mutableStateOf(searchText.isEmpty()) }

    // 1. Tornar a lista de viagens um estado mutável
    val listaDeViagens = remember {
        mutableStateListOf( // Usar mutableStateListOf para observabilidade
            ViagemCardInfo(UUID.randomUUID().toString(), R.drawable.logo_app, "Viagem de Negócios 1", "Conferência importante em São Paulo.", "São Paulo, SP", "Negócios", "10/06/2025", "João Cliente"),
            ViagemCardInfo(UUID.randomUUID().toString(), R.drawable.logo_app, "Viagem de Férias", "Relaxando na praia ensolarada.", "Praia do Forte, BA", "Lazer", "15/07/2025", "Maria Turista"),
            ViagemCardInfo(UUID.randomUUID().toString(), R.drawable.logo_app, "Aventura na Montanha", "Explorando trilhas e paisagens.", "Serra da Canastra, MG", "Aventura", "20/08/2025", "Carlos Aventureiro"),
            ViagemCardInfo(UUID.randomUUID().toString(), R.drawable.logo_app, "Exploração Urbana", "Descobrindo os segredos da cidade.", "Curitiba, PR", "Turismo", "10/09/2025", "Ana Viajante")
        )
    }
    var cardCounter by remember { mutableStateOf(listaDeViagens.size + 1) } // Contador para novos cards

    val topBarBaseHeight = 200.dp
    val searchBarOffsetFromBase = 10.dp // Quanto a barra de busca "desce" da base marrom
    val searchBarHeight = 48.dp
    val spaceAfterSearchBar = 16.dp

    // Altura total que o topBar customizado ocupará visualmente ANTES do conteúdo principal
    // O final da área marrom é topBarBaseHeight.
    // O topo da busca começa em: topBarBaseHeight + searchBarOffsetFromBase
    // O fim da busca é em: topBarBaseHeight + searchBarOffsetFromBase + searchBarHeight
    // O espaço total do topBar é: topBarBaseHeight + searchBarOffsetFromBase + searchBarHeight + spaceAfterSearchBar
    // No entanto, o Scaffold vai usar a altura do Composable que passarmos.
    // Vamos calcular a altura do Box que contém a área marrom E a busca flutuante
    val totalTopAreaHeight = topBarBaseHeight + searchBarOffsetFromBase + searchBarHeight + spaceAfterSearchBar


    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(totalTopAreaHeight) // Altura total para o topBar
                    .background(Color(0xFFF2F0F0)) // Fundo para a área de espaçamento
            ) {
                // Parte marrom do topo
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

                // Campo de busca posicionado com offset para "flutuar"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(searchBarHeight)
                        .align(Alignment.TopStart) // Alinhar ao topo do Box do topBar
                        // O y do offset é a posição final da área marrom + o quanto queremos que a busca desça
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

                    // 2. Botão para adicionar cards
                    IconButton(
                        onClick = {
                            val novoId = UUID.randomUUID().toString()
                            val novoCard = ViagemCardInfo(
                                id = novoId,
                                imagemResId = R.drawable.logo_app, // Imagem genérica
                                titulo = "Nova Viagem $cardCounter",
                                descricao = "Detalhes da nova viagem $cardCounter.",
                                localPrincipal = "Novo Local $cardCounter",
                                categoriaPrincipal = "Aventura",
                                dataInicio = "01/01/2026",
                                nomeUsuario = "Usuário"
                            )
                            listaDeViagens.add(novoCard) // Adiciona ao final da lista
                            cardCounter++
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "MINHAS VIAGENS",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(items = listaDeViagens, key = { viagem -> viagem.id }) { viagemInfo ->
                Row(verticalAlignment = Alignment.Top) {
                    Icon(Icons.Filled.Place, "Localização", modifier = Modifier.size(28.dp).padding(top = 4.dp), tint = Color(0xFFA86523))
                    Spacer(modifier = Modifier.width(12.dp))
                    CardViagem(
                        imageUrl = viagemInfo,
                        titulo = viagemInfo.titulo,
                        descricao = viagemInfo.descricao,
                        localPrincipal = viagemInfo.localPrincipal,
                        categoriaPrincipal = viagemInfo.categoriaPrincipal,
                        dataInicio = viagemInfo.dataInicio,
                        nomeUsuario = viagemInfo.nomeUsuario
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewTelaMyViagens() {
    TelaMyViagens(navController1 = rememberNavController())
}