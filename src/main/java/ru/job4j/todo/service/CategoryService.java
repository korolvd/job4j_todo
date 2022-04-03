package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryDBStore;

import java.util.List;

@Service
public class CategoryService {

    private CategoryDBStore store;

    public CategoryService(CategoryDBStore store) {
        this.store = store;
    }

    public List<Category> getAll() {
        return store.findAll();
    }
}
