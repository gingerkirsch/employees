package com.employees.app.controller;

import com.employees.app.model.Employee;
import com.employees.app.service.EmployeeService;
import com.employees.app.service.FileStorageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @Autowired
    private FileStorageService storage;

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<Employee> employees = service.listAll();
        model.addAttribute("employees", employees);
        return "index";
    }

    @RequestMapping("/new")
    public String newEmployeeForm(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveEmployee(@ModelAttribute("employee") Employee employee){
        service.save(employee);
        return "redirect:/";
    }

    @RequestMapping("/edit/{username}")
    public ModelAndView editEmployeeForm(@PathVariable(name="username") String username){
        ModelAndView mav = new ModelAndView("edit_employee");
        Employee employee = service.getByUsername(username);
        mav.addObject("employee", employee);

        return mav;
    }

    @RequestMapping("/delete/{username}")
    public String deleteEmployeeForm(@PathVariable(name="username") String username){
        Employee employee = service.getByUsername(username);
        service.delete(employee.getId());
        return "redirect:/";
    }

    @SneakyThrows
    @RequestMapping("/photo/{username}")
    public ModelAndView editEmployeeForm(
            @PathVariable(name="username") String username,
            @RequestParam(name="json", required = true) String json,
            @RequestParam(required = true, value = "file")MultipartFile file) {
        String fileName = storage.storeFile(file);
        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("uploads").path(fileName).toUriString();
        ModelAndView mav = new ModelAndView("upload_image");
        Employee employee = service.getByUsername(username);
        employee.setImage(fileUri);
        mav.addObject("employee", employee);
        return mav;
    }
}
