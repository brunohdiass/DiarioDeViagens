package com.example.diariodeviagens.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.diariodeviagens.R
import com.example.diariodeviagens.model.Midia // Certifique-se de que é o Midia atualizado
import com.example.diariodeviagens.model.Viagens


@Composable
fun CardViagem(
    viagem: Viagens
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // Imagem com etiqueta de local
            Box {
                AsyncImage(
                    model = viagem.midias?.firstOrNull()?.url ?: R.drawable.ilhabela, // Acessa a URL da primeira mídia, ou usa um placeholder
                    contentDescription = "Imagem da viagem: ${viagem.titulo}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color(0xAAFFFFFF), shape = RoundedCornerShape(50))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                        .align(Alignment.TopStart)
                ) {
                    // Acessa o nome do local da primeira localização na lista
                    Text(
                        text = viagem.locais?.firstOrNull()?.nome ?: "Local Desconhecido",
                        fontSize = 12.sp,
                        color = Color(0xFF5E4B4B)
                    )
                }
            }

            // Título e descrição
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = viagem.titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = viagem.descricao,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Rodapé com avatar, nome, data, categoria e ícones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Avatar e nome
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color.LightGray, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        // Acessa o nome do primeiro usuário na lista
                        Text(
                            text = viagem.usuario?.firstOrNull()?.nome ?: "Usuário Desconhecido",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                        // Acessa o nome da primeira categoria na lista
                        Text(
                            text = "${viagem.categorias?.firstOrNull()?.nome_categoria ?: "Categoria"} • ${viagem.data_inicio.split("T").first()}", // Pega só a data antes do 'T'
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Ícones
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Curtir",
                        modifier = Modifier.size(18.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = "Salvar",
                        modifier = Modifier.size(18.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Comentar",
                        modifier = Modifier.size(18.dp),
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ViagemCardPreview() {
    // Crie instâncias de exemplo para os novos data classes aninhados
    val sampleUser = com.example.diariodeviagens.model.UsuarioAPI(
        id = 1,
        nome = "Ana Preview",
        username = "ana_preview",
        email = "ana@example.com",
        senha = "senha123",
        biografia = "Gosta de viajar.",
        data_conta = "2025-01-01T00:00:00.000Z",
        palavra_chave = "palavra",
        foto_perfil = null
    )

    val sampleLocal = com.example.diariodeviagens.model.LocalAPI(
        id = 1,
        nome = "Paris, França",
        latitude = "48.8566",
        longitude = "2.3522",
        pais = "França",
        estado = null,
        cidade = "Paris"
    )

    val sampleCategory = com.example.diariodeviagens.model.CategoriaAPI(
        id = 1,
        nome_categoria = "Cultura"
    )

    val sampleMedia = com.example.diariodeviagens.model.Midia(
        id = 1,
        tipo = "foto",
        url = "https://images.unsplash.com/photo-1502602898668-a006c6114227?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        id_viagem = 123
    )

    val sampleViagem = Viagens(
        id = 123,
        titulo = "Viagem dos Sonhos a Paris",
        descricao = "Explorando a cidade luz, suas artes e culinária. Uma experiência inesquecível!",
        data_inicio = "2025-06-10T00:00:00.000Z",
        data_fim = "2025-06-17T00:00:00.000Z",
        visibilidade = "publica",
        data_criacao = "2025-06-05T15:30:00.000Z",
        usuario = listOf(sampleUser),
        locais = listOf(sampleLocal),
        categorias = listOf(sampleCategory),
        midias = listOf(sampleMedia)
    )
    CardViagem(viagem = sampleViagem)
}