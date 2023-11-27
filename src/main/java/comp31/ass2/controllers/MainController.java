package comp31.ass2.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pets;
import comp31.ass2.services.EmployeeService;

@Controller
public class MainController {
  EmployeeService employeeService;

  public MainController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/")
  public String getRoot() {
    return "index";
  }

  @GetMapping("/manager")
  public String getRequest(Model model, String status,String searchEmployee) { 
    //get all employees
    model.addAttribute("employee", new Employee());
    List<Employee> employees = employeeService.findAllEmployees();
    model.addAttribute("employees", employees);

    //get the status para and find the pets based on status
    model.addAttribute("status", status);
    List<Pets> filteredPets = employeeService.findByAdoptStatus(status);
    model.addAttribute("filteredPets", filteredPets);

    //get only the pending pets
    List<Pets> pendingPets = employeeService.findByAdoptStatus("pending");
    model.addAttribute("pendingPets", pendingPets);

    //get all pets
    List<Pets> allPets = employeeService.findAllPets();
    model.addAttribute("allPets", allPets);

    //get pets based on the employee lastname
    model.addAttribute("searchEmployee", searchEmployee);
    List<Pets> petsByEmp = employeeService.findPetsByEmployeeLastName(searchEmployee);
    model.addAttribute("petsByEmp", petsByEmp);

    return "manager";
  }

  @PostMapping("/add-employee") // Post new added employee data and redirect to /employee
  public String postAddEmployee(Employee newEmployee) {
    employeeService.addEmployee(newEmployee);
    return "redirect:/manager";
  }
}
