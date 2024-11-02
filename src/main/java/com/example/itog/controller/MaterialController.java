package com.example.itog.controller;

import com.example.itog.model.Material;
import com.example.itog.model.Supplier;
import com.example.itog.repositories.MaterialRepository;
import com.example.itog.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/materials")
public class MaterialController {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    // Отображение списка материалов
    @GetMapping
    public String listMaterials(Model model) {
        List<Material> materials = materialRepository.findAll();
        model.addAttribute("materials", materials);
        return "material/list"; // имя шаблона для списка материалов
    }

    // Показать форму для добавления нового материала
    @GetMapping("/new")
    public String showAddMaterialForm(Model model) {
        model.addAttribute("material", new Material());
        model.addAttribute("suppliers", supplierRepository.findAll()); // Передаем список поставщиков
        return "material/form"; // имя шаблона для формы
    }

    // Обработать форму для добавления нового материала
    @PostMapping
    public String addMaterial(@ModelAttribute Material material) {

        materialRepository.save(material);

        return "redirect:/materials"; // перенаправление на список материалов
    }

    // Показать форму для редактирования материала
    @GetMapping("/{id}/edit")
    public String editMaterial(@PathVariable Long id, Model model) {
        Optional<Material> optionalMaterial = materialRepository.findById(id);
        if (optionalMaterial.isPresent()) {
            model.addAttribute("material", optionalMaterial.get());
            model.addAttribute("suppliers", supplierRepository.findAll()); // Передаем список поставщиков
        } else {
            return "redirect:/materials"; // Или показываем сообщение об ошибке
        }
        return "material/form"; // возвращаем форму для редактирования
    }

    // Обработать форму для редактирования материала
    @PostMapping("/{id}")
    public String updateMaterial(@PathVariable Long id, @ModelAttribute Material material, @RequestParam Long supplierId) {
        Optional<Material> existingMaterialOpt = materialRepository.findById(id);
        Optional<Supplier> supplierOpt = supplierRepository.findById(supplierId);

        // Проверяем, что материал и поставщик существуют
        if (existingMaterialOpt.isPresent() && supplierOpt.isPresent()) {
            Material existingMaterial = existingMaterialOpt.get();
            Supplier supplier = supplierOpt.get();

            // Обновляем только поля, которые были изменены
            existingMaterial.setMaterialName(material.getMaterialName());
            existingMaterial.setMaterialCost(material.getMaterialCost());
            existingMaterial.setSupplier(supplier);

            // Сохраняем обновленный объект
            materialRepository.save(existingMaterial);
        }

        return "redirect:/materials"; // Перенаправление на список материалов
    }

    // Удалить материал
    @GetMapping("/{id}/delete")
    public String deleteMaterial(@PathVariable Long id) {
        materialRepository.deleteById(id);
        return "redirect:/materials"; // перенаправление на список материалов
    }
}
