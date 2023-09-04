package pl.projekt.basicListToDo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.projekt.basicListToDo.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
