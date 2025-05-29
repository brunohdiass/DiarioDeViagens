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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.InputStream

@Composable
fun TelaNovaPublicacao() {
    val context = LocalContext.current
    val titulo = remember { mutableStateOf("") }
    val legenda = remember { mutableStateOf("") }
    val tipoSelecionado = remember { mutableStateOf("Tipos de viagem") }
    val tiposViagem = listOf("Aventura", "Cultural", "Romântica", "Familiar")
    val expanded = remember { mutableStateOf(false) }

    var imagemUri by remember { mutableStateOf<Uri?>(null) }
    var imagemBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagemUri = uri
        uri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(it)
            imagemBitmap = BitmapFactory.decodeStream(inputStream)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // TOPO
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Nova Publicação", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Icon(Icons.Default.Close, contentDescription = "Fechar")
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // IMAGEM/VIDEO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
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
                Text("Selecionar Imagem/Vídeo", color = Color.DarkGray)
            }
        }

        Spacer(Modifier.height(6.dp))
        Text("1/3", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(Modifier.height(8.dp))

        // TÍTULO
        Text("Adicionar título", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        BasicTextField(
            value = titulo.value,
            onValueChange = { titulo.value = it },
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(6.dp))
                .padding(12.dp)
        )

        Spacer(Modifier.height(8.dp))

        // LEGENDA
        Text("Adicionar legenda", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        BasicTextField(
            value = legenda.value,
            onValueChange = { legenda.value = it },
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(6.dp))
                .padding(12.dp)
        )

        Spacer(Modifier.height(6.dp))
        Text("00/00/0000", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.align(Alignment.End))

        Spacer(Modifier.height(12.dp))
        Divider()
        Spacer(Modifier.height(12.dp))

        // LOCALIZAÇÃO
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Place, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Adicionar localização", fontSize = 14.sp)
        }

        Spacer(Modifier.height(12.dp))

        // DROPDOWN TIPO DE VIAGEM
        Column {
            Text("Tipo de Viagem", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(Modifier.height(6.dp))
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
                tiposViagem.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo) },
                        onClick = {
                            tipoSelecionado.value = tipo
                            expanded.value = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // USUÁRIO
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(36.dp), tint = Color.Gray)
            Spacer(Modifier.width(8.dp))
            Text("lara.horacio123", fontWeight = FontWeight.Medium)
        }

        Spacer(Modifier.height(16.dp))

        // BOTÃO COMPARTILHAR
        Button(
            onClick = { /* ação de compartilhar */ },
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
    TelaNovaPublicacao()
}
