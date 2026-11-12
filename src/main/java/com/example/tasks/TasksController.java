package com.example.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TasksController {

    private final List<Task> tasks = new ArrayList<>(
            List.of(
                    new Task(0, "Pray", false),
                    new Task(1, "Eat", false),
                    new Task(2, "Love", false)
            )
    );

    @GetMapping("/tasks")
    public List<Task> all() {
        return tasks;
    }

    @GetMapping("/tasks/{id}")
    public Task one(@PathVariable Long id) {
        return tasks.stream().filter(t -> t.id() == id).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Task task) {
        if (task.title().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        tasks.add(task);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        boolean removed = tasks.removeIf(t -> t.id() == id);
        if (!removed) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
