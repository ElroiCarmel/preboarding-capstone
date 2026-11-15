package com.example.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

record CreateTaskRequest(String title, Boolean done) {}

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private final TasksRepository repository;

    public TasksController(TasksRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    List<Task> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Task one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Task creat(@RequestBody CreateTaskRequest newTask) {
        if (newTask.title().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return repository.save(new Task(newTask.title(), newTask.done()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
