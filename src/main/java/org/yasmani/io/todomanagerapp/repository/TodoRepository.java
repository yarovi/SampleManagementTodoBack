package org.yasmani.io.todomanagerapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yasmani.io.todomanagerapp.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
