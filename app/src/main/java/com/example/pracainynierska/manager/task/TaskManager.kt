import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pracainynierska.API.api_client.TaskApi
import com.example.pracainynierska.API.model.Task
import com.example.pracainynierska.manager.task.TaskManagerInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskManager(
    private val apiClient: TaskApi
) : TaskManagerInterface {

    private val _tasks = MutableLiveData<List<Task>>()
    val boosters: LiveData<List<Task>> get() = _tasks


    override fun getTasks(): List<Task> {
        return _tasks.value ?: emptyList()
    }

    override fun getTasksList(): LiveData<List<Task>> {
        return _tasks
    }

    override suspend fun addTask(
        type: String,
        name: String,
        description: String,
        category: String,
        difficulty: String,
        startsAt: String,
        endsAt: String
    ) {
        return withContext(Dispatchers.IO) {
            apiClient.addTask(
                type,
                name,
                description,
                category,
                difficulty,
                startsAt,
                endsAt
            )
        }
    }

    override suspend fun getTasksViaApi() {
        return withContext(Dispatchers.IO) {
            val tasksList = apiClient.getTasks()
            _tasks.postValue(tasksList)
        }
    }
}
