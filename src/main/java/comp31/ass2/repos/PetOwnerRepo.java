package comp31.ass2.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import comp31.ass2.model.entity.PetOwner;
/*
 * Author: Manlin Mao
 * Date: 2023-12-06
 *
 * Class/File: PetOwnerRepo.java
 *
 * Additional context:
 * This interface, "PetOwnerRepo," extends the Spring Data JPA "CrudRepository" to provide basic CRUD
 * operations for the "PetOwner" entity. It defines methods for querying and retrieving data from the
 * underlying database. The interface includes methods such as "findAll," "findByUserId," and "findByStatus,"
 * which allow for the retrieval of all pet owners, a pet owner by their user ID, and a list of pet owners
 * based on their status, respectively.
 *
 * This repository interface is an essential component in the Spring Data JPA framework, enabling seamless
 * interaction with the underlying database for operations related to the "PetOwner" entity. It serves as
 * a bridge between the application and the database, providing a convenient and standardized way to perform
 * common database operations.
 */

public interface PetOwnerRepo extends CrudRepository<PetOwner, Integer> {
    public List<PetOwner> findAll();

    public PetOwner findByUserId(String userId);

    public List<PetOwner> findByStatus(String status);

}
