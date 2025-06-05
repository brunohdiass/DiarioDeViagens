package com.example.diariodeviagens.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items // Importante para LazyColumn/LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import com.example.diariodeviagens.R
import com.example.diariodeviagens.screens.components.CardViagem
// Importações que você pode precisar para Retrofit (se as classes estiverem em outros arquivos)
// import com.example.diariodeviagens.model.User
// import com.example.diariodeviagens.model.Viagens
// import com.example.diariodeviagens.service.UserService
// import com.example.diariodeviagens.service.RetrofitFactory
// Para que o RetrofitFactory e UserService não deem erro no Preview, você precisa defini-los ou mocká-los
// Por simplicidade, vou deixar comentados ou assumir que estão em outros arquivos.

// --- DEFINIÇÕES DE DADOS E SERVIÇOS (PARA SIMULAÇÃO) ---
// Se você não tem essas classes/interfaces, elas precisam ser criadas
// ou o código de chamada a elas deve ser removido/mockado para o Preview.

// Exemplo de Data Class para Viagem (se você não a definiu em model/Viagens.kt)
// data class Viagens(
//     val titulo: String,
//     val descricao: String,
//     val data_inicio: String,
//     val data_fim: String,
//     val visibilidade: String,
//     val id_usuario: Int,
//     val locais: List<String>,
//     val categorias: List<String>
// )

// Exemplo de Interface de Serviço (se você não a definiu em service/UserService.kt)
// interface UserService {
//     @GET("/users")
//     suspend fun getUsers(): List<User>
// }

// Exemplo de RetrofitFactory (se você não a definiu em service/RetrofitFactory.kt)
// import retrofit2.Retrofit
// import retrofit2.converter.gson.GsonConverterFactory
// class RetrofitFactory {
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://api.example.com/") // Sua URL base da API
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    fun getUserService(): UserService {
//        return retrofit.create(UserService::class.java)
//    }
// }

// --- FIM DAS DEFINIÇÕES DE DADOS E SERVIÇOS ---


@OptIn(ExperimentalMaterial3Api::class) // Anotação necessária para TopAppBar/Scaffold
@Composable
fun TelaHome(navController1: NavHostController?) {
    val searchText = remember { mutableStateOf("") }

    // NOTA: Para que `RetrofitFactory().getUserService()` funcione,
    // você precisa ter essas classes e interfaces definidas no seu projeto.
    // Para um Preview simples, você pode remover ou mockar isso.
    // val service = RetrofitFactory().getUserService()


    Scaffold(
        bottomBar = {
            // Rodapé fixo na parte inferior da tela
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp),
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
    ) { paddingValues -> // Usando o paddingValues do Scaffold

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F0F0))
                .padding(paddingValues) // Aplicando o padding do Scaffold aqui
        ) {
            // TOPO: Área marrom com logo, menu e campo de busca
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFA86523))
            ) {
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
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo do Diário de Viagens", // Adicione um contentDescription
                        modifier = Modifier.size(75.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu", // Adicione um contentDescription
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

                // Campo de busca flutuante
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .offset(y = (-10).dp) // Ajusta a posição vertical
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
                    // Ícone de busca dentro do BasicTextField (opcional)
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Pesquisar",
                        tint = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                            .size(24.dp)
                    )
                }
            }
            // CONTEÚDO ROLÁVEL: STORIES + FEED
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(
                    top = 216.dp, // Ajusta o top padding para o conteúdo começar abaixo do topo
                    bottom = 0.dp // O padding inferior será dado pelo Scaffold
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // STORIES
                item {
                    Text(
                        text = "Stories de Viagens",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(5) { // Exemplo de 5 stories de placeholder
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo), // Assuma que você tem uma imagem de placeholder
                                    contentDescription = "Story do Usuário",
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .background(Color.Gray)
                                )
                                Text(text = "Usuário ${it + 1}", fontSize = 10.sp)
                            }
                        }
                    }
                }

                // FEED DE VIAGENS
                item {
                    Text(
                        text = "Suas Viagens",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,

                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                // Exemplo de como você chamaria o CardViagem no LazyColumn
                items(10) { index -> // Renderiza 10 CardViagens de exemplo
                    CardViagem(
                        imagemResId = if (index % 2 == 0) R.drawable.ilhabela else R.drawable.ilhabela, // Alterne entre duas imagens
                        titulo = "Minha Viagem ${index + 1}",
                        descricao = "Descobrindo novos horizontes e criando memórias inesquecíveis nesta jornada ${index + 1}.",
                        localPrincipal = "Local Exemplo ${index + 1}",
                        categoriaPrincipal = if (index % 3 == 0) "Aventura" else "Relax",
                        dataInicio = "0${index + 1}/07/2025",
                        nomeUsuario = "Viajante ${index + 1}"
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewTelaHome() {
    val navController = rememberNavController()
    TelaHome(navController) // Passe o navController real para o Preview
}