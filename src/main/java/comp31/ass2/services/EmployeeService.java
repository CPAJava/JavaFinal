package comp31.ass2.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pet;
import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.repos.EmployeeRepo;
import comp31.ass2.repos.PetOwnerRepo;
import comp31.ass2.repos.PetsRepo;

@Service
public class EmployeeService {
  EmployeeRepo employeeRepo; // inject through constructor
  PetsRepo petsRepo;
  PetOwnerRepo petOwnerRepo;
  Logger logger = LoggerFactory.getLogger(EmployeeService.class);

  public EmployeeService(EmployeeRepo employeeRepo, PetsRepo petsRepo, PetOwnerRepo petOwnerRepo) {

    super();
    this.employeeRepo = employeeRepo;
    this.petsRepo = petsRepo;
    this.petOwnerRepo = petOwnerRepo;
  }

    public List<PetOwner> findOwnerByStatus(String status) {
    return petOwnerRepo.findByStatus(status);
  }

  public void setOwnerStatus(String ownerId, String ownerStatus) {
    PetOwner owner = petOwnerRepo.findByUserId(ownerId);
    logger.info("Owner id", ownerId, ownerStatus);
    if (owner != null) {
      owner.setStatus(ownerStatus);
      petOwnerRepo.save(owner);
      logger.info("Owner status updated successfully. OwnerId: {}, New Status: {}", ownerId, ownerStatus);
    } else {
      logger.warn("Owner not found with ID: {}", ownerId);
    }
  }

  
  public List<Pet> findByAdoptStatus(String status) {
    return petsRepo.findByAdoptStatus(status);
  }
}
