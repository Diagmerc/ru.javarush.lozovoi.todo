package ru.lozovoi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lozovoi.domain.Task;
import ru.lozovoi.service.ServiceTask;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class TaskController {

    private final ServiceTask serviceTask;

    public TaskController(ServiceTask serviceTask) {
        this.serviceTask = serviceTask;
    }

    @RequestMapping("/")
    public String tasks(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {

        List<Task> tasks = serviceTask.getAll((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        int totalPage = (int) Math.ceil(1.0 * serviceTask.getAllCount() / limit);
        if(totalPage > 1){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().toList();
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "tasks";
    }

    @PostMapping("/{id}")
    public String edit(Model model, @PathVariable Integer id, @RequestBody TaskInfo info) {
        if(id==null || id <= 0){
            throw new RuntimeException("Invalid id");
        }
        Task task = serviceTask.update(id, info.getDescription(), info.getStatus());
        return tasks(model,1, 10);
    }

    @PostMapping("/")
    public String add(Model model, @RequestBody TaskInfo info) {
        Task task = serviceTask.create(info.getDescription(), info.getStatus());
        return tasks(model, 1, 10);
    }

    @DeleteMapping("/{id}")
    public String delete(Model model, @PathVariable Integer id) {
        if(id==null || id <= 0){
            throw new RuntimeException("Invalid id");
        }
        serviceTask.delete(id);
        return tasks(model, 1, 10);
    }
}