package com.sample.todoapp.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.todoapp.model.User;
import com.sample.todoapp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(path="/add")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return ResponseEntity.ok().body(userService.createUser(user));
	}
	
	@GetMapping(path="/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> UserList = userService.getAllUsers();
		if(UserList == null || UserList.size() <= 0)
			UserList = Collections.emptyList();
		
		return ResponseEntity.ok().body(UserList);
	}
	
	@GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable long id) {
		User user = userService.getUser(id);
		return ResponseEntity.ok().body(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user){
		user.setId(id);
		return ResponseEntity.ok().body(userService.updateUser(user));
	}
	
	@DeleteMapping("/{id}")
	public HttpStatus deleteUser(@PathVariable long id) {
		userService.deleteUser(id);
		return HttpStatus.OK;
	}
}
