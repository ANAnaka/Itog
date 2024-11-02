package com.example.itog.controller;

import com.example.itog.model.Supplier;
import com.example.itog.repositories.SupplierRepository; // Ensure you have this repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    // Display list of suppliers
    @GetMapping
    public String listSuppliers(Model model) {
        List<Supplier> suppliers = supplierRepository.findAll();
        model.addAttribute("suppliers", suppliers);
        return "supplier/list"; // name of the template for supplier list
    }

    // Show form to add a new supplier
    @GetMapping("/new")
    public String showAddSupplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier/form"; // name of the template for the form
    }

    // Process the form to add a new supplier
    @PostMapping
    public String addSupplier(@ModelAttribute Supplier supplier) {
        supplierRepository.save(supplier);
        return "redirect:/suppliers"; // redirect to the list of suppliers
    }

    // Show form to edit a supplier
    @GetMapping("/{id}/edit")
    public String editSupplier(@PathVariable Long id, Model model) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            model.addAttribute("supplier", optionalSupplier.get());
        } else {
            return "redirect:/suppliers"; // or handle error appropriately
        }
        return "supplier/form"; // return form for editing
    }

    // Process the form to update a supplier
    @PostMapping("/{id}")
    public String updateSupplier(@PathVariable Long id, @ModelAttribute Supplier supplier) {
        supplier.setId(id); // set id for updating
        supplierRepository.save(supplier);
        return "redirect:/suppliers"; // redirect to the list of suppliers
    }

    // Delete a supplier
    @GetMapping("/{id}/delete")
    public String deleteSupplier(@PathVariable Long id) {
        supplierRepository.deleteById(id);
        return "redirect:/suppliers"; // redirect to the list of suppliers
    }
}
