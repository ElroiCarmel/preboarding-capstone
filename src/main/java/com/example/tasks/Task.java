package com.example.tasks;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
class Task {

    private @Id @GeneratedValue
    Long id;
    private String title;
    private Boolean done;

    public Task() {
    }

    public Task(String title, Boolean done) {
        this.title = title;
        this.done = done;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(done, task.done);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, done);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", done=" + done +
                '}';
    }
}