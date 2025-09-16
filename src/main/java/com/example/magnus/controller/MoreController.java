package com.example.magnus.controller;

import com.example.magnus.model.Employee;
import com.example.magnus.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/more")
public class MoreController {

    private final EmployeeService employeeService;
    private final Path uploadDir = Paths.get("uploads");

    public MoreController(EmployeeService employeeService) throws Exception {
        this.employeeService = employeeService;
        if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);
    }

    @GetMapping("/tabs")
    public String tabs(){ return "more/tabs"; }

    @GetMapping("/menu")
    public String menu(){ return "more/menu"; }

    @GetMapping("/autocomplete")
    public String autocomplete(Model m){ m.addAttribute("names", employeeService.list().stream().map(Employee::getName).collect(Collectors.toList())); return "more/autocomplete"; }

    @GetMapping("/collapsible")
    public String collapsible(){ return "more/collapsible"; }

    @GetMapping("/images")
    public String images(Model m){
        try {
            List<String> files = Files.list(uploadDir)
                    .map(Path::getFileName)
                    .map(Object::toString)
                    .collect(Collectors.toList());
            m.addAttribute("files", files);
        } catch (IOException e) {
            e.printStackTrace();
            m.addAttribute("files", List.of()); // empty list on error
        }
        return "more/images";
    }


    @PostMapping("/images/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file,
                              @RequestParam(required = false) String name) {
        if (file != null && !file.isEmpty()) {
            String base = FilenameUtils.getName(file.getOriginalFilename());
            Path dest = uploadDir.resolve(UUID.randomUUID().toString() + "-" + base);
            try {
                Files.copy(file.getInputStream(), dest);
            } catch (IOException e) {
                e.printStackTrace(); // Log the error, or use a logger
            }
        }
        return "redirect:/more/images";
    }


    @GetMapping("/images/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        Path f = uploadDir.resolve(filename);
        try {
            Resource res = new UrlResource(f.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.getFileName().toString() + "\"")
                    .body(res);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/slider")
    public String slider(){ return "more/slider"; }

    @GetMapping("/tooltips")
    public String tooltips(){ return "more/tooltips"; }

    @GetMapping("/popups")
    public String popups(){ return "more/popups"; }

    @GetMapping("/links")
    public String links(){ return "more/links"; }

    @GetMapping("/cssproperties")
    public String cssprops(){ return "more/cssproperties"; }

    @GetMapping("/iframes")
    public String iframes(){ return "more/iframes"; }
}
