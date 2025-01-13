package com.example.pracainynierska.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pracainynierska.API.handler.authorization.AuthorizationHandlerInterface
import com.example.pracainynierska.context.PlayerContextInterface
import com.example.pracainynierska.model.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TaskViewModel(
    pc: PlayerContextInterface,
    private val playerAuthorizationHandler: AuthorizationHandlerInterface,
) : AbstractViewModel(pc) {
    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>> = _tasks

    fun addTask(task: Task) {
        _tasks.value = _tasks.value?.plus(task)
    }

    fun completeTask(taskId: Int) {
        _tasks.value = _tasks.value?.map { if (it.id == taskId) it.copy(status = "Completed") else it }
    }

    fun updateTask(task: Task) {
        _tasks.value = _tasks.value?.map { if (it.id == task.id) task else it }
    }

    fun deleteTask(taskId: Int) {
        Log.d("LoginViewModel", "Usunięcie zadania z ID: $taskId")
        _tasks.value = _tasks.value?.filter { it.id != taskId }
    }

    fun getTaskById(taskId: String?): Task? = _tasks.value?.firstOrNull { it.id.toString() == taskId }

    fun getTasksForDate(selectedDate: String): List<Task>? {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val selectedDateParsed = dateFormat.parse(selectedDate) ?: return emptyList()

        val taskDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())

        return tasks.value?.filter { task ->
            val taskStartDate = taskDateFormat.parse(task.startDate)
            val taskEndDate = taskDateFormat.parse(task.endDate)

            taskStartDate != null &&
                taskEndDate != null &&
                selectedDateParsed.isSameDay(taskStartDate) ||
                selectedDateParsed.isSameDay(taskEndDate) ||
                (selectedDateParsed.after(taskStartDate) && selectedDateParsed.before(taskEndDate))
        }
    }

    // Porównanie dat bez godzin
    // todo powinno być w osobnym serwisie (np sameDateChecker)
    private fun Date.isSameDay(other: Date): Boolean {
        val calendar1 = Calendar.getInstance().apply { time = this@isSameDay }
        val calendar2 = Calendar.getInstance().apply { time = other }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
            calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
    }
}
