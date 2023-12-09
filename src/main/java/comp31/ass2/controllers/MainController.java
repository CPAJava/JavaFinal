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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // get the status para and find the pets based on status
    model.addAttribute("status", status);
    List<Pet> filteredPets = employeeService.findByAdoptStatus(status);
    logger.info("pets", filteredPets);
    model.addAttribute("filteredPets", filteredPets);

    // get petOwner
    List<PetOwner> filteredOwner = employeeService.findOwnerByStatus(ownerStatus);
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

  @GetMapping("/notApprovedPage")
  public String showNotApprovedOrIncorrectPage() {
    return "notApprovedOrIncorrect";
  }

  @GetMapping(value = { "/owner", "/employee" })
  public String showLoginPage(Model model, @RequestParam(name = "type", required = false) String loginType,
      RedirectAttributes redirectAttributes) {
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

    // handling error message
    String errorMessage = (String) redirectAttributes.getFlashAttributes().get("error");
    if (errorMessage != null) {
      model.addAttribute("error", errorMessage);
    }
    return "login";
  }

  @PostMapping("/login")
  public String getForm(@ModelAttribute("petOwner") PetOwner petOwner,
      @ModelAttribute("employee") Employee employee,
      Model model,
      RedirectAttributes redirectAttributes,
      HttpSession session, @RequestParam(name = "type", required = false) String loginType) {
    String returnPage = "login";
    if ("owner".equals(loginType)) {
      // Make sure the status of the current user passed in
      PetOwner currenPetOwner = loginService.findByUserId(petOwner.getUserId());
      try {
        returnPage = loginService.getValidForm(petOwner);
        if (returnPage.equals("redirect:/petOwner")) {
          session.removeAttribute("currentPetOwner");
          session.removeAttribute("currentEmployee");
          session.setAttribute("currentPetOwner", loginService.findByUserId(currenPetOwner.getUserId()));

        }

      } catch (DataIntegrityViolationException e) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/owner?type=owner";
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
  public String getPetOwnerPage(Model model, PetOwner petOwner, HttpSession session) {
    // Retrieve the current owner from the session
    PetOwner currentPetOwner = (PetOwner) session.getAttribute("currentPetOwner");
    Boolean isPreferenceSet = petOwnerService.preferenceIsSet(currentPetOwner);
    List<Pet> pendingPets = new ArrayList<>();
    List<Pet> availablePets = new ArrayList<>();

    // System.out.println(currentPetOwner.getPreferredType());

    model.addAttribute("isPreferenceSet", isPreferenceSet);
    if (isPreferenceSet) {
      // Pet preferences are set, show the pet owner page
      List<Pet> pets = petOwnerService.findPreferredPets(petOwnerService.findPreferedType(currentPetOwner));
      // List<Pet> pets = currentPetOwner.getPets();

      // !!!!!!!!!Need to move to pet services
      for (Pet pet : pets) {

        if ("pending".equals(pet.getAdoptStatus())
            && currentPetOwner.getUserId().equals(pet.getPetOwner().getUserId())) {
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

  // ******************************** Staff/Pets related controllers*****************************
  /*
* Author: Xuancheng Li
* Date: 2023-12-06
*
* The following controller methods handle the staff related pages requests such as search-display 
* all pets basic information, and add new pets, delete existings, as well as update pets 
* information. 
*/

//Provide staff main page with a display of all current pets with their information
//also handle the functions to add a pet
  @GetMapping({ "/showAllPets", "/add-pet" })
  public String getStaffMain(@RequestParam(defaultValue = "all") String selectedSpecies,
      @RequestParam(defaultValue = "") String staff, Model model) {

    model.addAttribute("pet", new Pet());
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
//Get new pets information and save as a new pet into database
  @PostMapping("/add-pet")
  public String postRegister(Model model, Pet newPet, @RequestParam(defaultValue = "") String userId) {

    Employee employeeInCharge = staffService.findByUserId(userId);
    
    newPet.setEmployee(employeeInCharge);
    newPet.setAdoptStatus("available");
    staffService.insertPet(newPet);
    
    return "redirect:/add-pet";
  }
//delete one pet by its name
  @GetMapping("/delete-pet")
  public String deletePetByName(Model model, @RequestParam(defaultValue = "") String petName) {
    
    staffService.deleteByPetName(petName);
    model.addAttribute("pet", new Pet());

    return "redirect:/add-pet";
  }
//find pet by its name and then update its information
  @GetMapping({ "/updatePetPage", "/find-pet" })
  public String findPetByName(Model model, @RequestParam(defaultValue = "") String petName) {

    model.addAttribute("petName", petName);
    model.addAttribute("ownerAll", staffService.findAllOwners());
    Pet found = staffService.findByPetName(petName);

    if (petName.isEmpty()) {
      Pet empty = new Pet();
      model.addAttribute("found", empty);
      model.addAttribute("StaffInCharge", "");
      model.addAttribute("currentOwner", " ");
    } else {
      if (found == null) {
        Pet foundEmpty = new Pet();
        model.addAttribute("found", foundEmpty);
        model.addAttribute("StaffInCharge", "");
        model.addAttribute("currentOwner", "-");
      } else {
        PetOwner owner = found.getPetOwner();
        if (owner == null) {
          model.addAttribute("found", found);
          model.addAttribute("StaffInCharge", found.getEmployee().getUserId());
          model.addAttribute("currentOwner", "-");
        } else {
          model.addAttribute("found", found);
          model.addAttribute("StaffInCharge", found.getEmployee().getUserId());
          model.addAttribute("currentOwner", found.getPetOwner().getUserId());
        }
      }
    }
    
    return "updatePets";
  }
//Post mapping to grab updated pets information from staff inputs
  @PostMapping("/update-pet")
  public String updatePetInfo(Model model, Pet newPet, @RequestParam(defaultValue = "") String ownerId,
      @RequestParam(defaultValue = "") String staffId) {

    model.addAttribute("found", new Pet());
    PetOwner updatedPetOwner = staffService.findOwnerByUserId(ownerId);
    Employee updatedEmployee = staffService.findByUserId(staffId);
    newPet.setPetOwner(updatedPetOwner);
    newPet.setEmployee(updatedEmployee);
    staffService.insertPet(newPet);
  
    return "redirect:/find-pet";
  }
}
