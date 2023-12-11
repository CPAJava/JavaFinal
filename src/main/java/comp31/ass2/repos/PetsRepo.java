/*
* Author: Xuancheng Li
* Date: 2023-12-06
*
* Class/File: PetsRepo.java
*
* This Java Repo class extends the CrudeRepository to provide basic functionalities, such as finding instances by
* name, size, color, etc. 
*/
package comp31.ass2.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pet;
//followings are functions provided with CrudReposityor
public interface PetsRepo extends CrudRepository<Pet, Integer> {
    
    public List<Pet> findAll();

    public List<Pet> findByPetName(String petName);

    public List<Pet> findByPetSize(String petSize);

    public List<Pet> findByPetColor(String petColor);

    public List<Pet> findByPetSpecies(String petSpecies);

    public List<Pet> findByAdoptStatus(String adoptStatus);

    public List<Pet> findByEmployee(Employee employee);

    List<Pet> findPetsByPetSpeciesAndPetColorAndPetSize(String petSpecies, String petColor, String petSize);


}
