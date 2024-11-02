package com.example.itog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/errors")
    public String handleError(HttpServletRequest request) {
        int statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (statusCode == 500) {
            return "error/403"; // Перенаправляем на custom 500.html
        }
        if (statusCode == 403) {
            return "error/403";
        }
        if (statusCode == 404) {
            return "error/404";
        }
        // Можно добавить обработку других кодов ошибок (404, 403 и т.д.)

        return "/home"; // Шаблон по умолчанию для прочих ошибок
    }
}
