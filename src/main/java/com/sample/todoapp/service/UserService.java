package com.sample.todoapp.service;

import java.util.List;

import com.sample.todoapp.model.User;

public interface UserService {

	/**
	 * Create user
	 * 
	 * @param user
	 * @return {@link User}
	 */
	public User createUser(User user);
	
	/**
	 * Get all users
	 * 
	 * @return List<User>
	 */
	public List<User> getAllUsers();
	
	/**
	 * Get a specified user
	 * 
	 * @param id
	 * @return {@link User}
	 */
	public User getUser(long id);

	/**
	 * Update user
	 * 
	 * @param user
	 * @return {@link User}
	 */
	public User updateUser(User user);
	
	/**
	 * Delete user
	 * 
	 * @param id
	 */
	public void deleteUser(long id);
}
