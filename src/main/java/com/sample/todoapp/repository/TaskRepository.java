package com.sample.todoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.todoapp.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByUserId(Long userId);
	
	void deleteByUserId(Long userId);
}
