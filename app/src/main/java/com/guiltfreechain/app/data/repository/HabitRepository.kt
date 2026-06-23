package com.guiltfreechain.app.data.repository

import com.guiltfreechain.app.data.model.Habit
import com.guiltfreechain.app.data.model.Log
import com.guiltfreechain.app.data.model.Rating
import com.guiltfreechain.app.data.model.User

class HabitRepository {

    // Хранилище в памяти (заглушка)
    private val users = mutableListOf<User>()
    private val habits = mutableListOf<Habit>()
    private var nextUserId = 1
    private var nextHabitId = 1

    // Авторизация
    suspend fun registerUser(name: String, email: String, password: String): Result<User> {
        return try {
            // Проверка что email не занят
            if (users.any { it.email == email }) {
                return Result.failure(Exception("Пользователь с таким email уже существует"))
            }

            val user = User(
                id = nextUserId++,
                name = name,
                email = email,
                password = password
            )
            users.add(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            val user = users.find { it.email == email && it.password == password }
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Неверный email или пароль"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Привычки
    suspend fun createHabit(userId: Int, title: String, frequency: String, note: String): Result<Habit> {
        return try {
            val habit = Habit(
                id = nextHabitId++,
                userId = userId,
                title = title,
                frequency = frequency,
                note = note,
                currentRating = 0f
            )
            habits.add(habit)
            Result.success(habit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserHabits(userId: Int): Result<List<Habit>> {
        return try {
            val userHabits = habits.filter { it.userId == userId }
            Result.success(userHabits)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateHabitRating(habitId: Int, rating: Float): Result<Rating> {
        return try {
            val habit = habits.find { it.id == habitId }
            if (habit != null) {
                habit.currentRating = rating
            }
            Result.success(Rating(habitId = habitId, rating = rating))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logHabit(habitId: Int, completed: Boolean, note: String): Result<Log> {
        return try {
            val log = Log(
                id = 0,
                habitId = habitId,
                completed = completed,
                note = note,
                date = System.currentTimeMillis()
            )
            Result.success(log)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}