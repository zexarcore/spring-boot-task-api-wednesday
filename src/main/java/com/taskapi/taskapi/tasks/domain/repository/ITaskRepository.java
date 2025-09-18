package com.taskapi.taskapi.tasks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskapi.taskapi.tasks.domain.models.entity.Task;

public interface ITaskRepository extends JpaRepository<Task, Long> {
    // Create
    // Read
    // Update
    // Delete

    // Custom query method to find tasks by status
    List<Task> findByStatus(boolean status);
}
