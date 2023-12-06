package comp31.ass2.services;

import java.util.List;
import org.springframework.stereotype.Service;
import comp31.ass2.model.entity.Pet;
import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.model.entity.PetPreferences;
import comp31.ass2.repos.PetOwnerRepo;
import comp31.ass2.repos.PetPreferencesRepo;
import comp31.ass2.repos.PetsRepo;

@Service
public class PetOwnerService {
    PetOwnerRepo petOwnerRepo;
    PetsRepo petsRepo;
    PetPreferencesRepo petPreferencesRepo;

    public PetOwnerService(PetOwnerRepo petOwnerRepo, PetsRepo petsRepo, PetPreferencesRepo petPreferencesRepo) {
        this.petOwnerRepo = petOwnerRepo;
        this.petsRepo = petsRepo;
        this.petPreferencesRepo = petPreferencesRepo;
    }

    // ****MOVE TO PET SERVICE!!!!! */
    public Pet findPetById(Integer petId) {
        return petsRepo.findById(petId).orElse(null);
    }

    // check if preference is set
    public Boolean preferenceIsSet(PetOwner petOwner) {
        return petOwner.getPreference();
    }

    // set pet owner preference when form submited
    public void setPreferences(PetOwner petOwner, Pet preferredPet) {

        PetPreferences petPreferences = new PetPreferences();
        petPreferences.setPreferredSpecies(preferredPet.getPetSpecies());
        petPreferences.setPreferredColor(preferredPet.getPetColor());
        petPreferences.setPreferredSize(preferredPet.getPetSize());

        petOwner.setPreference(true);

        petOwner.setPetPreferences(petPreferences);
        petPreferencesRepo.save(petPreferences);
        petOwnerRepo.save(petOwner);

    }

    public List<Pet> findPreferredPets(PetPreferences petPreferences) {

        String preferredSpecies = petPreferences.getPreferredSpecies();
        String preferredColor = petPreferences.getPreferredColor();
        String preferredSize = petPreferences.getPreferredSize();

        return petsRepo.findPetsByPetSpeciesAndPetColorAndPetSize(preferredSpecies, preferredColor, preferredSize);
    }

    public void adoptPet(PetOwner petOwner, Pet adoptedPet) {

        // List<Pet> pets = petOwner.getPets();
        List<Pet> pets = findPreferredPets(petOwner.getPetPreferences());
        // Update the pet's status
        for (Pet pet : pets) {
            // Set the pet owner for the adopted pet
            if (adoptedPet.getId() == pet.getId()) {
                pet.setPetOwner(petOwner);
                pet.setAdoptStatus("pending");
                petsRepo.save(pet);
            }
        }

        petOwner.setPets(pets);

    }

    public PetPreferences findPreferedType(PetOwner currentOwner) {
        return currentOwner.getPetPreferences();
    }

}
