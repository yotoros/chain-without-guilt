package com.guiltfreechain.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.guiltfreechain.app.ui.theme.PrimaryTeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onFinished: () -> Unit
) {
    var currentPage by remember { mutableIntStateOf(0) }

    val pages = listOf(
        OnboardingPage(
            title = "Прогресс не обнуляется",
            subtitle = "Ваши усилия важны, даже после пропуска. Позвольте себе просто продолжать."
        ),
        OnboardingPage(
            title = "Мягкий подход к привычкам",
            subtitle = "Никакого давления и чувства вины. Только поддержка на вашем пути."
        ),
        OnboardingPage(
            title = "Ваш темп — идеальный темп",
            subtitle = "Отслеживайте привычки в своём ритме. Пропуск — это тоже часть пути."
        )
    )

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
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = pages[currentPage].title,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = pages[currentPage].subtitle,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    if (currentPage < pages.size - 1) {
                        currentPage++
                    } else {
                        onFinished()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryTeal
                )
            ) {
                Text(
                    text = if (currentPage < pages.size - 1) "Продолжить" else "Начать",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onFinished) {
                Text(
                    text = "Уже есть аккаунт? Войти",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                repeat(pages.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(if (index == currentPage) 24.dp else 8.dp, 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .then(
                                if (index == currentPage) {
                                    Modifier.background(PrimaryTeal)
                                } else {
                                    Modifier.background(MaterialTheme.colorScheme.outlineVariant)
                                }
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

data class OnboardingPage(
    val title: String,
    val subtitle: String
)