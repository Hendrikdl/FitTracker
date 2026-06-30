package com.example.fittracker.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fittracker.navigation.Screen
import com.example.fittracker.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, vm: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(Modifier.height(8.dp))

        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            vm.login(email, password) {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        }) {
            Text("Login")
        }

        TextButton(onClick = {
            navController.navigate(Screen.Register.route)
        }) {
            Text("Create Account")
        }

        vm.error.collectAsState().value?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}