package pl.projekt.basicListToDo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.projekt.basicListToDo.model.Task;
import pl.projekt.basicListToDo.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Task getTaskById(long task_id) {
        return taskRepository.findById(task_id)
                .orElseThrow(() -> new RuntimeException("Task not found for id :: " + task_id));
    }

    @Override
    public void deleteTaskById(long task_id) {
        taskRepository.deleteById(task_id);
    }

    @Override
    public void deleteAll() {
        taskRepository.deleteAll();
    }

    @Override
    public Page<Task> findPaginated(int pageNumber, int pageSize, String sortedField, String sortedDirection) {
        Sort sort = sortedDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortedField).ascending() : Sort.by(sortedField).descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize,sort);
        return taskRepository.findAll(pageable);
    }
}
