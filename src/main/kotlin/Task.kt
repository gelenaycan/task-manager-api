package example

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val isDone: Boolean = false
)

//fake database for now -mini database kinda
val tasks = mutableListOf(
    Task(1, "Study Kotlin", "Learn basics of Ktor"),
    Task(2, "Prepare Interview", "Practice REST, SQL, DevOps"),
    Task(3, "Build a mini API", "Daily project for practice")
)
fun findTaskById(id: Int) : Task? {
    return tasks.find { it.id == id }
}

fun createTask(newTask: Task) : Task{
    //find the biggest id if there is not = 0
    val newId=(tasks.maxOfOrNull { it.id }?:0)+1
    //create a copy with new id
    val taskWithId = newTask.copy(id = newId)
    //add to the list
    tasks.add(taskWithId)
    //return the task
    return taskWithId

}

fun completeTask(id: Int): Task? {
    val task = tasks.find { it.id == id }
    if(task == null) return null
    val updated = task.copy(isDone = true)

    //remove the completed task
    tasks.remove(task)

    //add the new task
    tasks.add(updated)

    return updated
}
fun deleteTask(id: Int) : Boolean {
    val task = tasks.find { it.id == id } ?: return false
    tasks.remove(task)
    return true
}