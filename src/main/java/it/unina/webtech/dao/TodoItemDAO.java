package it.unina.webtech.dao;

import it.unina.webtech.data.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class TodoItemDAO {

    static List<TodoItem> todos = new ArrayList<TodoItem>();
    public static List<TodoItem> getAll(){
        return todos;
    }

    public static void add(TodoItem todo) {
        if(todo.getTitle() == null || todo.getTitle().isEmpty()){
            throw new IllegalArgumentException("Title must not be empty");
        }

        // Find the highest existing ID
        int maxId = todos.stream()
                .mapToInt(TodoItem::getId)
                .max()
                .orElse(0);

        // Set the new ID to maxId + 1
        todo.setId(maxId + 1);

        todos.add(todo);
    }

    public static void deleteById(int id) {
        TodoItem toDelete = todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .orElse(null);
        if(toDelete != null){
            todos.remove(toDelete);
        } else {
            throw new IllegalArgumentException("Invalid id");
        }
    }
}
