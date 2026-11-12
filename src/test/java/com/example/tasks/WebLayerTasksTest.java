package com.example.tasks;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest
@AutoConfigureRestTestClient
public class WebLayerTasksTest {

    @Autowired
    private RestTestClient client;


    @Test
    void getAllTasksTest() {
        client.get().uri("/tasks")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3);
    }

    @Test
    void oneTest() {
        client.get().uri("/tasks/0")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .consumeWith(result -> {
                    Task t = result.getResponseBody();
                    assertThat(t.id()).isEqualTo(0);
                    assertThat(t.title()).isEqualTo("Pray");
                    assertThat(t.done()).isFalse();
                });
    }

    @Test
    void oneNotExistsTest() {
        client.get().uri("/tasks/12")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createTaskTest() {
        Task task = new Task(5, "Homework", true);
        client.post().uri("/tasks")
                .body(task)
                .exchange()
                .expectStatus().isCreated();

        // Test the new task is in the list
        client.get().uri("/tasks/5")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("title").isEqualTo("Homework");
    }

    @Test
    void createBlankTitleTest() {
        Task task = new Task(5, "", true);
        client.post().uri("/tasks")
                .body(task)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deleteTest() {
        client.delete().uri("/tasks/1")
                .exchange()
                .expectStatus().isNoContent();
        client.get().uri("/tasks/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}
