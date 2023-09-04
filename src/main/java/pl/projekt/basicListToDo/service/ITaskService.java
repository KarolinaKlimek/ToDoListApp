package pl.projekt.basicListToDo.service;

import org.springframework.data.domain.Page;
import pl.projekt.basicListToDo.model.Task;

import java.util.List;

public interface ITaskService {

    List<Task> getAllTasks();
    void saveTask(Task task);

    Task getTaskById(long id);

    void deleteTaskById(long id);

    void deleteAll();

    Page<Task> findPaginated(int pageNumber, int pageSize, String sortedField, String sortedDirection);
}
