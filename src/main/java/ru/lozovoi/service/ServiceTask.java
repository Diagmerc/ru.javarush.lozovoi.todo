package ru.lozovoi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lozovoi.dao.TaskDAO;
import ru.lozovoi.domain.Status;
import ru.lozovoi.domain.Task;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServiceTask {

    private final TaskDAO taskDAO;

    public ServiceTask(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> getAll(int offset, int limit) {
        return taskDAO.getAll(offset, limit);
    }

    public int getAllCount() {
        return taskDAO.getAllCount();
    }


    @Transactional
    public Task update(int id, String description, Status status) {
        Task task = taskDAO.getById(id);
        if(task==null){
            throw new RuntimeException("Not found");
        }
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public Task create(String description, Status status) {
        Task task = new Task();
        task.setStatus(status);
        task.setDescription(description);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public void delete(int id) {
        Task task = taskDAO.getById(id);
        if(task==null){
            throw new RuntimeException("Not found");
        }
        taskDAO.delete(task);
    }
}