package com.guiltfreechain.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.guiltfreechain.app.ui.screens.*

object Routes {
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val HABITS = "habits/{userId}"
    const val CREATE_HABIT = "create_habit/{userId}"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.ONBOARDING
    ) {
        // Экран онбординга
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onFinished = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        // Экран входа/регистрации
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { userId ->
                    navController.navigate("habits/$userId") {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // Главный экран со списком привычек
        composable(
            route = Routes.HABITS,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 1

            HabitsListScreen(
                habits = emptyList(), // TODO: Загрузить из ViewModel
                userId = userId,
                onHabitClick = { habitId ->
                    // TODO: Navigate to habit details
                },
                onAddHabit = {
                    navController.navigate("create_habit/$userId")
                }
            )
        }

        // Экран создания привычки
        composable(
            route = Routes.CREATE_HABIT,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) {
            CreateHabitScreen(
                onSave = {
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}