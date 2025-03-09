package org.yasmani.io.todomanagerapp.service;

import org.yasmani.io.todomanagerapp.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto addTodo(TodoDto todoTodo);

    TodoDto  getTodo(Long id);

    List<TodoDto> getTodos();

    TodoDto updateTodo(Long id, TodoDto todoDto);

}
