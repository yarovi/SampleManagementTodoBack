package org.yasmani.io.todomanagerapp.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yasmani.io.todomanagerapp.dto.TodoDto;
import org.yasmani.io.todomanagerapp.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;
    // build rest api to create a todo
   @PostMapping
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto  todoDto) {

       TodoDto savedTodo = todoService.addTodo(todoDto);

       return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    // build rest api to get a todo

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable Long id) {
        TodoDto todoDto = todoService.getTodo(id);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> getTodos() {
        List<TodoDto> todoDtos = todoService.getTodos();
        return  ResponseEntity.ok(todoDtos);
    }
}
