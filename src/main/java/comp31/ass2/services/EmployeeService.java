/*
* Author: Yanan Liu
* Date: 2023-12-06
*
* Class/File: EmployeeRepo.java
*
* Additional context:
* This Java class represents the service layer for handling business logic related to employees in the application.
* It is annotated with Spring's @Service to indicate that it provides business services.
*
* The class includes instance variables for repositories, specifically "EmployeeRepo," "PetsRepo," and "PetOwnerRepo."
* These repositories are injected through the constructor, allowing the service to interact with the corresponding entities.
*
* The service interacts with the repositories ("EmployeeRepo," "PetsRepo," "PetOwnerRepo") to perform CRUD operations
* on the associated entities, namely "Employee," "Pets," and "PetOwner."
*
* Additional Notes:
* - Business logic is encapsulated within this service to handle operations related to employees, pets, and pet owners.
*/

package comp31.ass2.services;

import java.util.List;

import org.springframework.stereotype.Service;

import comp31.ass2.model.entity.Pet;
import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.repos.EmployeeRepo;
import comp31.ass2.repos.PetOwnerRepo;
import comp31.ass2.repos.PetsRepo;

@Service
public class EmployeeService {
  EmployeeRepo employeeRepo;
  PetsRepo petsRepo;
  PetOwnerRepo petOwnerRepo;

  // Constructor to inject repositories
  public EmployeeService(EmployeeRepo employeeRepo, PetsRepo petsRepo, PetOwnerRepo petOwnerRepo) {
    super();
    this.employeeRepo = employeeRepo;
    this.petsRepo = petsRepo;
    this.petOwnerRepo = petOwnerRepo;
  }

  /**
   * Finds PetOwners by their registration status.
   *
   * @param status The registration status to search for.
   * @return List of PetOwners with the specified registration status.
   */
  public List<PetOwner> findOwnerByStatus(String status) {
    return petOwnerRepo.findByStatus(status);
  }

  /**
   * Sets the registration status for a PetOwner.
   *
   * @param ownerId     The ID of the PetOwner.
   * @param ownerStatus The new registration status to set.
   */
  public void setOwnerStatus(String ownerId, String ownerStatus) {
    PetOwner owner = petOwnerRepo.findByUserId(ownerId);
    if (owner != null) {
      owner.setStatus(ownerStatus);
      petOwnerRepo.save(owner);
    }
  }

  /**
   * Finds pets by their adoption status.
   *
   * @param status The adoption status to search for.
   * @return List of pets with the specified adoption status.
   */

  public List<Pet> findByAdoptStatus(String status) {
    return petsRepo.findByAdoptStatus(status);
  }
}
