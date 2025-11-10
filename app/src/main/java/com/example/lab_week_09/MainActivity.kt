package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                    // 1. Buat NavController 
                    val navController = rememberNavController()
                    // 2. Panggil App Composable kita 
                    App(navController = navController)
                }
            }
        }
    }
}

// Composable root baru kita 
@Composable
fun App(navController: NavHostController) {
    // NavHost mengatur grafik navigasi 
    NavHost(
        navController = navController,
        startDestination = "home" 
    ) {
        // Rute "home" 
        composable("home") {
            // Berikan lambda navigasi ke Home 
            Home(
                navigateFromHomeToResult = { listString ->
                    // Navigasi ke rute result dengan membawa data 
                    navController.navigate("resultContent/?listData=$listString")
                }
            )
        }
        
        // Rute "resultContent" dengan argumen 
        composable(
            route = "resultContent/?listData={listData}", 
            arguments = listOf(navArgument("listData") { 
                type = NavType.StringType 
            })
        ) { backStackEntry ->
            // Ambil argumen dan teruskan ke ResultContent 
            ResultContent(
                listData = backStackEntry.arguments?.getString("listData").orEmpty() 
            )
        }
    }
}


@Composable
fun Home(
    // Terima lambda navigasi 
    navigateFromHomeToResult: (String) -> Unit
) {
    val listData = remember {
        listOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        ).toMutableStateList()
    }
    
    var inputField = remember { mutableStateOf(Student("")) }

    HomeContent(
        listData = listData,
        inputField = inputField.value,
        onInputValueChange = { newName ->
            // Ini adalah kode yang diperbaiki dari modul 
            inputField.value = inputField.value.copy(name = newName)
        },
        onButtonClick = {
            if (inputField.value.name.isNotBlank()) {
                listData.add(inputField.value)
                // Ini adalah kode yang diperbaiki dari modul 
                inputField.value = Student("")
            }
        },
        // Teruskan panggilan navigasi 
        navigateFromHomeToResult = {
            // Ubah daftar menjadi string sebelum navigasi 
            navigateFromHomeToResult(listData.toList().toString())
        }
    )
}

@Composable
fun HomeContent(
    listData: List<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    // Terima lambda navigasi baru 
    navigateFromHomeToResult: () -> Unit
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnBackgroundTitleText(text = stringResource(id = R.string.enter_item))
                
                TextField(
                    value = inputField.name,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = onInputValueChange
                )
                
                // Bungkus tombol dalam Row 
                Row {
                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_click),
                        onClick = onButtonClick
                    )
                    
                    // Tombol Finish baru 
                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_navigate),
                        onClick = navigateFromHomeToResult 
                    )
                }
            }
        }
        
        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnBackgroundItemText(text = item.name)
            }
        }
    }
}

// Composable baru untuk layar hasil 
@Composable
fun ResultContent(listData: String) { 
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tampilkan string yang dikirim 
        OnBackgroundItemText(text = listData)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        // Preview tidak bisa menangani navigasi, jadi kita pratinjau Home saja
        Home(navigateFromHomeToResult = {}) // [cite: 718]
    }
}

data class Student(
    var name: String
)
