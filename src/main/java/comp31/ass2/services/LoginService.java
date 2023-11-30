package comp31.ass2.services;

import java.util.List;

import org.springframework.stereotype.Service;

import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pet;
import comp31.ass2.repos.EmployeeRepo;
import comp31.ass2.repos.PetOwnerRepo;
import comp31.ass2.repos.PetsRepo;

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

    // Validate user credentials and return the corresponding department or login
    // page
    public String getValidForm(PetOwner petOwner) {
        PetOwner currentOwner = findByUserId(petOwner.getUserId());
        ;
        if (currentOwner != null && petOwner.getPassword().equals(currentOwner.getPassword())) {
            if ("approved".equals(currentOwner.getStatus())) {
                // if (currentOwner.getPreference()) {

                return "redirect:/petOwner";
                // } else {
                // return "redirect:/setPreferences";
                // }

            }

        }
        return "login";

    }

    public String getValidForm(Employee employee) {
        Employee currentEmployee = findByEmpId(employee.getUserId());

        if (currentEmployee != null && employee.getPassword().equals(currentEmployee.getPassword())) {
            if (currentEmployee.getPosition() == "manager") {
                return "redirect:/manager";
            }

        }
        return "login";
    }

}