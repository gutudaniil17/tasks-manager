package com.gutu.tasksmanager.repository;

import com.gutu.tasksmanager.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TaskRepository interface is a repository that handles the database operations related to tasks.
 *
 * @author gutu.daniil
 * @version 1.0
 * @since 06.03.2025
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
