package com.guiltfreechain.app.data.repository

import com.guiltfreechain.app.data.api.RetrofitClient
import com.guiltfreechain.app.data.model.Habit
import com.guiltfreechain.app.data.model.Log
import com.guiltfreechain.app.data.model.Rating
import com.guiltfreechain.app.data.model.User
import java.security.MessageDigest

class HabitRepository {

    private val api = RetrofitClient.api

    // ===== АВТОРИЗАЦИЯ =====

    suspend fun registerUser(name: String, email: String, password: String): Result<User> {
        return try {
            val passwordHash = hashPassword(password)
            val user = User(name = name, email = email, passwordHash = passwordHash)
            val response = api.createUser(user)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка регистрации: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            val passwordHash = hashPassword(password)
            val response = api.getUser(email, passwordHash)
            if (response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()) {
                Result.success(response.body()!!.first())
            } else {
                Result.failure(Exception("Неверный email или пароль"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ===== ПРИВЫЧКИ =====

    suspend fun getHabits(userId: Int): Result<List<Habit>> {
        return try {
            val response = api.getHabits(userId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Ошибка получения привычек"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createHabit(habit: Habit): Result<Habit> {
        return try {
            val response = api.createHabit(habit)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка создания привычки"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateHabit(habitId: Int, habit: Habit): Result<Habit> {
        return try {
            val response = api.updateHabit(habitId, habit)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка обновления привычки"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteHabit(habitId: Int): Result<Unit> {
        return try {
            val response = api.deleteHabit(habitId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка удаления привычки"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ===== ЛОГИ (ОТМЕТКИ) =====

    suspend fun createLog(log: Log): Result<Log> {
        return try {
            val response = api.createLog(log)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка создания записи"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLogs(habitId: Int): Result<List<Log>> {
        return try {
            val response = api.getLogs(habitId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Ошибка получения логов"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ===== РЕЙТИНГИ =====

    suspend fun createRating(rating: Rating): Result<Rating> {
        return try {
            val response = api.createRating(rating)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка создания рейтинга"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRatings(habitId: Int): Result<List<Rating>> {
        return try {
            val response = api.getRatings(habitId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Ошибка получения рейтингов"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ===== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ =====

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    fun calculateNewRating(currentRating: Float, completed: Boolean): Float {
        return if (completed) {
            // При выполнении - небольшой бонус (максимум 100)
            (currentRating + 2f).coerceAtMost(100f)
        } else {
            // При пропуске - снижение на 16%
            (currentRating - 16f).coerceAtLeast(0f)
        }
    }
}