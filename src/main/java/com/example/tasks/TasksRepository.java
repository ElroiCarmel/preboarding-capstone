package com.example.tasks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface TasksRepository extends JpaRepository<Task, Long> {
    List<Task> findByDone(Boolean done);

    long countByDone(Boolean done);
}
