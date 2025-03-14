package org.yasmani.io.todomanagerapp.service;

import org.yasmani.io.todomanagerapp.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto addTodo(TodoDto todoTodo);

    TodoDto  getTodo(Long id);

    List<TodoDto> getTodos();

    TodoDto updateTodo(Long id, TodoDto todoDto);

    void deleteTodoById(Long id);

    TodoDto completeTodoById(Long id);

    TodoDto uncompleteTodoById(Long id);
}
