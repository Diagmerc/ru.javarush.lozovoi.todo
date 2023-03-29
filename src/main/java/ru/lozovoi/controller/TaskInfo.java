package ru.lozovoi.controller;

import lombok.Data;
import ru.lozovoi.domain.Status;

@Data
public class TaskInfo {
    private String description;

    private Status status;
}