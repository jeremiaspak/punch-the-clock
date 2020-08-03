package com.punchtheclock.api.controller;

import com.punchtheclock.api.model.TimeRegistry;
import com.punchtheclock.api.model.User;
import com.punchtheclock.api.service.TimeRegistryService;
import com.punchtheclock.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private TimeRegistryService timeRegistryService;

  @GetMapping("/users")
  public Iterable<User> list() {
    return userService.list();
  }

  @GetMapping("/users/{id}")
  public User get(@PathVariable("id") Integer id) {
    return userService.get(id).orElseThrow(() ->
      new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
    );
  }

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  public User create(@Valid @RequestBody User user) {
    return userService.create(user);
  }

  @PutMapping("/users/{id}")
  public User update(@Valid @RequestBody User user, @PathVariable("id") Integer id) {
    return userService.update(user, id).orElseThrow(() ->
      new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
    );
  }

  @DeleteMapping("/users/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Integer id) {
    userService.get(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
      );

    userService.delete(id);
  }

  @GetMapping("/users/{id}/work-time")
  public HashMap<String, Object> getTimeRegistriesByUserId(
    @PathVariable("id") Integer id
  ) {
    Iterable<TimeRegistry> registries = timeRegistryService.findByUserId(id);
    String totalHours = timeRegistryService.getTotalHours(registries);
    HashMap<String, Object> result = new HashMap<>();
    result.put("registries", registries);
    result.put("total", totalHours);

    return result;
  }
}
