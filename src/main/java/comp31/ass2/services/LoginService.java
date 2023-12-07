package comp31.ass2.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pet;
import comp31.ass2.repos.EmployeeRepo;
import comp31.ass2.repos.PetOwnerRepo;
import comp31.ass2.repos.PetsRepo;
/*
 * Author: Manlin Mao
 * Date: 2023-12-06
 *
 * Class/File: LoginService.java
 *
 * Additional context:
 * The "LoginService" class is a service component in the application responsible for handling user authentication
 * and authorization. It interacts with the "PetOwnerRepo," "PetsRepo," and "EmployeeRepo" repositories to perform
 * operations related to pet owners and employees.
 *
 * The service includes methods for retrieving all pets, finding a pet owner by user ID, finding an employee by user ID,
 * and validating user credentials for both pet owners and employees. The validation process involves checking the
 * provided credentials against stored data in the repositories and redirecting users to specific pages based on their
 * roles and approval status.
 *
 * This class plays a crucial role in ensuring secure access to different parts of the application based on user roles
 * and status, contributing to the overall functionality of the login and authentication system.
 */

@Service
public class LoginService {
    PetOwnerRepo petOwnerRepo;
    PetsRepo petsRepo;
    EmployeeRepo employeeRepo;

    public LoginService(PetOwnerRepo petOwnerRepo, PetsRepo petsRepo, EmployeeRepo employeeRepo) {
        this.petOwnerRepo = petOwnerRepo;
        this.petsRepo = petsRepo;
        this.employeeRepo = employeeRepo;
    }

    public List<Pet> findAll() {
        return petsRepo.findAll();
    }

    // Retriving an petOwner by using provided userId
    public PetOwner findByUserId(String ownerId) {
        return petOwnerRepo.findByUserId(ownerId);
    }

    public Employee findByEmpId(String userId) {
        return employeeRepo.findByUserId(userId);
    }

    // Validate user credentials and return the corresponding user or login
    // page
    public String getValidForm(PetOwner petOwner) {
         PetOwner currentOwner = findByUserId(petOwner.getUserId());
      
        if (currentOwner != null && petOwner.getPassword().equals(currentOwner.getPassword())) {
            if ("approved".equals(currentOwner.getStatus())) {
                return "redirect:/petOwner";
            }else{
                return "redirect:/notApprovedPage";
            }
        }else{
             throw new DataIntegrityViolationException(
                    "User with ID " + petOwner.getUserId() + " password incorrect/user does not exist, please try again.");
        }
           
    }

    public String getValidForm(Employee employee) {
        Employee currentEmployee = findByEmpId(employee.getUserId());

        if (currentEmployee != null && employee.getPassword().equals(currentEmployee.getPassword())) {
            if (currentEmployee.getPosition() == "manager") {
                return "redirect:/manager";
            }
            //Tom
            else if (currentEmployee.getPosition() == "office") {
                return "redirect:/showAllPets";
            }
            //Tom
        }
        return "login";
    }

}