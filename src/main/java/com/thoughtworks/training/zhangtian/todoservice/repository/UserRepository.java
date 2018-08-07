package com.thoughtworks.training.zhangtian.todoservice.repository;

import com.thoughtworks.training.zhangtian.todoservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findOneByName(String name);
    Optional<User> findOneByIdAndName(Integer is, String name);
}
