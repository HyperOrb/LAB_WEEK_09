package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

// Aktivitas sekarang mewarisi dari ComponentActivity [cite: 60]
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Kita menggunakan setContent, bukan setContentView [cite: 64]
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(), // [cite: 70]
                    color = MaterialTheme.colorScheme.background // [cite: 73]
                ) {
                    // Siapkan daftar statis untuk Bagian 1 [cite: 212]
                    val list = listOf("Tanu", "Tina", "Tono")
                    // Panggil Composable Home kita dengan daftar tersebut [cite: 213]
                    Home(items = list)
                }
            }
        }
    }
}

// Ini adalah "Composable" kita, setara dengan file layout [cite: 82, 87]
@Composable
fun Home(items: List<String>) { // [cite: 128, 130]
    // LazyColumn lebih efisien untuk daftar, seperti RecyclerView [cite: 131, 133]
    LazyColumn {
        // 'item' digunakan untuk satu item (header kita) [cite: 137]
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp) // [cite: 146]
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally // [cite: 155]
            ) {
                Text(text = stringResource(id = R.string.enter_item)) // [cite: 156]
                
                // TextField adalah untuk input teks [cite: 159, 220]
                TextField(
                    value = "", // [cite: 160]
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text // Modul mengatakan Number[cite: 163], tapi kita masukkan nama
                    ),
                    onValueChange = { /* Biarkan kosong untuk saat ini */ } // [cite: 167]
                )
                
                // Button untuk aksi klik [cite: 170]
                Button(onClick = { /* Biarkan kosong untuk saat ini */ }) { // [cite: 173]
                    Text(text = stringResource(id = R.string.button_click)) // [cite: 175]
                }
            }
        }
        
        // 'items' digunakan untuk me-render daftar [cite: 181, 184]
        items(items) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item) // [cite: 188]
            }
        }
    }
}

// Fungsi @Preview ini untuk menampilkan pratinjau di Android Studio [cite: 195, 198]
@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        // Kita meneruskan data palsu ke preview [cite: 202]
        Home(items = listOf("Tanu", "Tina", "Tono"))
    }
}