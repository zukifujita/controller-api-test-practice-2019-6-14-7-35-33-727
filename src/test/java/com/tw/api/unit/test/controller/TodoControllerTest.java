package com.tw.api.unit.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.api.unit.test.domain.todo.Todo;
import com.tw.api.unit.test.domain.todo.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TodoController.class)
@ActiveProfiles(profiles = "test")
class TodoControllerTest {
    @Autowired
    private TodoController todoController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoRepository todoRepository;

    @Test
    void should_get_all_todo() throws Exception {
        //given
        List<Todo> todoList = new ArrayList<>();
        Todo todo = new Todo("title", true);
        todoList.add(todo);
        //when
        when(todoRepository.getAll()).thenReturn(todoList);
        //then
        ResultActions result = mvc.perform(get("/todos"));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].completed", is(true)))
                .andExpect(jsonPath("$[0].title").value("title"));
    }

    @Test
    void should_get_todo_by_id() throws Exception {
        //given
        List<Todo> todoList = new ArrayList<>();
        Todo todo = new Todo(1, "title", true, 1);
        todoList.add(todo);
        //when
        when(todoRepository.findById(1)).thenReturn(todoList.stream().findFirst());
        //then
        ResultActions result = mvc.perform(get("/todos/{todo-id}", 1));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.completed", is(true)))
                .andExpect(jsonPath("$.title").value("title"));
    }

    @Test
    void should_not_get_todo_by_id() throws Exception {
        //given
        List<Todo> todoList = new ArrayList<>();
        Todo todo = new Todo(1, "title", true, 1);
        todoList.add(todo);
        //when
        when(todoRepository.findById(1)).thenReturn(todoList.stream().findFirst());
        //then
        ResultActions result = mvc.perform(get("/todos/{todo-id}", 3));
        result.andExpect(status().isNotFound());
    }

    @Test
    void should_save_todo() throws Exception {
        //given
        Todo todo = new Todo(1, "title", true, 1);
        //when
        ResultActions result = mvc.perform(post("/todos")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(todo)));
        //then
        result.andExpect(status().isCreated());
    }

    @Test
    void should_update_todo() throws Exception {
        //given
        List<Todo> todoList = new ArrayList<>();
        Todo todo = new Todo(1, "title", true, 1);
        Todo todoUpdate = new Todo(1, "gwapo", true, 1);
        todoList.add(todo);
        //when
        when(todoRepository.findById(1)).thenReturn(todoList.stream().findFirst());
        //given
        ResultActions result = mvc.perform(patch("/todos/{todo-id}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(todoUpdate)));
        result.andExpect(status().isOk());
    }

    @Test
    void should_not_update_todo_when_todo_is_present() throws Exception {
        //given
        List<Todo> todoList = new ArrayList<>();
        Todo todo = new Todo(1, "title", true, 1);
        Todo todoUpdate = new Todo(1, "title", true, 1);
        todoList.add(todo);
        //when
        when(todoRepository.findById(1)).thenReturn(todoList.stream().findFirst());
        //given
        ResultActions result = mvc.perform(patch("/todos/{todo-id}", 3)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(todoUpdate)));
        result.andExpect(status().isNotFound());
    }

    @Test
    void should_not_update_todo_when_newTodo_is_null() throws Exception {
        //given
        List<Todo> todoList = new ArrayList<>();
        Todo todo = new Todo(1, "title", true, 1);
        Todo newTodo = new Todo(1, "title2", true, 1);
        todoList.add(todo);
        //when
        when(todoRepository.findById(1)).thenReturn(todoList.stream().findFirst());
        //given
        ResultActions result = mvc.perform(patch("/todos/{todo-id}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(null)));
        result.andExpect(status().isBadRequest());
    }

    @Test
    void should_delete_todo_by_id() throws Exception {
        //given
        List<Todo> todoList = new ArrayList<>();
        Todo todo = new Todo(1, "title", true, 1);
        todoList.add(todo);
        //when
        when(todoRepository.findById(1)).thenReturn(todoList.stream().findFirst());
        //then
        ResultActions result = mvc.perform(delete("/todos/{id}", 1));
        result.andExpect(status().isOk());
    }

    @Test
    void should_not_delete_todo_by_id() throws Exception {
        //given
        List<Todo> todoList = new ArrayList<>();
        Todo todo = new Todo(1, "title", true, 1);
        todoList.add(todo);
        //when
        when(todoRepository.findById(1)).thenReturn(todoList.stream().findFirst());
        //then
        ResultActions result = mvc.perform(delete("/todos/{id}", 2));
        result.andExpect(status().isNotFound());
    }
}