package com.guiltfreechain.app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiltfreechain.app.data.model.Habit
import com.guiltfreechain.app.data.repository.HabitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HabitsViewModel(
    private val repository: HabitRepository,
    private val userId: Int
) : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    init {
        loadHabits()
    }

    fun loadHabits() {
        viewModelScope.launch {
            _loading.value = true
            repository.getUserHabits(userId).fold(
                onSuccess = { habitList ->
                    _habits.value = habitList
                    _loading.value = false
                },
                onFailure = {
                    _loading.value = false
                }
            )
        }
    }

    fun createHabit(title: String, frequency: String, note: String) {
        viewModelScope.launch {
            _loading.value = true
            repository.createHabit(userId, title, frequency, note).fold(
                onSuccess = { habit ->
                    val currentList = _habits.value.toMutableList()
                    currentList.add(habit)
                    _habits.value = currentList
                    _loading.value = false
                },
                onFailure = {
                    _loading.value = false
                }
            )
        }
    }

    fun deleteHabit(habitId: Int) {
        viewModelScope.launch {
            val currentList = _habits.value.toMutableList()
            currentList.removeAll { it.id == habitId }
            _habits.value = currentList
        }
    }
}