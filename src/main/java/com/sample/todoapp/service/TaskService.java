package com.sample.todoapp.service;

import java.util.List;

import com.sample.todoapp.model.Task;

public interface TaskService {

	/**
	 * Create Task under specified user
	 * 
	 * @param userid
	 * @param task {@link Task}
	 * @return {@link Task}
	 */
	public Task createTask(Long userid, Task task);

	/**
	 * Get all task by specified user
	 * 
	 * @param userid
	 * @return List<Task>
	 */
	public List<Task> getAllTaskByUserId(Long userid);
	
	/**
	 * Get a specified task
	 * 
	 * @param id
	 * @return {@link Task}
	 */
	public Task getTask(Long id);

	/**
	 * Update task status
	 * 
	 * @param task
	 * @return {@link Task}
	 */
	public Task updateTaskStatus(Task task);
	
	/**
	 * Delete a specified task
	 * 
	 * @param id
	 */
	public void deleteTask(Long id);
	
	/**
	 * Delete all task under specified user
	 * 
	 * @param userid
	 */
	public void deleteAllTaskOfUser(Long userid);
}
