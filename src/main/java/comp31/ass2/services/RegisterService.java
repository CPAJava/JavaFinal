package comp31.ass2.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.repos.PetOwnerRepo;

@Service
public class RegisterService {

    PetOwnerRepo petOwnerRepo;

    public RegisterService(PetOwnerRepo petOwnerRepo) {
        this.petOwnerRepo = petOwnerRepo;
    }

    public void registerPetOwner(PetOwner petOwner) {
        // Check if the user already exists when register
        if (isUserExists(petOwner.getUserId())) {
            throw new DataIntegrityViolationException(
                    "User with ID " + petOwner.getUserId() + " already exists. Please try another one.");
        }
        // Save the new PetOwner if the user doesn't exist
        petOwnerRepo.save(petOwner);
    }

    public boolean isUserExists(String userId) {
        return petOwnerRepo.findByUserId(userId) != null;
    }

    public PetOwner findOwner(String userId) {
        return petOwnerRepo.findByUserId(userId);
    }
}
