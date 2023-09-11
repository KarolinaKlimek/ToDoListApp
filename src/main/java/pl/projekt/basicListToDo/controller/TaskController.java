package pl.projekt.basicListToDo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.projekt.basicListToDo.model.Task;
import pl.projekt.basicListToDo.service.ITaskService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1,"title","asc", model);
    }

    @GetMapping("/newTaskPage")
    public String newTaskPage(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "newTask";
    }

    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute("task") @Valid Task task, BindingResult result) {

        if(result.hasErrors()) {
            return "newTask";
        }

        if (task.getId() != null) {
            Task originalTask = taskService.getTaskById(task.getId());
            originalTask.setTitle(task.getTitle());
            originalTask.setDescription(task.getDescription());
            originalTask.setPriority(task.getPriority());
            originalTask.setDone(task.getDone());
            originalTask.setCreated(task.getCreated());
            if (task.getDeadline() != null) {
                originalTask.setDeadline(task.getDeadline());
            }
            taskService.saveTask(originalTask);
        } else {
            task.setDone(false);
            taskService.saveTask(task);
        }

        return "redirect:/";
    }

    @GetMapping("/showUpdateTaskPage/{id}")
    public String showUpdateTaskPage(@PathVariable(value = "id") long id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "updateTask";
    }

    @GetMapping("/deleteTaskById/{id}")
    public String deleteTaskById(@PathVariable(value = "id") long id){
        taskService.deleteTaskById(id);
        return "redirect:/";
    }

    @PostMapping("/deleteAll")
    public String deleteAllTasks() {
        taskService.deleteAll();
        return "redirect:/";
    }

    @GetMapping("/page/{pageNumber}")
    public String findPaginated(
            @PathVariable(value = "pageNumber") int pageNumber,
            @RequestParam("sortedField") String sortedField,
            @RequestParam("sortedDirection") String sortedDirection,
            Model model) {
        int pageSize = 10;
        Page<Task> page = taskService.findPaginated(pageNumber,pageSize,sortedField,sortedDirection);
        List<Task> tasks = page.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements",page.getTotalElements());

        model.addAttribute("sortedField", sortedField);
        model.addAttribute("sortedDirection", sortedDirection);
        model.addAttribute("reserveSortDirection", sortedDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("tasks",tasks);

        return "mainPage";
    }
}
