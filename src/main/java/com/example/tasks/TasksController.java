package com.example.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

record CreateTaskRequest(String title, Boolean done) {}

record TaskCountResponse(long total, long done) {}

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private final TasksRepository repository;

    public TasksController(TasksRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    List<Task> all(@RequestParam(required = false) Boolean done) {
        if (done == null) return repository.findAll();
        return repository.findByDone(done);
    }

    @GetMapping("/count")
    TaskCountResponse count() {
        return new TaskCountResponse(repository.count(), repository.countByDone(true));
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

    @PutMapping("/{id}")
    Task update(@PathVariable Long id, @RequestBody CreateTaskRequest updatedTask) {
        if (updatedTask.title() == null || updatedTask.title().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Task task = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        task.setTitle(updatedTask.title());
        task.setDone(updatedTask.done());
        return repository.save(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
