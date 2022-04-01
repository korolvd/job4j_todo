package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

import java.sql.Date;
import java.time.LocalDate;


@Controller
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("items", service.findAll());
        return "index";
    }

    @GetMapping("/showNew")
    public String showNew(Model model) {
        model.addAttribute("items", service.findNew());
        return "index";
    }

    @GetMapping("/showDone")
    public String showDone(Model model) {
        model.addAttribute("items", service.findDone());
        return "index";
    }

    @PostMapping("/newTask")
    public String addItem(@ModelAttribute Item item) {
        item.setCreated(Date.valueOf(LocalDate.now()));
        service.add(item);
        return "redirect:/index";
    }

    @GetMapping("/details/{itemId}")
    public String detailsItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", service.findById(id));
        return "details";
    }

    @GetMapping("/editTask/{itemId}")
    public String editItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", service.findById(id));
        return "update";
    }

    @PostMapping("/saveChanges")
    public String updateItem(@ModelAttribute Item item) {
        java.util.Date date = service.findById(item.getId()).getCreated();
        item.setCreated(date);
        service.update(item);
        return "redirect:/details/" + item.getId();
    }

    @GetMapping("/doneTask/{taskId}")
    public String doneItem(@PathVariable("taskId") int id) {
        Item item = service.findById(id);
        item.setDone(true);
        service.update(item);
        return "redirect:/details/" + item.getId();
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteItem(@PathVariable("taskId") int id) {
        service.deleteById(id);
        return "redirect:/index";
    }
}
