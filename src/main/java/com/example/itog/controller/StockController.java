package com.example.itog.controller;

import com.example.itog.model.Stock;
import com.example.itog.repositories.StockRepository;
import com.example.itog.model.Material;
import com.example.itog.repositories.MaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @GetMapping()
    @PreAuthorize("hasAuthority('SUPPLIER')")
    public String listStock(Model model) {
        List<Stock> stockList = stockRepository.findAll();
        model.addAttribute("stocks", stockList);
        return "stock/list";
    }

    // Show form to add a new stock item
    @GetMapping("/new")
    public String showAddStockForm(Model model) {
        model.addAttribute("stock", new Stock());
        model.addAttribute("materials", materialRepository.findAll()); // Load materials for dropdown
        return "stock/form";
    }

    // Handle form submission for adding a new stock item
    @PostMapping
    public String addStock(@ModelAttribute Stock stock) {
        stockRepository.save(stock);
        return "redirect:/stock";
    }

    // Show form to edit a stock item
    @GetMapping("/{id}/edit")
    public String editStock(@PathVariable Long id, Model model) {
        Optional<Stock> optionalStock = stockRepository.findById(id);
        if (optionalStock.isPresent()) {
            model.addAttribute("stock", optionalStock.get());
            model.addAttribute("materials", materialRepository.findAll()); // Load materials for dropdown
            return "stock/form";
        } else {
            return "redirect:/stock"; // Redirect if stock item not found
        }
    }

    // Handle form submission for updating a stock item
    @PostMapping("/{id}")
    public String updateStock(@PathVariable Long id, @ModelAttribute Stock stock) {
        stock.setId(id); // Ensure ID is set for updating
        stockRepository.save(stock);
        return "redirect:/stock";
    }

    // Delete a stock item
    @GetMapping("/{id}/delete")
    public String deleteStock(@PathVariable Long id) {
        stockRepository.deleteById(id);
        return "redirect:/stock";
    }
}
