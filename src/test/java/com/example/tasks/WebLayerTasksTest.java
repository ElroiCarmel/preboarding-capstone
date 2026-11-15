package com.example.tasks;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest
@AutoConfigureRestTestClient
public class WebLayerTasksTest {

    @Autowired
    private RestTestClient client;

    @MockitoBean
    private TasksRepository repository;

    @Test
    void getAllTasksTest() {
        client.get().uri("/tasks")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getOnlyDoneTasksTest() {
        when(repository.findById(1L)).thenReturn(Optional.of(new Task("Study", false)));
        when(repository.findById(2L)).thenReturn(Optional.of(new Task("Exercise", true)));

        client.get().uri("/tasks?done=true")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(1);
    }

    @Test
    void oneTest() {
        when(repository.findById(1L)).thenReturn(Optional.of(new Task("Study", false)));

        client.get().uri("/tasks/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("title").isEqualTo("Study");
    }

    @Test
    void createTaskTest() {
        CreateTaskRequest task = new CreateTaskRequest("Homework", false);
        client.post().uri("/tasks")
                .body(task)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void createTaskWithBlankTitleTest() {
        CreateTaskRequest task = new CreateTaskRequest("   ", false);
        client.post().uri("/tasks")
                .body(task)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void getTaskNotExistsTest() {
        client.get().uri("/tasks/99999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateTaskTest() {
        when(repository.findById(1L)).thenReturn(Optional.of(new Task("Study", false)));

        CreateTaskRequest updatedTask = new CreateTaskRequest("Study", true);
        client.put().uri("/tasks/{id}", 1L)
                .body(updatedTask)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("done").isEqualTo(true);
    }

}
