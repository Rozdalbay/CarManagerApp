package com.example.carcosts.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.carcosts.ui.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    navController: NavController,
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    val expenses by viewModel.expenses.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Расходы") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
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
            items(expenses) { expense ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "${expense.category}: ${expense.amount} ₽", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Пробег: ${expense.mileage} км")
                        if (!expense.comment.isNullOrEmpty()) {
                            Text(text = "Комментарий: ${expense.comment}")
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AddExpenseDialog(
                onDismiss = { showDialog = false },
                onConfirm = { amount, category, mileage, comment ->
                    viewModel.addExpense(amount.toDoubleOrNull() ?: 0.0, category, mileage.toIntOrNull() ?: 0, comment)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddExpenseDialog(onDismiss: () -> Unit, onConfirm: (String, String, String, String) -> Unit) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Добавить расход") },
        text = {
            Column {
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Сумма") })
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Категория") })
                OutlinedTextField(value = mileage, onValueChange = { mileage = it }, label = { Text("Пробег") })
                OutlinedTextField(value = comment, onValueChange = { comment = it }, label = { Text("Комментарий") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(amount, category, mileage, comment) }) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Отмена") }
        }
    )
}