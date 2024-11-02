package com.example.itog.controller;

import com.example.itog.model.Order;
import com.example.itog.model.Client; // Импортируйте модель Client
import com.example.itog.repositories.OrderRepository; // Импортируйте репозиторий для Order
import com.example.itog.repositories.ClientRepository; // Импортируйте репозиторий для Client
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository; // Репозиторий для работы с заказами

    @Autowired
    private ClientRepository clientRepository; // Репозиторий для работы с клиентами

    // Отображение списка заказов
    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "order/list"; // имя шаблона для списка заказов
    }

    // Показать форму для добавления нового заказа
    @GetMapping("/new")
    public String showAddOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("clients", clientRepository.findAll()); // Получаем список клиентов для выпадающего списка
        return "order/form"; // имя шаблона для формы
    }

    // Обработать форму для добавления нового заказа
    @PostMapping
    public String addOrder(@ModelAttribute Order order) {
        order.setOrderDate(new Date()); // Устанавливаем дату заказа
        orderRepository.save(order);
        return "redirect:/orders"; // перенаправление на список заказов
    }

    // Показать форму для редактирования заказа
    @GetMapping("/{id}/edit")
    public String editOrder(@PathVariable Long id, Model model) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            model.addAttribute("order", optionalOrder.get());
            model.addAttribute("clients", clientRepository.findAll()); // Список клиентов для выбора
        } else {
            return "redirect:/orders"; // или показать сообщение об ошибке
        }
        return "order/form"; // возвращаем форму для редактирования
    }

    // Обработать форму для редактирования заказа
    @PostMapping("/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute Order order) {
        order.setId(id); // установить id для обновления
        orderRepository.save(order);
        return "redirect:/orders"; // перенаправление на список заказов
    }

    // Удалить заказ
    @GetMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "redirect:/orders"; // перенаправление на список заказов
    }
}
