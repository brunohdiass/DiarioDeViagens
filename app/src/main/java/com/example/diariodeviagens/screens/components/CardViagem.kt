//package com.example.diariodeviagens.screens.components
//
//importandroidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.BookmarkBorder
//import androidx.compose.material.icons.filled.ChatBubbleOutline
//import androidx.compose.material.icons.filled.FavoriteBorder
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//import com.example.diariodeviagens.R
//
//
//@Composable
//fun CardViagem(
//    imageUrl: String?, // ID do recurso da imagem (ex: R.drawable.sua_imagem)
//    titulo: String = "Título da Viagem",
//    descricao: String = "Uma breve descrição da sua incrível aventura.",
//    localPrincipal: String = "Local Desconhecido", // O primeiro local da lista
//    categoriaPrincipal: String = "Geral", // A primeira categoria da lista
//    dataInicio: String = "dd/mm/aaaa",
//    nomeUsuario: String = "Nome do Usuário",
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(modifier = Modifier.fillMaxWidth()) {
//
//            // Imagem com etiqueta de local
//            Box {
//                AsyncImage( // Use AsyncImage da Coil ou similar
//                    model = imageUrl ?: R.drawable.ilhabela, // Forneça uma imagem de placeholder se a URL for nula
//                    contentDescription = "Imagem da viagem: $titulo",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp),
//                    contentScale = ContentScale.Crop
//                )
//
//                Box(
//                    modifier = Modifier
//                        .padding(12.dp)
//                        .background(Color(0xAAFFFFFF), shape = RoundedCornerShape(50))
//                        .padding(horizontal = 10.dp, vertical = 4.dp)
//                        .align(Alignment.TopStart)
//                ) {
//                    Text(
//                        text = localPrincipal,
//                        fontSize = 12.sp,
//                        color = Color(0xFF5E4B4B)
//                    )
//                }
//            }
//
//            // Título e descrição
//            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
//                Text(
//                    text = titulo,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp,
//                    color = Color.Black
//                )
//                Text(
//                    text = descricao,
//                    fontSize = 14.sp,
//                    color = Color.DarkGray,
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//
//            // Rodapé com avatar, nome, data, categoria e ícones
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 8.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                // Avatar e nome
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Box(
//                        modifier = Modifier
//                            .size(28.dp)
//                            .background(Color.LightGray, CircleShape)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Column {
//                        Text(text = nomeUsuario, fontSize = 12.sp, color = Color.Black)
//                        Text(
//                            text = "$categoriaPrincipal • $dataInicio",
//                            fontSize = 10.sp,
//                            color = Color.Gray
//                        )
//                    }
//                }
//
//                // Ícones
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Default.FavoriteBorder,
//                        contentDescription = "Curtir",
//                        modifier = Modifier.size(18.dp),
//                        tint = Color.Gray
//                    )
//                    Spacer(modifier = Modifier.width(12.dp))
//                    Icon(
//                        imageVector = Icons.Default.BookmarkBorder,
//                        contentDescription = "Salvar",
//                        modifier = Modifier.size(18.dp),
//                        tint = Color.Gray
//                    )
//                    Spacer(modifier = Modifier.width(12.dp))
//                    Icon(
//                        imageVector = Icons.Default.ChatBubbleOutline,
//                        contentDescription = "Comentar",
//                        modifier = Modifier.size(18.dp),
//                        tint = Color.Gray
//                    )
//                }
//            }
//        }
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//private fun ViagemCardPreview() {
//    CardViagem()
//}
