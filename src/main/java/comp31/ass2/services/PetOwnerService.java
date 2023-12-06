package comp31.ass2.services;

import java.util.List;

import org.springframework.stereotype.Service;

import comp31.ass2.model.entity.Pet;
import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.repos.PetOwnerRepo;
import comp31.ass2.repos.PetsRepo;
import jakarta.transaction.Transactional;

@Service
public class PetOwnerService {
    PetOwnerRepo petOwnerRepo;
    PetsRepo petsRepo;

    public PetOwnerService(PetOwnerRepo petOwnerRepo, PetsRepo petsRepo) {
        this.petOwnerRepo = petOwnerRepo;
        this.petsRepo = petsRepo;
    }

    //****MOVE TO PET SERVICE!!!!! */
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
        petOwner.setPets(findPreferredPets(preferredPet));
        petOwner.setPreferredType(preferredPet);
        petOwnerRepo.save(petOwner);

    }

    public List<Pet> findPreferredPets(Pet preferredPet) {
        String preferredSpecies = preferredPet.getPetSpecies();
        String preferredColor = preferredPet.getPetColor();
        String preferredSize = preferredPet.getPetSize();

        return petsRepo.findPetsByPetSpeciesAndPetColorAndPetSize(preferredSpecies, preferredColor, preferredSize);
    }

 
    public void adoptPet(PetOwner petOwner, Pet adoptedPet) {

        // List<Pet> pets = petOwner.getPets();
        List<Pet> pets = findPreferredPets(petOwner.getPreferredType());
        // Update the pet's status
        for (Pet pet : pets) {
            // Set the pet owner for the adopted pet
            if (adoptedPet.getId() == pet.getId()) {
                pet.setPetOwner(petOwner);
                pet.setAdoptStatus("pending");
                petsRepo.save(pet);
            }
        }
      
       // petOwner.getPets().add(adoptedPet);
        // adoptedPet.setAdoptStatus("pending");
        // adoptedPet.setPetOwner(petOwner);
        // petsRepo.save(adoptedPet);
        petOwner.setPets(pets);
        // Save the changes to the database
       // petOwnerRepo.save(petOwner);
       
    }
    
}
