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
import com.example.diariodeviagens.model.LocalPayload // IMPORTANTE: Importe LocalPayload
import com.example.diariodeviagens.model.Midia
import com.example.diariodeviagens.service.RetrofitFactory
import com.example.diariodeviagens.service.uploadFileToAzure
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

@Composable
fun TelaNovaPublicacao(navController: NavHostController?) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val retrofit = remember { RetrofitFactory() } // Use remember para o RetrofitFactory

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
    // As variáveis 'local', 'latitude', 'longitude' individuais não serão mais usadas para o payload
    // mas podem ser úteis para exibir o texto na UI se você quiser.
    val localParaExibicao = remember { mutableStateOf("") } // Para exibir o nome do local selecionado na UI

    // ESTADO CRÍTICO: Armazena o objeto NominatimResult completo selecionado
    val selectedNominatimResult = remember { mutableStateOf<NominatimResult?>(null) }


    var imagemUri by remember { mutableStateOf<Uri?>(null) }
    var imagemBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }

    // TODO: Adicione uma variável para a lista de mídias (se estiver usando imagens/vídeos múltiplos)
    // val medias = remember { mutableStateListOf<MidiaPayload>() }

    LaunchedEffect(Unit) {
        try {
            val response = retrofit.getCategoriaService().getCategorias()

            if (response.isSuccessful) {
                response.body()?.let { apiResponse ->
                    if (apiResponse.status && apiResponse.categorias.isNotEmpty()) {
                        categorias.clear()
                        apiResponse.categorias.forEach { cat ->
                            categorias.add(cat.id to cat.nome)
                        }
                        println("✅ Categorias carregadas: ${categorias.size} itens")
                    } else {
                        println("⚠️ API de categorias retornou status falso ou lista vazia")
                    }
                }
            } else {
                println("❌ Erro na resposta da API de categorias: ${response.code()} - ${response.errorBody()?.string()}")
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
            // TODO: Se você estiver lidando com múltiplos uploads e URLs para o backend,
            // aqui você adicionaria a lógica para fazer o upload da imagem e
            // adicionar a URL e o tipo à sua lista de 'medias'.
        }
    }

    // ID fixo do usuário por enquanto (MUITO IMPORTANTE: Substitua por um ID dinâmico do usuário logado)
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

        // Campo de texto para busca de local
        BasicTextField(
            value = localQuery.value,
            onValueChange = {
                localQuery.value = it
                scope.launch {
                    if (it.isNotBlank() && it.length > 2) { // Evita buscas muito curtas
                        try {
                            val results = RetrofitFactory.NominatimApi.create().searchLocation(it)
                            resultadosLocal.clear()
                            resultadosLocal.addAll(results)
                        } catch (e: Exception) {
                            println("Erro ao buscar localização: ${e.message}")
                            // TODO: Exibir uma mensagem de erro para o usuário
                        }
                    } else {
                        resultadosLocal.clear() // Limpa resultados se a query for muito curta
                    }
                }
            },
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F2F2), RoundedCornerShape(6.dp))
                .padding(12.dp),
            decorationBox = { innerTextField ->
                if (localQuery.value.isEmpty()) {
                    Text("Pesquisar local...", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de sugestões de local
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F8F8), RoundedCornerShape(6.dp))
                .padding(4.dp)
                .heightIn(max = 200.dp) // Limita a altura para evitar que ocupe a tela inteira
        ) {
            items(resultadosLocal) { locItem -> // 'locItem' é um único NominatimResult
                Text(
                    text = locItem.display_name,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            localParaExibicao.value = locItem.display_name // Atualiza o texto exibido
                            localQuery.value = locItem.display_name // Atualiza o campo de busca para o nome completo
                            resultadosLocal.clear() // Limpa as sugestões
                            selectedNominatimResult.value = locItem // **Armazena o objeto NominatimResult completo!**
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
                    contentDescription = "Imagem selecionada",
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

        Button(
            onClick = {
                // Validações básicas do formulário
                if (titulo.value.isBlank()) {
                    println("❌ Validação: O título não pode estar vazio!")
                    return@Button
                }
                if (descricao.value.isBlank()) {
                    println("❌ Validação: A descrição não pode estar vazia!")
                    return@Button
                }
                if (dataInicio.value.isBlank() || dataFim.value.isBlank()) {
                    println("❌ Validação: As datas de início e fim são obrigatórias!")
                    return@Button
                }
                if (categoriaSelecionadaId.value == null) {
                    println("❌ Validação: Selecione uma categoria!")
                    return@Button
                }
                if (imagemUri == null) {
                    println("❌ Validação: Selecione uma imagem para a viagem!")
                    return@Button
                }

                scope.launch {
                    try {
                        // Criar local no backend e pegar o ID retornado
                        val localId: Int? = selectedNominatimResult.value?.let { nominatimResult ->
                            val localPayload = LocalPayload(
                                nome = nominatimResult.display_name,
                                latitude = nominatimResult.lat.toDouble(),
                                longitude = nominatimResult.lon.toDouble()
                            )

                            val responseLocal = retrofit.getLocalService().criarLocal(localPayload)
                            val idLocal = responseLocal.local.id
                            idLocal
                        }

                        val locaisParaBackend = localId?.let { listOf(it) } ?: emptyList()

                        // --- INÍCIO DO UPLOAD DA IMAGEM ---

                        // Abrir arquivo a partir da URI
                        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(imagemUri!!, "r")
                        if (parcelFileDescriptor == null) {
                            println("❌ Não foi possível abrir o arquivo da imagem.")
                            return@launch
                        }
                        val fileDescriptor = parcelFileDescriptor.fileDescriptor

                        val inputStream = FileInputStream(fileDescriptor)
                        val tempFile = File(context.cacheDir, "upload_temp.jpg")
                        tempFile.outputStream().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }

                        val azureImageUrl = uploadFileToAzure(
                            file = tempFile,
                            storageAccount = "diariodeviagem",
                            containerName = "fotosvideos",
                            sasToken = "sp=racwl&st=2025-06-05T12:07:14Z&se=2025-06-12T20:07:14Z&sv=2024-11-04&sr=c&sig=JoV%2BGNNaowZyXQMpkv397WpEjthe0zsW914ABZ8GEsI%3D"
                        )

                        if (azureImageUrl == null) {
                            println("❌ Falha no upload da imagem para o Azure.")
                            return@launch
                        }

                        // --- FIM DO UPLOAD ---

                        // Construir objeto da viagem para envio, incluindo midia
                        val viagemRequest = Viagens(
                            titulo = titulo.value,
                            descricao = descricao.value,
                            data_inicio = dataInicio.value,
                            data_fim = dataFim.value,
                            visibilidade = "publica",
                            id_usuario = userId,
                            categorias = listOfNotNull(categoriaSelecionadaId.value),
                            locais = locaisParaBackend,
                            midias = listOf(
                                Midia(
                                    url = azureImageUrl,
                                    tipo = "foto"
                                )
                            )
                        )

                        println("JSON da Requisição: $viagemRequest")

                        // Enviar viagem para o backend
                        val responseViagem = retrofit.getViagemService().postarViagem(viagemRequest)

                        if (responseViagem.isSuccessful) {
                            println("✅ Viagem postada com sucesso!")
                            // Limpar campos após sucesso
                            titulo.value = ""
                            descricao.value = ""
                            dataInicio.value = ""
                            dataFim.value = ""
                            tipoSelecionado.value = "Selecione uma categoria"
                            categoriaSelecionadaId.value = null
                            localQuery.value = ""
                            resultadosLocal.clear()
                            localParaExibicao.value = ""
                            selectedNominatimResult.value = null
                            imagemUri = null
                            imagemBitmap = null

                            navController?.navigate("TelaMyViagens") {
                                popUpTo("TelaMyViagens") { inclusive = true }
                            }
                        } else {
                            val errorBody = responseViagem.errorBody()?.string()
                            println("❌ Erro na resposta da API: ${responseViagem.code()} - $errorBody")
                        }

                    } catch (e: Exception) {
                        println("❌ Falha na requisição: ${e.message}")
                        e.printStackTrace()
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