package com.example.magnus.controller;

import com.example.magnus.model.Employee;
import com.example.magnus.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service){ this.service = service; }

    @GetMapping("/create")
    public String createForm(Model m){ m.addAttribute("employee", new Employee()); return "employee/form"; }

    @PostMapping("/create")
    public String create(@Valid Employee employee){ service.create(employee); return "redirect:/employee/search"; }

    @GetMapping("/search" )
    public String search(Model m){ m.addAttribute("employees", service.list()); return "employee/list"; }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable UUID id, Model m){ m.addAttribute("employee", service.get(id)); return "employee/form"; }

    @PostMapping("/{id}/update")
    public String update(@PathVariable UUID id, @Valid Employee employee){ service.update(id, employee); return "redirect:/employee/search"; }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id){ service.softDelete(id); return "redirect:/employee/search"; }

    @PostMapping("/bulk-delete")
    @ResponseBody
    public String bulkDelete(@RequestBody List<UUID> ids){ int c = service.bulkSoftDelete(ids); return "deleted="+c; }

    @PostMapping("/{id}/restore")
    @ResponseBody
    public String restore(@PathVariable UUID id){ service.restore(id); return "ok"; }
}
