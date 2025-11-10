package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import com.example.lab_week_09.ui.theme.OnBackgroundItemText
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.PrimaryTextButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Panggil Home tanpa parameter 
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
    // Definisikan state untuk daftar 
    val listData = remember {
        listOf(
            Student("Tanu"), 
            Student("Tina"), 
            Student("Tono")  
        ).toMutableStateList() // Konversi ke daftar yang bisa diubah
    }
    
    // Definisikan state untuk input field 
    val inputField = remember { mutableStateOf(Student("")) }

    // Panggil HomeContent dan teruskan state dan lambda
    HomeContent(
        listData = listData,
        inputField = inputField.value,
        onInputValueChange = { newName ->
            // Perbarui state inputField saat pengguna mengetik 
            // Ini adalah kode yang diperbaiki dari modul
            inputField.value = inputField.value.copy(name = newName)
        },
        onButtonClick = {
            // Tambahkan item baru ke daftar jika tidak kosong 
            // Ini adalah kode yang diperbaiki, sesuai dengan perbaikan di Assignment
            if (inputField.value.name.isNotBlank()) {
                listData.add(inputField.value)
                // Reset input field 
                inputField.value = Student("")
            }
        }
    )
}

@Composable
fun HomeContent(
    listData: List<Student>,  // Gunakan List agar lebih fleksibel
    inputField: Student,
    onInputValueChange: (String) -> Unit, 
    onButtonClick: () -> Unit 
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ganti Text dengan Composable baru kita 
                OnBackgroundTitleText(text = stringResource(id = R.string.enter_item))
                
                TextField(
                    value = inputField.name,  // Tampilkan state
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text 
                    ),
                    // Panggil lambda saat nilai berubah 
                    onValueChange = onInputValueChange 
                )
                
                // Ganti Button dengan Composable baru kita 
                PrimaryTextButton(
                    text = stringResource(id = R.string.button_click),
                    onClick = onButtonClick
                )
            }
        }
        
        // Render daftar dari state 
        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ganti Text dengan Composable baru kita 
                OnBackgroundItemText(text = item.name)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        Home()
    }
}

// Data class kita 
data class Student(
    var name: String
)
