package com.sample.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.todoapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
