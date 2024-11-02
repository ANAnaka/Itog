package com.example.itog.controller;

import com.example.itog.model.OrderItem;
import com.example.itog.repositories.OrderItemRepository;
import com.example.itog.repositories.OrderRepository;
import com.example.itog.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/orderitems")
public class OrderItemController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // Отображение списка заказанных товаров
    @GetMapping
    public String listOrderItems(Model model) {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        model.addAttribute("orderItems", orderItems);
        return "orderitem/list"; // имя шаблона для списка заказанных товаров
    }

    // Показать форму для добавления нового заказанного товара
    @GetMapping("/new")
    public String showAddOrderItemForm(Model model) {
        model.addAttribute("orderItem", new OrderItem());
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "orderitem/form"; // имя шаблона для формы
    }

    // Обработать форму для добавления нового заказанного товара
    @PostMapping
    public String addOrderItem(@ModelAttribute OrderItem orderItem) {
        orderItemRepository.save(orderItem);
        return "redirect:/orderitems"; // перенаправление на список заказанных товаров
    }

    // Показать форму для редактирования заказанного товара
    @GetMapping("/{id}/edit")
    public String editOrderItem(@PathVariable Long id, Model model) {
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(id); // Используйте Optional
        if (optionalOrderItem.isPresent()) {
            model.addAttribute("orderItem", optionalOrderItem.get()); // Добавляем заказанный товар, если он найден
            model.addAttribute("orders", orderRepository.findAll());
            model.addAttribute("products", productRepository.findAll());
        } else {
            return "redirect:/orderitems"; // перенаправляем, если не найден
        }
        return "orderitem/form"; // возвращаем форму для редактирования
    }

    // Обработать форму для редактирования заказанного товара
    @PostMapping("/{id}")
    public String updateOrderItem(@PathVariable Long id, @ModelAttribute OrderItem orderItem) {
        orderItem.setId(id); // установить id для обновления
        orderItemRepository.save(orderItem);
        return "redirect:/orderitems"; // перенаправление на список заказанных товаров
    }

    // Удалить заказанный товар
    @GetMapping("/{id}/delete")
    public String deleteOrderItem(@PathVariable Long id) {
        orderItemRepository.deleteById(id);
        return "redirect:/orderitems"; // перенаправление на список заказанных товаров
    }
}
