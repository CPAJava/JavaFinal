package comp31.ass2.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pet;
import comp31.ass2.repos.EmployeeRepo;
import comp31.ass2.repos.PetsRepo;

@Service
public class EmployeeService {
  EmployeeRepo employeeRepo; // inject through constructor
  PetsRepo petsRepo;
  Logger logger = LoggerFactory.getLogger(EmployeeService.class);

  public EmployeeService(EmployeeRepo employeeRepo, PetsRepo petsRepo) {

    super();
    this.employeeRepo = employeeRepo;
    this.petsRepo = petsRepo;

  }

  public List<Employee> findAllEmployees() {
    return employeeRepo.findAll();
  }

  public Employee findEmployee(String lastName){
          // List<Employee> emp=employeeRepo.findByLastNameIgnoreCase(lastName);
          // System.out.println("emplist: " + emp);
          logger.info("-----------findEmployee",employeeRepo.findByLastNameIgnoreCase("lastName"));

    return employeeRepo.findByLastNameIgnoreCase(lastName);
  }

  public void addEmployee(Employee emp) {
    employeeRepo.save(emp);
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

  public List<Pet> findByAdoptStatus(String status) {
    return petsRepo.findByAdoptStatus(status);
  }

  public List<Pet> findPetsByEmployeeLastName(String lastName) {
    Employee emp = findEmployee(lastName);
    logger.debug("Searching for employees with last name: {}", lastName);
    List<Pet> petsList = new ArrayList<>();
    petsList = petsRepo.findByEmployee(emp);

    return petsList;
  }
}
