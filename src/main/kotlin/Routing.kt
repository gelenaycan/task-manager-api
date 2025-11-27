package example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.http.*



fun Application.configureRouting() {
    routing {

        get("/") {
            call.respondText("Task Manager API is running!")
        }

        get("/tasks") {
            call.respond(tasks)
        }

        get("/tasks/{id}") {
            val idParam = call.parameters["id"]
            val id = idParam?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@get
            }

            val task = findTaskById(id)

            if (task == null) {
                call.respond(HttpStatusCode.NotFound, "Task not found")
            } else {
                call.respond(task)
            }
        }
        post("/tasks") {
            val newTask = call.receive<Task>()
            val createdTask = createTask(newTask)
            call.respond(HttpStatusCode.Created, createdTask)
        }
        put("/tasks/{id}/complete") {
            val idParam = call.parameters["id"]
            val id = idParam?.toIntOrNull()

            if (id==null){
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@put
            }
            val updatedTask = completeTask(id)

            if(updatedTask==null){
                call.respond(HttpStatusCode.NotFound, "Task not found")

            }else{
                call.respond(HttpStatusCode.OK, updatedTask)
            }


        }
        delete("/tasks/{id}") {
            val idParam = call.parameters["id"]
            val id = idParam?.toIntOrNull()

            if(id==null){
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@delete
            }

            val deleted = deleteTask(id)

            if(!deleted){
                call.respond(HttpStatusCode.NotFound, "Task not found")
            }else{
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}
