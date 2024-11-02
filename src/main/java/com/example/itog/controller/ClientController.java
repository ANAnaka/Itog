package com.example.itog.controller;

import com.example.itog.model.Client;
import com.example.itog.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientService;

    @GetMapping
    public String listClients(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "client/list";
    }

    @GetMapping("/new")
    public String showAddClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "client/form";
    }

    @PostMapping
    public String addClient(@ModelAttribute Client client) {
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/{id}/edit")
    public String editClient(@PathVariable Long id, Model model) {
        Optional<Client> optionalClient = clientService.findById(id);
        if (optionalClient.isPresent()) {
            model.addAttribute("client", optionalClient.get());
        } else {
            return "redirect:/clients";
        }
        return "client/form";
    }


    @PostMapping("/{id}")
    public String updateClient(@PathVariable Long id, @ModelAttribute Client client) {
        client.setId(id);
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/{id}/delete")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
        return "redirect:/clients"; // перенаправление на список клиентов
    }
}
