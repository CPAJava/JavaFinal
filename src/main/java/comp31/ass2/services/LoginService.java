package comp31.ass2.services;

import java.util.List;

import org.springframework.stereotype.Service;


import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.model.entity.Pet;
import comp31.ass2.repos.PetOwnerRepo;
import comp31.ass2.repos.PetsRepo;

@Service
public class LoginService {
    PetOwnerRepo petOwnerRepo;
    PetsRepo petsRepo;

    public LoginService(PetOwnerRepo petOwnerRepo, PetsRepo petsRepo) {
        this.petOwnerRepo = petOwnerRepo;
        this.petsRepo = petsRepo;
    }

    public List<Pet> findAll() {
        return petsRepo.findAll();
    }

    // Retriving an petOwner by using provided userId
    public PetOwner findByUserId(String ownerId) {
        return petOwnerRepo.findByUserId(ownerId);
    }

    // Validate user credentials and return the corresponding department or login
    // page
    public String getValidForm(PetOwner petOwner) {
        PetOwner currentOwner = findByUserId(petOwner.getUserId());
        ;
        if (currentOwner != null && petOwner.getPassword().equals(currentOwner.getPassword())) {
            if (currentOwner.getStatus() == "approved") {
                // if (currentOwner.getPreference()) {

                return "redirect:/petOwner";
                // } else {
                // return "redirect:/setPreferences";
                // }

            }

        }
        return "ownerLogin";

    }

}