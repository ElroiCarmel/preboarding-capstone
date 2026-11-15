package com.example.tasks;

import org.springframework.data.jpa.repository.JpaRepository;

interface TasksRepository extends JpaRepository<Task, Long> {
}
