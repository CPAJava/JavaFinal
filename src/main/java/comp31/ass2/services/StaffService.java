/*
* Author: Xuancheng Li
* Date: 2023-12-06
*
* Class/File: StaffService.java
*
* This Java service class provides all functionalities that office staff will mainly focused about. 
* These functions includes basic CRUD funcions to pet database, which composes the main structure
* of staff main page. 
* 
*/
package comp31.ass2.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pet;
import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.repos.EmployeeRepo;
import comp31.ass2.repos.PetOwnerRepo;
import comp31.ass2.repos.PetsRepo;


@Service
public class StaffService {

    EmployeeRepo employeeRepo;
    PetsRepo petsRepo;
    PetOwnerRepo petOwnerRepo;

    public StaffService(EmployeeRepo employeeRepo, PetsRepo petsRepo, PetOwnerRepo petOwnerRepo) {
        this.employeeRepo = employeeRepo;
        this.petsRepo = petsRepo;
        this.petOwnerRepo =petOwnerRepo;
    }
    //find All Pets from database
    public List<Pet> findAllPets() {
        return petsRepo.findAll();
    }
    //find one pet by its name 
    public Pet findByPetName(String petName) {
        Pet foundPet = null;
        List<Pet> tempFound = petsRepo.findByPetName(petName);
        if (!tempFound.isEmpty()){
            foundPet = tempFound.get(0);
        }        
        return foundPet;
    }
    //insert a new pet/update existing pet
    public void insertPet(Pet newPet) {
        petsRepo.save(newPet);
    }
    //delete one pet by its name
    public void deleteByPetName(String delName) {
        List<Pet> tempPets = petsRepo.findByPetName(delName);
        
        if (!tempPets.isEmpty()) {
            Pet tempPet = tempPets.get(0);
            Integer tempPetId = tempPet.getId();
            petsRepo.deleteById(tempPetId);
        }
    }
    //filter pets by species
    public List<Pet> findByPetSpecies(String species) {
        return petsRepo.findByPetSpecies(species);
    }
    //find all pets' species for staff to choose
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
    //find all staff names
    public ArrayList<String> findAllStaffs() {
        ArrayList<String> allStaffs = new ArrayList<>();
        List<Employee> allEmployee = employeeRepo.findAll();
        for (int i = 0; i < allEmployee.size(); i++) {
            if(allEmployee.get(i).getPosition().equals("office")) {
                allStaffs.add(allEmployee.get(i).getUserId());
            }
        }
        return allStaffs;
    }
    //find staff user id
    public Employee findByUserId(String userId) {
        return employeeRepo.findByUserId(userId);
    }
    //find all owners
    public List<PetOwner> findAllOwners() {
        return petOwnerRepo.findAll();
    }
    //find owner by userid
    public PetOwner findOwnerByUserId(String userId) {
        return petOwnerRepo.findByUserId(userId);
    }
}
