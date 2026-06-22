package com.guiltfreechain.app.data.api

import com.guiltfreechain.app.data.model.Habit
import com.guiltfreechain.app.data.model.Log
import com.guiltfreechain.app.data.model.Rating
import com.guiltfreechain.app.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface SupabaseApi {

    // ===== ПОЛЬЗОВАТЕЛИ =====
    @POST("rest/v1/users")
    suspend fun createUser(@Body user: User): Response<User>

    @GET("rest/v1/users")
    suspend fun getUser(
        @Query("email") email: String,
        @Query("password_hash") password: String
    ): Response<List<User>>

    // ===== ПРИВЫЧКИ =====
    @GET("rest/v1/habits")
    suspend fun getHabits(@Query("user_id") userId: Int): Response<List<Habit>>

    @POST("rest/v1/habits")
    suspend fun createHabit(@Body habit: Habit): Response<Habit>

    @PATCH("rest/v1/habits")
    suspend fun updateHabit(
        @Query("id") habitId: Int,
        @Body habit: Habit
    ): Response<Habit>

    @DELETE("rest/v1/habits")
    suspend fun deleteHabit(@Query("id") habitId: Int): Response<Unit>

    // ===== ЛОГИ (ОТМЕТКИ) =====
    @GET("rest/v1/logs")
    suspend fun getLogs(@Query("habit_id") habitId: Int): Response<List<Log>>

    @POST("rest/v1/logs")
    suspend fun createLog(@Body log: Log): Response<Log>

    // ===== РЕЙТИНГИ =====
    @GET("rest/v1/ratings")
    suspend fun getRatings(@Query("habit_id") habitId: Int): Response<List<Rating>>

    @POST("rest/v1/ratings")
    suspend fun createRating(@Body rating: Rating): Response<Rating>
}