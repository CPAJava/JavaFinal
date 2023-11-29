package comp31.ass2.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import comp31.ass2.model.entity.Pet;
import comp31.ass2.repos.EmployeeRepo;
import comp31.ass2.repos.PetsRepo;

//This StaffService is added by Xuancheng to support Staff user cases/application functions

@Service
public class StaffService {

    EmployeeRepo employeeRepo;
    PetsRepo petsRepo;

    public StaffService(EmployeeRepo employeeRepo, PetsRepo petsRepo) {
        this.employeeRepo = employeeRepo;
        this.petsRepo = petsRepo;
    }

    public List<Pet> findAllPets() {
        return petsRepo.findAll();
    }
    
    public List<Pet> findBypetName(String petName) {
        return petsRepo.findByPetName(petName);
    }

    public void insertPet(Pet newPet) {
        petsRepo.save(newPet);
    }

    public void deleteByPetName(String delName) {
        List<Pet> tempPets = petsRepo.findByPetName(delName);
        System.out.println(tempPets);
        Pet tempPet = tempPets.get(0);
        Integer tempPetId = tempPet.getId();
        petsRepo.deleteById(tempPetId);
    }

    public List<Pet> findByPetSpecies(String species) {
        return petsRepo.findByPetSpecies(species);
    }

    public ArrayList<String> findAllPetSpecies() {

        List<Pet> allPetsList = petsRepo.findAll();
        HashMap<String, Integer> hashmap = new HashMap<String, Integer>();

        for (int j = 0; j < allPetsList.size(); j++) {
            allPetsList.get(j).getPetSpecies();
            hashmap.put(allPetsList.get(j).getPetSpecies(), j);
        }
        ArrayList<String> allPetSpecies = new ArrayList<>(hashmap.keySet());

        return allPetSpecies;
    }

    // public void updatePetByName(String petName) {
    //     List<Pet> updateOne = findBypetName(petName);

    // }
}
