package com.punchtheclock.api.controller;
import com.punchtheclock.api.model.User;
import com.punchtheclock.api.service.TimeRegistryService;
import com.punchtheclock.api.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserService userService;

  @MockBean
  private TimeRegistryService timeRegistryService;

  private User userFactory() {
    User user = new User();
    user.setId(1);
    user.setName("John");
    user.setEmail("john@punchtheclock.com");
    user.setCpf("12345678901");
    return  user;
  }

  @Test
  public void shouldReturnListOfUsersAnd200() throws Exception {
    User user = userFactory();

    when(userService.list()).thenReturn(Arrays.asList(user));
    mockMvc.perform(get("/users"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$[0].id").value(user.getId()))
      .andExpect(jsonPath("$[0].name").value(user.getName()))
      .andExpect(jsonPath("$[0].email").value(user.getEmail()))
      .andExpect(jsonPath("$[0].cpf").value(user.getCpf()));
  }

  @Test
  public void shouldReturnUserAnd200() throws Exception {
    User user = userFactory();

    when(userService.get(any())).thenReturn(Optional.of(user));
    mockMvc.perform(get("/users/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(user.getId()))
      .andExpect(jsonPath("$.name").value(user.getName()))
      .andExpect(jsonPath("$.email").value(user.getEmail()))
      .andExpect(jsonPath("$.cpf").value(user.getCpf()));
  }

  @Test
  public void shouldReturnUserErrorAnd404() throws Exception {
    mockMvc.perform(get("/users/1"))
      .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnUserAnd201() throws Exception {
    User user = userFactory();
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    when(userService.create(any())).thenReturn(user);
    mockMvc.perform(post("/users")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(user))
    )
    .andDo(print())
    .andExpect(status().isCreated());

    verify(userService, times(1)).create(userCaptor.capture());
    assertThat(userCaptor.getValue()).isEqualToComparingFieldByField(user);
  }

  @Test
  public void updateShouldReturnUserAnd200() throws Exception {
    User user = userFactory();
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    when(userService.update(any(), any())).thenReturn(Optional.of(user));
    mockMvc.perform(put("/users/1")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(user))
    )
    .andExpect(status().isOk());

    verify(userService, times(1)).update(userCaptor.capture(), any());
    assertThat(userCaptor.getValue()).isEqualToComparingFieldByField(user);
  }
}
