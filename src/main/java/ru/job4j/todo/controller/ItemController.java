package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Controller
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    public ItemController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findAll());
        return "index";
    }

    @GetMapping("/showNew")
    public String showNew(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findNew());
        return "index";
    }

    @GetMapping("/showDone")
    public String showDone(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findDone());
        return "index";
    }

    @GetMapping("/addPage")
    public String formAdd(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("categories", categoryService.getAll());
        return "addPage";
    }

    @PostMapping("/newTask")
    public String addItem(@ModelAttribute Item item,
                          @RequestParam(name = "catIds") List<String> idsCat,
                          HttpSession session) {
        item.setCreated(Date.valueOf(LocalDate.now()));
        item.setUser((User) session.getAttribute("user"));
        itemService.add(item, idsCat);
        return "redirect:/index";
    }

    @GetMapping("/details/{itemId}")
    public String detailsItem(Model model, @PathVariable("itemId") int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("item", itemService.findById(id));
        return "details";
    }

    @GetMapping("/editTask/{itemId}")
    public String editItem(Model model, @PathVariable("itemId") int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("item", itemService.findById(id));
        return "update";
    }

    @PostMapping("/saveChanges")
    public String updateItem(@ModelAttribute Item item) {
        Item itemDb = itemService.findById(item.getId());
        item.setCreated(itemDb.getCreated());
        item.setUser(itemDb.getUser());
        itemService.update(item);
        return "redirect:/details/" + item.getId();
    }

    @GetMapping("/doneTask/{taskId}")
    public String doneItem(@PathVariable("taskId") int id) {
        itemService.doneById(id);
        return "redirect:/details/" + id;
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteItem(@PathVariable("taskId") int id) {
        itemService.deleteById(id);
        return "redirect:/index";
    }
}
