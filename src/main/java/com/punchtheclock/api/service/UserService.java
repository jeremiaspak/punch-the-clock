package com.punchtheclock.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.punchtheclock.api.model.User;
import com.punchtheclock.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public Iterable<User> list() {
    return userRepository.findAll();
  }

  public Optional<User> get(Integer id) {
    return userRepository.findById(id);
  }

  public User create(User user) {
    return userRepository.save(user);
  }

  public Optional<User> update(User newUser, Integer id) {
    return userRepository
      .findById(id)
      .map(user -> {
        user.setName(newUser.getName());
        user.setCpf(newUser.getCpf());
        user.setEmail(newUser.getEmail());
        return userRepository.save(user);
      });
  }

  public void delete(Integer id) {
    userRepository.deleteById(id);
  }
}
