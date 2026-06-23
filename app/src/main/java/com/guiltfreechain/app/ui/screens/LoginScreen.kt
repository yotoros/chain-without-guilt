package com.guiltfreechain.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.guiltfreechain.app.R
import com.guiltfreechain.app.ui.theme.PrimaryTeal
import com.guiltfreechain.app.viewModel.AuthState
import com.guiltfreechain.app.viewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: (Int) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }

    val authState by viewModel.authState.collectAsState()

    // Обработка состояния авторизации
    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.Success -> {
                onLoginSuccess(state.userId)
                viewModel.resetState()
            }
            is AuthState.Error -> {
                // Ошибка показывается в UI
            }
            else -> {}
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Логотип
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Цепочка без вины",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Цепочка без вины",
                style = MaterialTheme.typography.headlineMedium,
                color = PrimaryTeal
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Заботьтесь о себе без давления.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Поле имени (только для регистрации)
            if (!isLogin) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Ваше имя") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Поле email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Поле пароля
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                singleLine = true
            )

            // Ошибка
            if (authState is AuthState.Error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (authState as AuthState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Кнопка "Начать без регистрации"
            OutlinedButton(
                onClick = {
                    // TODO: Guest mode - пока заглушка
                    onLoginSuccess(0)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryTeal
                )
            ) {
                Text(
                    text = "Начать без регистрации",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка входа/регистрации
            Button(
                onClick = {
                    if (isLogin) {
                        viewModel.login(email, password)
                    } else {
                        viewModel.register(name, email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryTeal
                ),
                enabled = authState !is AuthState.Loading
            ) {
                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = if (isLogin) "Войти по почте" else "Зарегистрироваться",
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Переключатель вход/регистрация
            TextButton(onClick = {
                isLogin = !isLogin
                viewModel.resetState()
            }) {
                Text(
                    text = if (isLogin) "Нет аккаунта? Зарегистрироваться" else "Уже есть аккаунт? Войти",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}