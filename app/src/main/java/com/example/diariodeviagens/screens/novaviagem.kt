package com.example.diariodeviagens.screens

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.diariodeviagens.model.Categoria
import com.example.diariodeviagens.model.Viagens
import com.example.diariodeviagens.service.RetrofitFactory
import kotlinx.coroutines.launch
import java.io.InputStream

@Composable
fun TelaNovaPublicacao(navController: NavHostController?) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val titulo = remember { mutableStateOf("") }
    val descricao = remember { mutableStateOf("") }
    val dataInicio = remember { mutableStateOf("") }
    val dataFim = remember { mutableStateOf("") }

    var imagemUri by remember { mutableStateOf<Uri?>(null) }
    var imagemBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imagemUri = uri
        uri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(it)
            imagemBitmap = BitmapFactory.decodeStream(inputStream)
        }
    }

    // ID fixo do usuário por enquanto
    val userId = 1

    // Categoria dinâmica da API
    val retrofit = RetrofitFactory()
    val categoriaService = retrofit.getCategoriaService()

    var categorias by remember { mutableStateOf(listOf<Categoria>()) }
    var categoriaSelecionada by remember { mutableStateOf<Categoria?>(null) }
    val expanded = remember { mutableStateOf(false) }
    val tipoSelecionado = remember { mutableStateOf("Selecione uma categoria") }

    LaunchedEffect(Unit) {
        try {
            val response = categoriaService.listarCategorias()
            if (response.isSuccessful) {
                categorias = response.body() ?: emptyList()
            } else {
                println("❌ Erro ao carregar categorias: ${response.code()}")
            }
        } catch (e: Exception) {
            println("❌ Erro de rede: ${e.message}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Cabeçalho
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Nova Publicação", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Icon(Icons.Default.Close, contentDescription = "Fechar", modifier = Modifier.clickable {
                navController?.popBackStack()
            })
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // Imagem
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imagemBitmap != null) {
                Image(
                    bitmap = imagemBitmap!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text("Selecionar Imagem", color = Color.DarkGray)
            }
        }

        Spacer(Modifier.height(12.dp))

        // Título
        Text("Título da viagem", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        BasicTextField(
            value = titulo.value,
            onValueChange = { titulo.value = it },
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F2F2), RoundedCornerShape(6.dp))
                .padding(12.dp)
        )

        Spacer(Modifier.height(12.dp))

        // Descrição
        Text("Descrição", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        BasicTextField(
            value = descricao.value,
            onValueChange = { descricao.value = it },
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFFF2F2F2), RoundedCornerShape(6.dp))
                .padding(12.dp)
        )

        Spacer(Modifier.height(12.dp))

        // Data início
        Text("Data de Início (yyyy-MM-dd)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        BasicTextField(
            value = dataInicio.value,
            onValueChange = { dataInicio.value = it },
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .background(Color(0xFFF2F2F2), RoundedCornerShape(6.dp))
                .padding(12.dp)
        )

        Spacer(Modifier.height(12.dp))

        // Data fim
        Text("Data de Fim (yyyy-MM-dd)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        BasicTextField(
            value = dataFim.value,
            onValueChange = { dataFim.value = it },
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .background(Color(0xFFF2F2F2), RoundedCornerShape(6.dp))
                .padding(12.dp)
        )

        Spacer(Modifier.height(12.dp))

        // Categoria (Dropdown)
        Text("Categoria", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Button(
            onClick = { expanded.value = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA86523)),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(tipoSelecionado.value, color = Color.White)
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            categorias.forEach { categoria ->
                DropdownMenuItem(
                    text = { Text(categoria.nome_categoria) },
                    onClick = {
                        tipoSelecionado.value = categoria.nome_categoria
                        categoriaSelecionada = categoria
                        expanded.value = false
                    }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Botão compartilhar
        Button(
            onClick = {
                val viagemRequest = Viagens(
                    titulo = titulo.value,
                    descricao = descricao.value,
                    data_inicio = dataInicio.value,
                    data_fim = dataFim.value,
                    visibilidade = "publica",
                    id_usuario = userId,
                    locais = listOf(),
                    categorias = categoriaSelecionada?.let { listOf(it.id) } ?: listOf()
                )

                scope.launch {
                    try {
                        val response = retrofit.getViagemService().postarViagem(viagemRequest)
                        if (response.isSuccessful) {
                            println("✅ Viagem postada com sucesso!")
                            navController?.navigate("telaListaViagens") // ajuste para sua rota real
                        } else {
                            println("❌ Erro na resposta: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        println("❌ Falha ao postar viagem: ${e.message}")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA86523))
        ) {
            Text("Compartilhar", color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNovaPublicacao() {
    val navController = rememberNavController()
    TelaNovaPublicacao(navController = navController)
}
