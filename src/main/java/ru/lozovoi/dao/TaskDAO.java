package ru.lozovoi.dao;

import ru.lozovoi.domain.Task;

import java.util.List;

public interface TaskDAO {


    List<Task> getAll(int offset, int limit);

    int getAllCount();

    Task getById(int id);

    void saveOrUpdate(Task task);

    void delete(Task task);
}
