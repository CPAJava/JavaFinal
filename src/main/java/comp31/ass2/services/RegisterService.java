package comp31.ass2.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.repos.PetOwnerRepo;
/*
 * Author: Manlin Mao
 * Date: 2023-12-06
 *
 * Class/File: RegisterService.java
 *
 * Additional context:
 * The "RegisterService" class is a service component in the application responsible for handling the registration
 * of new pet owners. It interacts with the "PetOwnerRepo" repository to perform operations related to pet owner
 * registration, validation, and retrieval.
 *
 * The service includes methods such as "registerPetOwner" for registering a new pet owner, "isUserExists" for checking
 * if a user with a given ID already exists, and "findOwner" for retrieving pet owner details by user ID. The registration
 * process ensures data integrity by checking for duplicate user IDs before saving the new pet owner to the repository.
 *
 * This class plays a crucial role in facilitating the registration of pet owners, contributing to the overall functionality
 * of the application's user management system.
 */

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
