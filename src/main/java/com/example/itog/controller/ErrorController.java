package com.example.itog.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class ErrorController {

    @GetMapping("/error/403")
    public String handle403(Model model) {
        model.addAttribute("error", "Доступ запрещен!");
        return "error/403"; // Верните имя шаблона для страницы 403
    }

    @GetMapping("/error/404")
    public String handle404(Model model) {
        model.addAttribute("error", "Страница не найдена!");
        return "error/404"; // Верните имя шаблона для страницы 404
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404Exception(Model model) {
        model.addAttribute("error", "Страница не найдена!");
        return "error/404"; // Верните имя шаблона для страницы 404
    }
}
