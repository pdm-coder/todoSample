package com.sample.todoapp.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sample.todoapp.exception.ResourceNotFoundException;
import com.sample.todoapp.model.Task;
import com.sample.todoapp.service.TaskService;

@RestController
@RequestMapping("/")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@PostMapping(path="user/{userid}/task")
	public ResponseEntity<Task> createTask(@PathVariable long userid, @RequestBody Task task) {
		return ResponseEntity.ok().body(taskService.createTask(userid, task));
	}
	
	@GetMapping(path="user/{userid}/task/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Task>> getAllTaskByUserId(@PathVariable long userid) {
		List<Task> taskList = taskService.getAllTaskByUserId(userid);
		if(taskList == null || taskList.size() <= 0)
			taskList = Collections.emptyList();
		
		return ResponseEntity.ok().body(taskList);
	}
	
	@GetMapping(path="task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> getTask(@PathVariable long id) {
		Task task = taskService.getTask(id);
		return ResponseEntity.ok().body(task);
	}
	
	@PutMapping("task")
	public ResponseEntity<Task> updateTaskStatus(@RequestBody Task task){
		return ResponseEntity.ok().body(this.taskService.updateTaskStatus(task));
	}
	
	@DeleteMapping("task/{id}")
	public HttpStatus deleteTask(@PathVariable long id) {
		taskService.deleteTask(id);
		return HttpStatus.OK;
	}
	
	@DeleteMapping("user/{userid}/task/all")
	public HttpStatus deleteAllTaskOfUser(@PathVariable long userid) {
		taskService.deleteAllTaskOfUser(userid);
		return HttpStatus.OK;
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String handleResourceNotFoundException(Exception ex) {
		return ex.getMessage();
	}
}
