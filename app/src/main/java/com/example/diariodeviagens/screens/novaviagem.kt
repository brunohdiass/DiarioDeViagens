
package com.example.diariodeviagens.screens
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.diariodeviagens.model.NominatimResult
import com.example.diariodeviagens.model.Viagens
import com.example.diariodeviagens.service.RetrofitFactory
import kotlinx.coroutines.launch
import java.io.InputStream

@Composable
fun TelaNovaPublicacao(navController: NavHostController?) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val retrofit = RetrofitFactory()

    val titulo = remember { mutableStateOf("") }
    val descricao = remember { mutableStateOf("") }
    val dataInicio = remember { mutableStateOf("") }
    val dataFim = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    val tipoSelecionado = remember { mutableStateOf("Selecione uma categoria") }
    val categorias = remember { mutableStateListOf<Pair<Int, String>>() }
    val categoriaSelecionadaId = remember { mutableStateOf<Int?>(null) }

    val localQuery = remember { mutableStateOf("") }
    val resultadosLocal = remember { mutableStateListOf<NominatimResult>() }
    val nome = remember { mutableStateOf("") }
    val latitude = remember { mutableStateOf("") }
    val longitude = remember { mutableStateOf("") }




    var imagemUri by remember { mutableStateOf<Uri?>(null) }
    var imagemBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }

    LaunchedEffect(Unit) {
        try {
            val response = retrofit.getCategoriaService().getCategorias()

            if (response.isSuccessful) {
                response.body()?.let { apiResponse ->
                    // Verifica se há categorias na resposta
                    if (apiResponse.status && apiResponse.categorias.isNotEmpty()) {
                        categorias.clear()
                        apiResponse.categorias.forEach { cat ->
                            categorias.add(cat.id to cat.nome)
                        }
                        println("✅ Categorias carregadas: ${categorias.size} itens")
                    } else {
                        println("⚠️ API retornou status falso ou lista vazia")
                    }
                }
            } else {
                println("❌ Erro na resposta: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            println("💥 Erro ao carregar categorias: ${e.message}")
            e.printStackTrace()
        }
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imagemUri = uri
        uri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(it)
            imagemBitmap = BitmapFactory.decodeStream(inputStream)
        }
    }

    // ID fixo do usuário por enquanto
    val userId = 3

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

        Text("Localização", fontWeight = FontWeight.Bold, fontSize = 14.sp)

        BasicTextField(
            value = localQuery.value,
            onValueChange = {
                localQuery.value = it
                scope.launch {
                    try {
                        val results = RetrofitFactory.NominatimApi.create().searchLocation(it)
                        resultadosLocal.clear()
                        resultadosLocal.addAll(results)
                    } catch (e: Exception) {
                        println("Erro ao buscar localização: ${e.message}")
                    }
                }
            },
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F2F2), RoundedCornerShape(6.dp))
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

// Lista de sugestões de local
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F8F8), RoundedCornerShape(6.dp))
                .padding(4.dp)
        ) {
            items(resultadosLocal) { locais ->
                Text(
                    text = locais.display_name,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            nome.value = locais.display_name
                            latitude.value = locais.lat
                            longitude.value = locais.lon
                            localQuery.value = locais.display_name
                            resultadosLocal.clear()
                        }
                        .padding(8.dp)
                )
            }
        }


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

        // Dropdown de Categorias
        Text("Categoria", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopStart)
        ) {
            OutlinedButton(
                onClick = { expanded.value = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F2F2), RoundedCornerShape(6.dp)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Text(
                    text = tipoSelecionado.value,
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontSize = 14.sp)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Abrir menu de categorias"
                )
            }

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                when {
                    categorias.isEmpty() -> {
                        DropdownMenuItem(
                            text = { Text("Nenhuma categoria disponível") },
                            onClick = {}
                        )
                    }
                    else -> {
                        categorias.forEach { (id, nome) ->
                            DropdownMenuItem(
                                text = { Text(nome) },
                                onClick = {
                                    tipoSelecionado.value = nome
                                    categoriaSelecionadaId.value = id
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
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
                    categorias = listOfNotNull(categoriaSelecionadaId.value),
                    nome = nome.value
                )

                scope.launch {
                    try {
                        val response = retrofit.getViagemService().postarViagem(viagemRequest)
                        if (response.isSuccessful) {
                            println("✅ Viagem postada com sucesso!")
                            navController?.navigate("TelaMyViagens")
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