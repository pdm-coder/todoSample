package com.sample.todoapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.todoapp.exception.ResourceNotFoundException;
import com.sample.todoapp.model.Task;
import com.sample.todoapp.model.User;
import com.sample.todoapp.repository.TaskRepository;
import com.sample.todoapp.repository.UserRepository;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Task createTask(Long userid, Task task) {
		Optional<User> optionalUser = userRepository.findById(userid);
		
		if(!optionalUser.isPresent()) {
			throw new ResourceNotFoundException("Record not found with userid : " + userid);
		}
		else
			return taskRepository.save(task);
	}

	@Override
	public List<Task> getAllTaskByUserId(Long userid) {
		Optional<User> optionalUser = userRepository.findById(userid);
		
		if(!optionalUser.isPresent()) {
			throw new ResourceNotFoundException("Record not found with userid : " + userid);
		}
		else
			return taskRepository.findByUserId(userid);
	}
	
	@Override
	public Task getTask(Long id) {
		Optional<Task> optionaltask = taskRepository.findById(id);
		
		if(!optionaltask.isPresent()) {
			throw new ResourceNotFoundException("Record not found with id : " + id);
		}
		else
			return optionaltask.get();
	}

	@Override
	public Task updateTaskStatus(Task task) {
		Optional<Task> optionaltask = taskRepository.findById(task.getId());
		
		if(!optionaltask.isPresent()) {
			throw new ResourceNotFoundException("Record not found with id : " + task.getId());
		}
		else {
			Task updateTask = optionaltask.get();
			updateTask.setStatus(task.getStatus());
			taskRepository.save(updateTask);
	
			return updateTask;
		}
	}

	@Override
	public void deleteTask(Long id) {
		Optional<Task> optionaltask = taskRepository.findById(id);
		if(!optionaltask.isPresent()) {
			throw new ResourceNotFoundException("Record not found with id : " + id);
		}
		else {
			taskRepository.deleteById(id);;
		}
	}
	
	@Override
	public void deleteAllTaskOfUser(Long userid) {
		Optional<User> optionalUser = userRepository.findById(userid);
		
		if(!optionalUser.isPresent()) {
			throw new ResourceNotFoundException("Record not found with userid : " + userid);
		}
		else
			taskRepository.deleteByUserId(userid);
	}

}
