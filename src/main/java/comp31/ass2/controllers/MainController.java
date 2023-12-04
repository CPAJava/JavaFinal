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
import comp31.ass2.services.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
  EmployeeService employeeService;

  LoginService loginService;
  RegisterService registerService;
  PetOwnerService petOwnerService;
  //Adding staffService by Xuancheng
  StaffService staffService;

  Logger logger = LoggerFactory.getLogger(EmployeeService.class);

  public MainController(EmployeeService employeeService, LoginService loginService,
      RegisterService registerService, PetOwnerService petOwnerService, StaffService staffService) {
    this.employeeService = employeeService;
    this.loginService = loginService;
    this.registerService = registerService;
    this.petOwnerService = petOwnerService;
    this.staffService =staffService;
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
    return "redirect:/petOwner";
  }

  @GetMapping("/petOwner")
  public String getPetOwnerPage(Model model, PetOwner petOwner, HttpSession session, Pet preferredPet) {
    // Retrieve the current owner from the session
    PetOwner currentPetOwner = (PetOwner) session.getAttribute("currentPetOwner");
    Boolean isPreferenceSet = petOwnerService.preferenceIsSet(currentPetOwner);
    List<Pet> pendingPets = new ArrayList<>();
    model.addAttribute("isPreferenceSet", isPreferenceSet);
    if (isPreferenceSet) {
      // Pet preferences are set, show the pet owner page
      List<Pet> pets = petOwnerService.findPreferredPets(currentPetOwner);
      model.addAttribute("pets", pets);

      for (Pet pet : pets) {
        if (pet.getPetOwner() != null && pet.getPetOwner().equals(currentPetOwner)
            && "pending".equals(pet.getAdoptStatus())) {
          pendingPets.add(pet);
        }
      }
      if (pendingPets != null) {
        model.addAttribute("pendingPets", pendingPets);
      }

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

  // ********************************************** Staff/Pets related controllers
  // (Xuancheng):
  //getStaffMain() was modified from getRoot() controller
  @GetMapping({"/showAllPets", "/add-pet"})
    public String getStaffMain(@RequestParam(defaultValue = "all") String selectedSpecies, @RequestParam(defaultValue = "") String staff, Model model) {
        model.addAttribute("pet", new Pet());
        // List<Pets> pets = staffService.findAllPets();
        // model.addAttribute("allPets", pets); 
        model.addAttribute("speciesAll", staffService.findAllPetSpecies());
        model.addAttribute("staffAll", staffService.findAllStaffs());
        if (selectedSpecies.equals("all")) {
            model.addAttribute("allPets", staffService.findAllPets());
            model.addAttribute("title", "All");
        } else {
            model.addAttribute("allPets", staffService.findByPetSpecies(selectedSpecies));
            model.addAttribute("title", selectedSpecies);
        }
        model.addAttribute("staffInCharge", staffService.findByUserId(staff));

        return "pets";
    }

    @PostMapping("/add-pet")
    public String postRegister(Model model, Pet newPet, @RequestParam(defaultValue = "") String userId) {

        //model.addAttribute("pet", new Pet());

        Employee employeeInCharge = staffService.findByUserId(userId);
        //PetOwner dummyOwner = new PetOwner("dummy","dummy","dummy","dummy","dummy","dummy",false);
        //dummyOwner.setPreferredPet(newPet);
        System.out.println(userId);
        //System.out.println(dummyOwner);
        newPet.setEmployee(employeeInCharge);
        newPet.setAdoptStatus("available");
        //newPet.setPetOwner(dummyOwner);
        staffService.insertPet(newPet);
        System.out.println(newPet);
        return "redirect:/add-pet";
    }

    @GetMapping("/delete-pet")
    public String deletePetByName(Model model, @RequestParam(defaultValue = "") String petName) {
        staffService.deleteByPetName(petName);
        model.addAttribute("pet", new Pet());

        return "redirect:/add-pet";
    }

    @GetMapping({"/updatePetPage", "/find-pet"})
    public String findPetByName(Model model, @RequestParam(defaultValue = "") String petName) {
        //logger.info("pets------", petName);
        // System.out.println(petName);
        model.addAttribute("petName", petName);
        //List<Pets> foundPet = staffService.findBypetName(petName);
        //logger.info("pets------", foundPet);
        if (petName.isEmpty()) {
            Pet found = new Pet();
            model.addAttribute("found", found);
        }
        else {
        Pet found = new Pet();
        found = staffService.findByPetName(petName); 
        model.addAttribute("found", found);
        }
        return "updatePets";
    }

    @PostMapping("/update-pet")
    public String updatePetInfo(Model model, Pet newPet) {

        model.addAttribute("found", new Pet());
        staffService.insertPet(newPet);

        return "redirect:/find-pet";
    }
}
