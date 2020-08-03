package com.punchtheclock.api.controller;

import com.punchtheclock.api.model.TimeRegistry;
import com.punchtheclock.api.service.TimeRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;

@RestController
public class TimeRegistryController {

  @Autowired
  private TimeRegistryService timeRegistryService;

  @GetMapping("/users/{userId}/time-registries")
  public Iterable<TimeRegistry> getTimeRegistriesByUserId(
    @PathVariable("userId") Integer userId
  ) {
    return timeRegistryService.findByUserId(userId);
  }

  @PostMapping("/users/{userId}/time-registries")
  @ResponseStatus(HttpStatus.CREATED)
  public TimeRegistry create(
    @PathVariable("userId") Integer userId,
    @Valid @RequestBody TimeRegistry registry
  ) {
    return timeRegistryService.create(userId, registry).orElseThrow(() ->
      new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
    );
  }
}
