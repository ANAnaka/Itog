package com.example.itog.controller;

import com.example.itog.model.AppUsers;
import com.example.itog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public String listUsers(Model model) {
        List<AppUsers> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    // Показать форму для добавления нового пользователя
    @GetMapping("/new")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new AppUsers());
        return "user/form"; // Имя шаблона для формы
    }

    // Обработать форму для добавления нового пользователя
    @PostMapping
    public String addUser(@ModelAttribute AppUsers user) {
        userRepository.save(user);
        return "redirect:/users"; // Перенаправление на список пользователей
    }

    // Показать форму для редактирования пользователя
    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<AppUsers> optionalUser = userRepository.findById((id));
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
        } else {
            return "redirect:/users"; // Перенаправляем, если пользователь не найден
        }
        return "user/form"; // Возвращаем форму для редактирования
    }

    // Обработать форму для редактирования пользователя
    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute AppUsers user) {
        user.setId(id);
        userRepository.save(user);
        return "redirect:/users"; // Перенаправление на список пользователей
    }

    // Удалить пользователя
    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById((id));
        return "redirect:/users"; // Перенаправление на список пользователей
    }
}
