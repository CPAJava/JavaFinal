package comp31.ass2.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pet;
import comp31.ass2.model.entity.PetOwner;

import comp31.ass2.services.EmployeeService;
import comp31.ass2.services.LoginService;
import comp31.ass2.services.PetOwnerService;
import comp31.ass2.services.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
  EmployeeService employeeService;

  LoginService loginService;
  RegisterService registerService;
  PetOwnerService petOwnerService;

  Logger logger = LoggerFactory.getLogger(EmployeeService.class);

  public MainController(EmployeeService employeeService, LoginService loginService,
      RegisterService registerService, PetOwnerService petOwnerService) {
    this.employeeService = employeeService;
    this.loginService = loginService;
    this.registerService = registerService;
    this.petOwnerService = petOwnerService;
  }

  @GetMapping("/")
  public String getRoot() {
    return "index";
  }

  @GetMapping("/manager")
  public String getRequest(Model model, String status, String searchEmployee, String ownerStatus) {
   
    //get the status para and find the pets based on status
    model.addAttribute("status", status);
    List<Pet> filteredPets = employeeService.findByAdoptStatus(status);
    logger.info("pets", filteredPets);
    model.addAttribute("filteredPets", filteredPets);

    //get petOwner
     List<PetOwner> filteredOwner=employeeService.findOwnerByStatus(ownerStatus);
    model.addAttribute("ownerStatus", ownerStatus);
    model.addAttribute("filteredOwner", filteredOwner);

    return "manager";
  }

  @PostMapping("/approve-owner") // Post new added employee data and redirect to /employee
  public String approveOwner(String ownerId, String ownerStatus) {
    employeeService.setOwnerStatus(ownerId, ownerStatus);
    return "redirect:/manager";
  }


  // ********************************************** Pet Owner related controllers
  // (Manlin):

  @GetMapping("success")
  public String showSuccessPage() {
    return "success";
  }

  @GetMapping(value = { "/owner", "/employee" })
  public String showLoginPage(Model model, @RequestParam(name = "type", required = false) String loginType) {
    Boolean isOwner;
    PetOwner petOwner = new PetOwner();
    Employee employee = new Employee();
    if ("owner".equals(loginType)) {
      model.addAttribute("petOwner", petOwner);
      isOwner = true;
    } else {
      model.addAttribute("employee", employee);
      isOwner = false;
    }
    model.addAttribute("isOwner", isOwner);

    return "login";
  }

  @PostMapping("/login")
  public String getForm(@ModelAttribute("petOwner") PetOwner petOwner,
      @ModelAttribute("employee") Employee employee,
      Model model,
      HttpSession session, @RequestParam(name = "type", required = false) String loginType) {
    String returnPage = "index";
    if ("owner".equals(loginType)) {
      // Handle PetOwner login
      returnPage = loginService.getValidForm(petOwner);
      if (returnPage.equals("redirect:/petOwner")) {
        session.setAttribute("currentPetOwner", loginService.findByUserId(petOwner.getUserId()));
      }
    } else if ("employee".equals(loginType)) {
      // Handle Employee login
      returnPage = loginService.getValidForm(employee);
      // Handle Employee login logic
      session.setAttribute("currentEmployee", loginService.findByUserId(employee.getUserId()));
    }

    return returnPage;
  }

  @PostMapping("/petOwner")
  public String setPreferences(Model model, HttpSession session, Pet preferedPet) {
    PetOwner currentPetOwner = (PetOwner) session.getAttribute("currentPetOwner");

    petOwnerService.setPreferences(currentPetOwner, preferedPet);

    return "redirect:/petOwner";
  }

  @PostMapping("/adopt")
  public String setStatus(Model model, HttpSession session, Pet preferedPet,
      @RequestParam(name = "petId", required = false) Integer petId) {
    PetOwner currentPetOwner = (PetOwner) session.getAttribute("currentPetOwner");

    // Find the selected pet by its ID
    Pet adoptedPet = petOwnerService.findPetById(petId);
    // Associate the pet with the current pet owner
    petOwnerService.adoptPet(currentPetOwner, adoptedPet);
    List<Pet> pets = currentPetOwner.getPets();
    pets.size();
    for (Pet pet : pets) {
      System.out.println(pet.getAdoptStatus());
      System.out.println(pet.getPetName());
    }
    return "redirect:/petOwner";
  }

  @GetMapping("/petOwner")
  public String getPetOwnerPage(Model model, PetOwner petOwner, HttpSession session, Pet preferredPet) {
    // Retrieve the current owner from the session
    PetOwner currentPetOwner = (PetOwner) session.getAttribute("currentPetOwner");
    Boolean isPreferenceSet = petOwnerService.preferenceIsSet(currentPetOwner);
    List<Pet> pendingPets = new ArrayList<>();
    List<Pet> availablePets = new ArrayList<>();

    if (currentPetOwner == null) {
      // Handle the case where currentPetOwner is null, e.g., redirect to login page
      return "redirect:/";
    }

    model.addAttribute("isPreferenceSet", isPreferenceSet);
    if (isPreferenceSet) {
      // Pet preferences are set, show the pet owner page
      List<Pet> pets = currentPetOwner.getPets();

      for (Pet pet : pets) {

        if ("pending".equals(pet.getAdoptStatus())) {
          pendingPets.add(pet);
        } else if ("available".equals(pet.getAdoptStatus())) {
          availablePets.add(pet);
        }
      }

      model.addAttribute("pendingPets", pendingPets);
      model.addAttribute("pets", availablePets);

    } else {
      // Pet preferences are not set, show the preference form
      model.addAttribute("preferredPet", new Pet());

    }

    return "petOwner";
  }

  @GetMapping("/register")
  public String getRegister(Model model) {

    model.addAttribute("petOwner", new PetOwner());
    return "register";
  }

  // post petOwner
  @PostMapping("/register")
  public String registerNewPetOwner(Model model, PetOwner newPetOwner) {

    try {
      registerService.registerPetOwner(newPetOwner);
      return "redirect:/success"; // Redirect to a success page
    } catch (DataIntegrityViolationException e) {
      model.addAttribute("error", e.getMessage());
      return "register"; // Return to the registration form with an error message
    }
  }

}
