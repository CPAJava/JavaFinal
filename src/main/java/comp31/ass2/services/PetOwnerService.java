package comp31.ass2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import comp31.ass2.model.entity.Pet;
import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.repos.PetOwnerRepo;
import comp31.ass2.repos.PetsRepo;

@Service
public class PetOwnerService {
    PetOwnerRepo petOwnerRepo;
    PetsRepo petsRepo;

    public PetOwnerService(PetOwnerRepo petOwnerRepo, PetsRepo petsRepo) {
        this.petOwnerRepo = petOwnerRepo;
        this.petsRepo = petsRepo;
    }

    public Pet findPetById(Integer petId) {
        return petsRepo.findById(petId).orElse(null);
    }

    // check if preference is set
    public Boolean preferenceIsSet(PetOwner petOwner) {
        return petOwner.getPreference();
    }

    // set pet owner preference when form submited
    public void setPreferences(PetOwner petOwner, Pet preferredPet) {
        petOwner.setPreference(true);
        petOwner.setPreferredPet(preferredPet);
        petOwnerRepo.save(petOwner);

    }

    public List<Pet> findPreferredPets(PetOwner petOwner) {
        String preferredSpecies = petOwner.getPreferredPet().getPetSpecies();
        String preferredColor = petOwner.getPreferredPet().getPetColor();
        String preferredSize = petOwner.getPreferredPet().getPetSize();

        return petsRepo.findPetsByPetSpeciesAndPetColorAndPetSize(preferredSpecies, preferredColor, preferredSize);
    }

    public void adoptPet(PetOwner petOwner, Pet adoptedPet) {
        // Set the pet owner for the adopted pet
        adoptedPet.setPetOwner(petOwner);

        // Update the pet's status or any other logic you need
        adoptedPet.setStatus("pending");

        // Save the changes to the database
        petsRepo.save(adoptedPet);
    }
}
