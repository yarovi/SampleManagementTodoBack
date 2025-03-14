package org.yasmani.io.todomanagerapp.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.yasmani.io.todomanagerapp.dto.TodoDto;
import org.yasmani.io.todomanagerapp.entity.Todo;
import org.yasmani.io.todomanagerapp.exception.ResourceNotfountException;
import org.yasmani.io.todomanagerapp.repository.TodoRepository;
import org.yasmani.io.todomanagerapp.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private ModelMapper modelMapper;

    private final TodoRepository todoRepository;

    @Override
    public TodoDto addTodo(TodoDto todoTodo) {
        // convert todoDto into jpa entity
        /*
        Todo todo = new Todo();
        todo.setTitle(todoTodo.getTitle());
        todo.setDescription(todoTodo.getDescription());
        todo.setCompleted(todoTodo.isCompleted());
*/
        Todo todo = modelMapper.map(todoTodo, Todo.class);
        Todo saveTodo = todoRepository.save(todo);

        /*
        TodoDto savedTodoDto = new TodoDto();
        savedTodoDto.setId(saveTodo.getId());
        savedTodoDto.setTitle(saveTodo.getTitle());
        savedTodoDto.setDescription(saveTodo.getDescription());
        savedTodoDto.setCompleted(saveTodo.isCompleted());
*/
        return modelMapper.map(saveTodo, TodoDto.class);
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotfountException("Todo not found with id: " + id));
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotfountException("Todo not found with id: " + id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public void deleteTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotfountException("Todo not found with id: " + id));
        todoRepository.delete(todo);

    }

    @Override
    public TodoDto completeTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotfountException("Todo not found with id: " + id));
        todo.setCompleted(true);
        Todo completedTodo = todoRepository.save(todo);
        return modelMapper.map(completedTodo, TodoDto.class);
    }

    @Override
    public TodoDto uncompleteTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotfountException("Todo not found with id: " + id));
        todo.setCompleted(false);
        Todo uncompletedTodo = todoRepository.save(todo);
        return modelMapper.map(uncompletedTodo, TodoDto.class);

    }
}
