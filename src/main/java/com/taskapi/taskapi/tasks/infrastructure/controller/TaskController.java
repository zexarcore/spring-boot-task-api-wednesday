package com.taskapi.taskapi.tasks.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskapi.taskapi.tasks.domain.models.entity.Task;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    

    // read
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Tarea #1", "Descripcion #1", false));
        return ResponseEntity.ok(tasks);
    }

    // create

    // update

    // delete
}
