package com.sample.todoapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.todoapp.exception.ResourceNotFoundException;
import com.sample.todoapp.model.User;
import com.sample.todoapp.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		
		if(!optionalUser.isPresent()) {
			throw new ResourceNotFoundException("User Record not found with id : " + id);
		}
		else
			return optionalUser.get();
	}
	
	@Override
	public User updateUser(User user) {
		Optional<User> optionalUser = userRepository.findById(user.getId());
		
		if(!optionalUser.isPresent()) {
			throw new ResourceNotFoundException("Record not found with id : " + user.getId());
		}
		else {
			User updateUser = optionalUser.get();
			updateUser.setName(user.getName());
			userRepository.save(updateUser);
	
			return updateUser;
		}
	}
	
	@Override
	public void deleteUser(long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(!optionalUser.isPresent()) {
			throw new ResourceNotFoundException("User Record not found with id : " + id);
		}
		else {
			userRepository.deleteById(id);;
		}
	}

}
