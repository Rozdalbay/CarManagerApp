package com.example.carcosts.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carcosts.ui.viewmodel.CarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListScreen(
    viewModel: CarViewModel = hiltViewModel(),
    onCarClick: (Int) -> Unit
) {
    val cars by viewModel.cars.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Мои автомобили") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cars) { car ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCarClick(car.id) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "${car.brand} ${car.model}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Год: ${car.year}, Пробег: ${car.initialMileage} км")
                    }
                }
            }
        }

        if (showDialog) {
            AddCarDialog(
                onDismiss = { showDialog = false },
                onConfirm = { brand, model, year, mileage ->
                    viewModel.addCar(brand, model, year, mileage)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddCarDialog(onDismiss: () -> Unit, onConfirm: (String, String, String, String) -> Unit) {
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Добавить авто") },
        text = {
            Column {
                OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Марка") })
                OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Модель") })
                OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Год") })
                OutlinedTextField(value = mileage, onValueChange = { mileage = it }, label = { Text("Пробег") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(brand, model, year, mileage) }) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Отмена") }
        }
    )
}